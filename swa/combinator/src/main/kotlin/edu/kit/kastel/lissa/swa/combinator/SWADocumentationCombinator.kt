package edu.kit.kastel.lissa.swa.combinator

import edu.kit.kastel.lissa.swa.documentation.recognition.model.SketchRecognitionResult
import edu.kit.kastel.mcse.ardoco.core.api.agent.Claimant
import edu.kit.kastel.mcse.ardoco.core.api.data.text.Word
import edu.kit.kastel.mcse.ardoco.core.api.data.textextraction.MappingKind
import edu.kit.kastel.mcse.ardoco.core.api.data.textextraction.TextState
import edu.kit.kastel.mcse.ardoco.core.common.util.SimilarityUtils
import org.slf4j.LoggerFactory

class SWADocumentationCombinator : Claimant {
    companion object {
        private val logger = LoggerFactory.getLogger(SWADocumentationCombinator::class.java)
    }

    fun combineInformation(textState: TextState, sketchRecognitionResult: SketchRecognitionResult) {
        // TODO Further work ..
        val elements: List<String> = sketchRecognitionResult.boxes.filter { it.confidence > 0.5 }.flatMap { it.texts }
            .filter { it.confidence > 0.5 }.map { it.text }
        val nounMappings = textState.nounMappings
        for (nounMapping in nounMappings) {
            val similarList: List<Pair<Word, String>> = nounMapping.referenceWords.toList().times(elements)
            val similars = similarList.filter { (word, otherString) -> SimilarityUtils.areWordsSimilar(word.text, otherString) }
            if (similars.size > 1) {
                logger.warn("Found multiple matchings for sketch element and model element .. using first")
            }

            val similar = similars.sortedBy { it.first.position }.sortedBy { it.first.sentenceNo }.firstOrNull()

            if (similar != null) {
                logger.info("Found Noun Mapping similar to a sketch element: ${nounMapping.reference}::${similar.second}")
                nounMapping.addKindWithProbability(MappingKind.NAME, this, 0.9)
            }
        }
    }
}

private fun <A, B> List<A>.times(other: List<B>): List<Pair<A, B>> {
    val result = mutableListOf<Pair<A, B>>()
    for (a in this) {
        for (b in other) {
            result.add(a to b)
        }
    }
    return result
}
