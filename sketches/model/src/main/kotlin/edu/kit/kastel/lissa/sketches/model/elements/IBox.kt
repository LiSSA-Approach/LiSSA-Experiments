package edu.kit.kastel.lissa.sketches.model.elements

import edu.kit.kastel.lissa.sketches.model.impl.Box
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypeMapping
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes

@SketchBoxTypeMapping(type = SketchBoxTypes.UNKNOWN, implementation = Box::class)
interface IBox : ISketchElement {
    fun currentInterpretation(): SketchBoxTypes
    override fun elementType(): SketchElementType = SketchElementType.BOXOID
}