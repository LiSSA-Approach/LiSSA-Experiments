package edu.kit.kastel.lissa.sketches.model.elements

import java.io.Serializable

interface ISketchElement : Serializable {
    fun name(): String
    fun setName(name: String)
    fun currentConfidence(): Double
    fun setCurrentConfidence(confidence: Double)
}