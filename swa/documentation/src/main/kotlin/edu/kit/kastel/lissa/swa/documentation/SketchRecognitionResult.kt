package edu.kit.kastel.lissa.swa.documentation

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import kotlin.math.abs

data class SketchRecognitionResult(val boxes: List<Box>, val textBoxes: List<TextBox>, val edges: List<Edge>)

data class Box(
    @JsonProperty val uuid: String = UUID.randomUUID().toString(),
    @JsonProperty val box: List<Int>,
    @JsonProperty val confidence: Double,
    @JsonProperty("class") val classification: String,
    @Transient val texts: MutableList<TextBox> = mutableListOf()
) {
    fun area() = abs(box[0] - box[2]) * abs(box[1] - box[3])
}

data class TextBox(val x: Int, val y: Int, val w: Int, val h: Int, val confidence: Double, val text: String) {
    fun absoluteBox(): List<Double> = listOf(x, y, x + w, y + h).map { it.toDouble() }
}

data class Edge(
    @JsonProperty val uuid: String,
    @JsonProperty val connectedBoxes: List<String>,
    @Transient val texts: MutableList<TextBox> = mutableListOf()
)
