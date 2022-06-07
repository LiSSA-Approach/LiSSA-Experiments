package edu.kit.kastel.lissa.swa.documentation

import edu.kit.kastel.mcse.ardoco.core.api.data.DataStructure
import edu.kit.kastel.mcse.ardoco.core.api.data.textextraction.ITextState
import edu.kit.kastel.mcse.ardoco.core.text.providers.corenlp.CoreNLPProvider
import edu.kit.kastel.mcse.ardoco.core.textextraction.TextExtraction
import java.io.InputStream

class TextPipeline {
    fun analyzeText(textStream: InputStream): ITextState {
        val text = CoreNLPProvider(textStream).annotatedText
        val data = DataStructure(text, mapOf())
        TextExtraction().execute(data, mapOf())
        return data.textState!!
    }
}
