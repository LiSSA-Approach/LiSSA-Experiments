package edu.kit.kastel.lissa.sketches.model.impl.elements.uml

import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLInterface
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes

class UMLInterface : UMLThing(), IUMLInterface {
    override fun currentInterpretation() = SketchBoxTypes.INTERFACE
}
