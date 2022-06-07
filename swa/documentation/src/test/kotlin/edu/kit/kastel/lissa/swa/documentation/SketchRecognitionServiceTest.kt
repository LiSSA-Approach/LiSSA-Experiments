package edu.kit.kastel.lissa.swa.documentation

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.awt.Desktop
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SketchRecognitionServiceTest {
    companion object {
        const val PATH = "src/test/resources/bbb-arch-overview.png"
        // const val PATH = "src/test/resources/highlevelArchitecture.png"
    }

    private lateinit var service: SketchRecognitionService

    @BeforeAll
    fun setup() {
        service = SketchRecognitionService()
        service.start()
        File("target/testout").mkdirs()
    }

    @AfterAll
    fun tearDown() {
        service.stop()
    }

    @Test
    fun testSimpleRecognition() {
        val file = FileInputStream(File(PATH))
        val response = service.recognize(file)
        Assertions.assertNotNull(response)
        // Assertions.assertEquals(42, response.boxes.size)

        val destination = File("target/testout/result_testSimpleRecognition.png")
        visualize(
            FileInputStream(File(PATH)),
            response,
            FileOutputStream(destination)
        )
        if (Desktop.isDesktopSupported()) Desktop.getDesktop().open(destination)
    }
}
