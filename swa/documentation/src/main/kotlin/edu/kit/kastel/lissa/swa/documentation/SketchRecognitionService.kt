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
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.util.*
import java.util.stream.IntStream.range

class SketchRecognitionService {
    companion object {
        const val DOCKER_IMAGE = "ghcr.io/lissa-approach/detectron2-sr:latest"
        val logger: Logger = LoggerFactory.getLogger(SketchRecognitionService::class.java)
    }

    private val oom = createObjectMapper()
    private val docker = DockerManager("SketchRecognitionService")
    private lateinit var container: ContainerResponse

    fun start() {
        this.container = docker.createContainerByImage(DOCKER_IMAGE)
    }

    fun recognize(file: File): RecognitionResult {
        FileInputStream(file).use {
            return recognize(it)
        }
    }

    fun recognize(data: InputStream): RecognitionResult {
        ensureReadiness()
        val recognitionData = sendRequest(data)
        val boxes: List<Box> = oom.readValue(recognitionData)
        return RecognitionResult(boxes)
    }

    private fun ensureReadiness() {
        val tries = 3
        val waiting = 5000L

        HttpClients.createDefault().use { client ->
            for (currentTry in range(0, tries)) {
                try {
                    val response = client.execute(HttpGet("http://127.0.0.1:${container.apiPort}/sketches/"))
                    val responseEntity = response?.entity
                    val data = when (val contentStream = responseEntity?.content) {
                        null -> ""
                        else -> Scanner(contentStream).useDelimiter("\\A").use { it.next() } ?: ""
                    }
                    if (data.startsWith("Hello from Sketch Recognition")) return
                } catch (e: IOException) {
                    logger.debug("${e.message} -- Try $currentTry", e)
                    Thread.sleep(waiting)
                }
            }
        }
        error("Cannot ensure that sketch service is running.")
    }

    private fun sendRequest(image: InputStream): String {
        // Create Request
        val builder = MultipartEntityBuilder.create()
        builder.addBinaryBody("file", image, ContentType.APPLICATION_OCTET_STREAM, "image")
        val uploadFile = HttpPost("http://127.0.0.1:${container.apiPort}/sketches/")
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
