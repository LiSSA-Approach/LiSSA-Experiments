package edu.kit.kastel.lissa.swa.combinator

import edu.kit.kastel.lissa.swa.documentation.SketchRecognitionResult
import edu.kit.kastel.mcse.ardoco.core.api.agent.IClaimant
import edu.kit.kastel.mcse.ardoco.core.api.data.textextraction.ITextState
import edu.kit.kastel.mcse.ardoco.core.api.data.textextraction.MappingKind
import edu.kit.kastel.mcse.ardoco.core.common.util.SimilarityUtils
import org.slf4j.LoggerFactory

class SWADocumentationCombinator : IClaimant {
    companion object {
        private val logger = LoggerFactory.getLogger(SWADocumentationCombinator::class.java)
    }

    fun combineInformation(textState: ITextState, sketchRecognitionResult: SketchRecognitionResult) {
        // TODO Further work ..
        val elements = sketchRecognitionResult.boxes.filter { it.confidence > 0.5 }.flatMap { it.texts }
            .filter { it.confidence > 0.5 }.map { it.text }
        val nounMappings = textState.nounMappings
        for (nounMapping in nounMappings) {
            val similar = nounMapping.referenceWords.castToList().times(elements)
                .find { (a, b) -> SimilarityUtils.areWordsSimilar(a.text, b, 0.9) }

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
