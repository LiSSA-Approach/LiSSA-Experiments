package edu.kit.kastel.lissa.sketches.model.impl

import edu.kit.kastel.lissa.sketches.model.elements.ISketchElement

abstract class AbstractElement protected constructor() : ISketchElement {
    @Transient val rawData: MutableMap<String, Any> = mutableMapOf()

    private var nameValue: String = ""
    private var currentConfidenceValue = 0.0

    override fun name(): String = nameValue
    override fun setName(name: String) {
        nameValue = name
    }

    override fun currentConfidence() = currentConfidenceValue
    override fun setCurrentConfidence(confidence: Double) {
        currentConfidenceValue = confidence
    }

    override fun toString(): String {
        return String.format(
            "%s [name=%s, confidence=%s]", //
            this.javaClass.simpleName,
            nameValue,
            currentConfidenceValue)
    }
}
