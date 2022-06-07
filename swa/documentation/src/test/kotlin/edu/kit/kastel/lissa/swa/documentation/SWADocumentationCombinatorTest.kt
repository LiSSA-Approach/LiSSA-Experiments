package edu.kit.kastel.lissa.swa.documentation

import edu.kit.kastel.mcse.ardoco.core.api.data.textextraction.ITextState
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File
import java.io.FileInputStream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SWADocumentationCombinatorTest {
    private lateinit var sketchRecognitionResult: SketchRecognitionResult
    private lateinit var textState: ITextState

    @BeforeAll
    fun setup() {
        val service = SketchRecognitionService()
        service.start()
        val file = FileInputStream(File("src/test/resources/highlevelArchitecture.png"))
        sketchRecognitionResult = service.recognize(file)
        service.stop()
        textState =
            TextPipeline().analyzeText(FileInputStream("../pcm/src/test/resources/benchmark/teammates/teammates.txt"))
    }

    @Test
    fun testCombinator() {
        val combinator = SWADocumentationCombinator()
        val textState = this.textState.createCopy()
        combinator.combineInformation(textState, sketchRecognitionResult)
    }
}
