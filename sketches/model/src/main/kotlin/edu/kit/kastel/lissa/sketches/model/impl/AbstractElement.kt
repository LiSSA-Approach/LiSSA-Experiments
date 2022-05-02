package edu.kit.kastel.lissa.sketches.model.impl

import edu.kit.kastel.lissa.sketches.model.elements.ISketchElement
import java.util.*

abstract class AbstractElement protected constructor() : ISketchElement {
    @Transient
    val rawData: MutableMap<String, java.io.Serializable> = mutableMapOf()

    private var nameValue: String = ""
    private var currentConfidenceValue = 0.0
    private var elementId = UUID.randomUUID().toString()

    override fun name(): String = nameValue
    override fun setName(name: String) {
        nameValue = name
    }

    override fun currentConfidence() = currentConfidenceValue
    override fun setCurrentConfidence(confidence: Double) {
        currentConfidenceValue = confidence
    }

    override fun id(): String = this.elementId
    override fun setId(id: String) {
        this.elementId = id
    }

    override fun toString(): String {
        return String.format(
            "%s [name=%s, confidence=%s]", //
            this.javaClass.simpleName,
            nameValue,
            currentConfidenceValue
        )
    }
}
