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
    @Transient val texts: MutableList<TextBox> = mutableListOf(),
    @Transient var dominatingColor: Int? = null
) {
    fun area() = abs(box[0] - box[2]) * abs(box[1] - box[3])
}

data class TextBox(
    @JsonProperty val x: Int,
    @JsonProperty val y: Int,
    @JsonProperty val w: Int,
    @JsonProperty val h: Int,
    @JsonProperty val confidence: Double,
    @JsonProperty val text: String,
    @Transient var dominatingColor: Int? = null
) {
    fun absoluteBox(): List<Double> = listOf(x, y, x + w, y + h).map { it.toDouble() }
    fun area(): Int {
        val box = absoluteBox()
        return abs(box[0] - box[2]).toInt() * abs(box[1] - box[3]).toInt()
    }
}

data class Edge(
    @JsonProperty val uuid: String,
    @JsonProperty val connectedBoxes: List<String>,
    @Transient val texts: MutableList<TextBox> = mutableListOf(),
)
