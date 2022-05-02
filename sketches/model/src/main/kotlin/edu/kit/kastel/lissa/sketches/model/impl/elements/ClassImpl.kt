package edu.kit.kastel.lissa.sketches.model.impl.elements

import edu.kit.kastel.lissa.sketches.model.elements.class_diagram.IClass
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes

class ClassImpl : CallableImpl(), IClass {
    override fun currentInterpretation(): SketchBoxTypes = SketchBoxTypes.CLASS
}
