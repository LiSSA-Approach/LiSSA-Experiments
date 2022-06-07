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
import org.apache.hc.core5.net.URIBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URI
import java.util.*
import java.util.stream.IntStream.range

class SketchRecognitionService {
    companion object {
        const val DOCKER_SKETCH_RECOGNITION = "ghcr.io/lissa-approach/detectron2-sr:latest"
        const val DOCKER_OCR = "ghcr.io/lissa-approach/tesseract-diagram-ocr:latest"
        const val DEFAULT_PORT = 5005

        const val MINIMUM_AREA_IN_PXPX = 20
        const val EXPANSION_IN_PX = 5

        // Just for Debugging. Never set both to false
        const val DOCKER_SKETCH_RECOGNITION_VIA_DOCKER = true
        const val DOCKER_OCR_VIA_DOCKER = true

        val logger: Logger = LoggerFactory.getLogger(SketchRecognitionService::class.java)
    }

    private val oom = createObjectMapper()
    private val docker = DockerManager("SketchRecognitionService")
    private lateinit var diagramDetectionContainer: ContainerResponse
    private lateinit var ocrContainer: ContainerResponse

    fun start() {
        if (DOCKER_SKETCH_RECOGNITION_VIA_DOCKER) {
            this.diagramDetectionContainer = docker.createContainerByImage(DOCKER_SKETCH_RECOGNITION, true, false)
        } else {
            this.diagramDetectionContainer = ContainerResponse("", DEFAULT_PORT)
        }

        if (DOCKER_OCR_VIA_DOCKER) {
            this.ocrContainer = docker.createContainerByImage(DOCKER_OCR, true, false)
        } else {
            this.ocrContainer = ContainerResponse("", DEFAULT_PORT)
        }
    }

    fun stop() = this.docker.shutdownAll()

    fun recognize(input: InputStream): SketchRecognitionResult {
        val byteData = input.readBytes()
        ensureReadiness(diagramDetectionContainer.apiPort, "sketches")
        ensureReadiness(ocrContainer.apiPort, "ocr")
        val sketchRecognition =
            sendSketchRecognitionRequest(ByteArrayInputStream(byteData), diagramDetectionContainer.apiPort)

        val boxes: List<Box> = oom.readValue(sketchRecognition)

        val textRecognition = sendOCRRequest(
            ByteArrayInputStream(byteData),
            ocrContainer.apiPort,
            boxes.filter { it.classification == "Label" }
        )

        val texts: List<TextBox> = oom.readValue(textRecognition)
        combineBoxesAndText(boxes, texts)
        // TODO Extract Edges
        return SketchRecognitionResult(boxes, texts, listOf())
    }

    private fun combineBoxesAndText(boxes: List<Box>, texts: List<TextBox>) {
        for (text in texts) {
            if (text.text.length < 3) continue
            val intersects =
                boxes.filter { it.classification == "Label" }.map { it to it.box.bb().iou(text.absoluteBox().bb()) }

            val results = intersects.filter { it.second.iou > 0.5 || it.second.areaIntersect / it.first.area() > 0.1 }
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

    private fun sendSketchRecognitionRequest(image: InputStream, port: Int): String {
        // Create Request
        val builder = MultipartEntityBuilder.create()
        builder.addBinaryBody("file", image, ContentType.APPLICATION_OCTET_STREAM, "image")
        val uploadFile = HttpPost("http://127.0.0.1:$port/sketches/")
        val multipart: HttpEntity = builder.build()
        uploadFile.entity = multipart
        return executeRequest(uploadFile)
    }

    private fun sendOCRRequest(image: InputStream, port: Int, labels: List<Box>): String {
        val boxCoordinates = enhanceLabels(labels).flatMap { it.box }.joinToString(",")

        val builder = MultipartEntityBuilder.create()
        builder.addBinaryBody("file", image, ContentType.APPLICATION_OCTET_STREAM, "image")
        val multipart: HttpEntity = builder.build()
        val uploadFile = HttpPost("http://127.0.0.1:$port/ocr/")
        if (labels.isNotEmpty()) {
            val uri: URI = URIBuilder(uploadFile.uri).addParameter("regions", boxCoordinates).build()
            uploadFile.uri = uri
        }
        uploadFile.entity = multipart
        return executeRequest(uploadFile)
    }

    private fun enhanceLabels(labels: List<Box>): List<Box> {
        val result = labels.filter { it.area() > MINIMUM_AREA_IN_PXPX }.toMutableList()

        for (idx in result.indices) {
            result[idx] = expandPixels(result[idx])
        }

        return result
    }

    private fun expandPixels(box: Box): Box {
        // TODO Better expansion mechanism based on area
        val newPositions = listOf(
            box.box[0] - EXPANSION_IN_PX,
            box.box[1] - EXPANSION_IN_PX,
            box.box[2] + EXPANSION_IN_PX,
            box.box[3] + EXPANSION_IN_PX
        )
        // Copy References here. No Copies!
        return Box(box.uuid, newPositions, box.confidence, box.classification, box.texts)
    }

    private fun executeRequest(uploadFile: HttpPost): String {
        HttpClients.createDefault().use {
            val httpResponse: CloseableHttpResponse? = it.execute(uploadFile)
            val responseEntity: HttpEntity? = httpResponse?.entity
            val contentStream = responseEntity?.content
            return if (contentStream == null) "" else Scanner(contentStream).useDelimiter("\\A").next() ?: ""
        }
    }
}
