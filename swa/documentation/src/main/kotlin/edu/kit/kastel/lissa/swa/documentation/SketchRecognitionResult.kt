package edu.kit.kastel.lissa.swa.documentation

import com.fasterxml.jackson.annotation.JsonProperty

data class SketchRecognitionResult(val boxes: List<Box>)

data class Box(
    @JsonProperty val box: List<Int>,
    @JsonProperty val confidence: Double,
    @JsonProperty("class") val classification: String,
    @Transient val texts: MutableList<TextBox> = mutableListOf()
)

data class TextBox(val x: Int, val y: Int, val w: Int, val h: Int, val text: String) {
    fun absoluteBox(): List<Double> = listOf(x, y, x + w, y + h).map { it.toDouble() }
}
