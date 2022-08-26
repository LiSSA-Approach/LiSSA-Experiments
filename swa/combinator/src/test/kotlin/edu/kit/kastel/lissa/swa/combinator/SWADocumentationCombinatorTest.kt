package edu.kit.kastel.lissa.swa.combinator

import edu.kit.kastel.informalin.data.DataRepository
import edu.kit.kastel.lissa.swa.api.sketches.SketchRecognitionResult
import edu.kit.kastel.lissa.swa.documentation.recognition.SketchRecognitionService
import edu.kit.kastel.mcse.ardoco.core.api.data.textextraction.TextState
import edu.kit.kastel.mcse.ardoco.core.text.providers.corenlp.CoreNLPProvider
import edu.kit.kastel.mcse.ardoco.core.textextraction.TextExtraction
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SWADocumentationCombinatorTest {
    private lateinit var sketchRecognitionResult: SketchRecognitionResult
    private lateinit var textState: TextState

    @BeforeAll
    fun setup() {
        val service = SketchRecognitionService()
        service.start()
        val file = FileInputStream(File("../documentation/src/test/resources/highlevelArchitecture.png"))
        sketchRecognitionResult = service.recognize(file)
        service.stop()
        textState =
            analyzeText(FileInputStream("../benchmark/teammates/teammates.txt"))
    }

    private fun analyzeText(textStream: InputStream): TextState {
        val repository = DataRepository()
        CoreNLPProvider(repository, textStream).run()
        TextExtraction(repository).run()
        return repository.getData("TextState", TextState::class.java).orElseThrow()
    }

    @Test
    fun testCombinator() {
        val combinator = SWADocumentationCombinator()
        val textState = this.textState.createCopy()
        combinator.combineInformation(textState, sketchRecognitionResult)
    }
}
