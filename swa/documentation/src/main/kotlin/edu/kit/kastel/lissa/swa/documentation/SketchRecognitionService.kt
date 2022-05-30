package edu.kit.kastel.lissa.swa.documentation

import com.fasterxml.jackson.module.kotlin.readValue
import edu.kit.kastel.informalin.framework.docker.ContainerResponse
import edu.kit.kastel.informalin.framework.docker.DockerManager
import edu.kit.kastel.lissa.utils.createObjectMapper
import org.apache.hc.client5.http.classic.methods.HttpGet
import org.apache.hc.client5.http.classic.methods.HttpPost
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.core5.http.ContentType
import org.apache.hc.core5.http.HttpEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.util.*
import java.util.stream.IntStream.range

class SketchRecognitionService {
    companion object {
        const val DOCKER_SKETCH_RECOGNITION = "ghcr.io/lissa-approach/detectron2-sr:latest"
        const val DOCKER_OCR = "ghcr.io/lissa-approach/tesseract-diagram-ocr:latest"
        val logger: Logger = LoggerFactory.getLogger(SketchRecognitionService::class.java)
    }

    private val oom = createObjectMapper()
    private val docker = DockerManager("SketchRecognitionService")
    private lateinit var diagramDetectionContainer: ContainerResponse
    private lateinit var ocrContainer: ContainerResponse

    fun start() {
        this.diagramDetectionContainer = docker.createContainerByImage(DOCKER_SKETCH_RECOGNITION, true, false)
        this.ocrContainer = docker.createContainerByImage(DOCKER_OCR, true, false)
    }

    fun recognize(file: File): SketchRecognitionResult {
        FileInputStream(file).use {
            return recognize(it)
        }
    }

    fun recognize(input: InputStream): SketchRecognitionResult {
        val byteData = input.readBytes()
        ensureReadiness(diagramDetectionContainer.apiPort, "sketches")
        ensureReadiness(ocrContainer.apiPort, "ocr")
        val sketchRecognition =
            sendRequest(ByteArrayInputStream(byteData), diagramDetectionContainer.apiPort, "sketches")
        val textRecognition = sendRequest(ByteArrayInputStream(byteData), ocrContainer.apiPort, "ocr")

        val boxes: List<Box> = oom.readValue(sketchRecognition)
        val texts: List<TextBox> = oom.readValue(textRecognition)
        combineBoxesAndText(boxes, texts)
        return SketchRecognitionResult(boxes)
    }

    private fun combineBoxesAndText(boxes: List<Box>, texts: List<TextBox>) {
        for (text in texts) {
            if (text.text.length < 3) continue
            val intersects =
                boxes.filter { it.classification == "Label" }.map { it to it.box.bb().iou(text.absoluteBox().bb()) }

            val results = intersects.filter { it.second > 0.0 }
            if (results.isEmpty()) continue
            logger.info("Found {} intersects with {}", intersects.size, text.text)
            results.forEach { it.first.texts.add(text) }
        }
    }

    private fun ensureReadiness(port: Int, entryPoint: String) {
        val tries = 5
        val waiting = 10000L

        HttpClients.createDefault().use { client ->
            for (currentTry in range(0, tries)) {
                try {
                    val response = client.execute(HttpGet("http://127.0.0.1:$port/$entryPoint/"))
                    val responseEntity = response?.entity
                    val data = when (val contentStream = responseEntity?.content) {
                        null -> ""
                        else -> Scanner(contentStream).useDelimiter("\\A").use { it.next() } ?: ""
                    }
                    if (data.startsWith("Hello from ")) return
                } catch (e: IOException) {
                    logger.debug("${e.message} -- Try $currentTry", e)
                    Thread.sleep(waiting)
                }
            }
        }
        error("Cannot ensure that sketch service is running.")
    }

    private fun sendRequest(image: InputStream, port: Int, entryPoint: String): String {
        // Create Request
        val builder = MultipartEntityBuilder.create()
        builder.addBinaryBody("file", image, ContentType.APPLICATION_OCTET_STREAM, "image")
        val uploadFile = HttpPost("http://127.0.0.1:$port/$entryPoint/")
        val multipart: HttpEntity = builder.build()
        uploadFile.entity = multipart

        HttpClients.createDefault().use {
            val httpResponse: CloseableHttpResponse? = it.execute(uploadFile)
            val responseEntity: HttpEntity? = httpResponse?.entity
            val contentStream = responseEntity?.content
            return if (contentStream == null) "" else Scanner(contentStream).useDelimiter("\\A").next() ?: ""
        }
    }

    fun stop() = this.docker.shutdownAll()
}
