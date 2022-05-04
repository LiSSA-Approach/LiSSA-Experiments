package edu.kit.kastel.lissa.sketches.model

import edu.kit.kastel.lissa.sketches.model.elements.SketchElement
import edu.kit.kastel.lissa.sketches.model.elements.SketchElementType
import edu.kit.kastel.lissa.sketches.model.elements.wrapper.ISketchNode

interface ISketch {
    fun findById(uuid: String): SketchElement
    fun addSketchElement(name: String, confidence: Double, type: SketchElementType): SketchElement
    fun <E : ISketchNode> getElements(type: SketchElementType, typeAsClass: Class<E>): List<E>
}
