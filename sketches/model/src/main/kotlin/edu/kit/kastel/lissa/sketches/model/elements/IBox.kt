package edu.kit.kastel.lissa.sketches.model.elements

import edu.kit.kastel.lissa.sketches.model.impl.BoxImpl
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypeMapping
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes
import edu.kit.kastel.lissa.sketches.model.types.SketchElementType

@SketchBoxTypeMapping(type = SketchBoxTypes.UNKNOWN, implementation = BoxImpl::class)
interface IBox : ISketchElement {
    fun currentInterpretation(): SketchBoxTypes
    override fun elementType(): SketchElementType = SketchElementType.BOX
}
