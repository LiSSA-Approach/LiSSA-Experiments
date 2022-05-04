package edu.kit.kastel.lissa.sketches.model.elements.wrapper

import edu.kit.kastel.lissa.sketches.model.elements.SketchElement

internal interface ISketchNode {
    fun sketchElement(): SketchElement
    fun uuid() = sketchElement().uuid()
    fun setUuid(uuid: String) = sketchElement().setUuid(uuid)
}
