package edu.kit.kastel.lissa.sketches.model.impl

import edu.kit.kastel.lissa.sketches.model.elements.IBox
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes

open class BoxImpl : AbstractElement, IBox {
    constructor() {
        // NOP
    }

    constructor(name: String, confidence: Double) {
        this.setName(name)
        this.setCurrentConfidence(confidence)
    }

    override fun currentInterpretation(): SketchBoxTypes = SketchBoxTypes.UNKNOWN

    override fun toString(): String {
        return String.format(
            "%s [name=%s, confidence=%s, interpretation=%s]", //
            this.javaClass.simpleName,
            this.name(),
            this.currentConfidence(),
            currentInterpretation()
        )
    }
}
