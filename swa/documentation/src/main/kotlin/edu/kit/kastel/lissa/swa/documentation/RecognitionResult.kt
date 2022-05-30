package edu.kit.kastel.lissa.swa.documentation

import com.fasterxml.jackson.annotation.JsonProperty

data class RecognitionResult(val boxes: List<Box>)

data class Box(
    @JsonProperty val box: List<Int>,
    @JsonProperty val confidence: Double,
    @JsonProperty("class") val classification: String
)
