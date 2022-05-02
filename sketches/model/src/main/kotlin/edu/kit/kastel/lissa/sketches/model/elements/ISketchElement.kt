package edu.kit.kastel.lissa.sketches.model.elements

import edu.kit.kastel.lissa.sketches.model.types.SketchElementType
import java.io.Serializable

interface ISketchElement : Serializable {
    fun name(): String
    fun setName(name: String)
    fun currentConfidence(): Double
    fun setCurrentConfidence(confidence: Double)
    fun elementType(): SketchElementType
    fun setId(id: String)
    fun id(): String
}
