package edu.kit.kastel.lissa.sketches.model.impl.elements

import edu.kit.kastel.lissa.sketches.model.elements.component_diagram.IComponent
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes

class ComponentImpl : CallableImpl(), IComponent {
    override fun currentInterpretation(): SketchBoxTypes = SketchBoxTypes.COMPONENT
}
