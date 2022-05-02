package edu.kit.kastel.lissa.sketches.model.impl.elements

import edu.kit.kastel.lissa.sketches.model.elements.generic.IInterface
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes

class InterfaceImpl : CallableImpl(), IInterface {
    override fun currentInterpretation(): SketchBoxTypes = SketchBoxTypes.INTERFACE
}
