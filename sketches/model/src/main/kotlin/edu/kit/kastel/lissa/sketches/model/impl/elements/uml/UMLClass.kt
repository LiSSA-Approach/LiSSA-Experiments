package edu.kit.kastel.lissa.sketches.model.impl.elements.uml

import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLClass
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes

class UMLClass : UMLThing(), IUMLClass {
    override fun currentInterpretation() = SketchBoxTypes.CLASS
}