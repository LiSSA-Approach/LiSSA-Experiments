package edu.kit.kastel.lissa.sketches.model.elements.wrapper

import edu.kit.kastel.lissa.sketches.model.elements.SketchElement
import edu.kit.kastel.lissa.sketches.model.elements.SketchElementType

open class GenericNode(protected val sketchElement: SketchElement, sketchElementType: SketchElementType) : ISketchNode {
    companion object {
        const val NAME = "NAME"
    }

    init {
        this.sketchElement.setInterpretation(sketchElementType)
    }

    final override fun sketchElement(): SketchElement = sketchElement

    fun name(): String =
        sketchElement.readPrimitiveValue(NAME, String::class.java, "${this::class.java}[Unknown Instance]")

    fun setName(newName: String) {
        sketchElement.savePrimitiveValue(NAME, newName)
    }

    fun setConfidence(newConfidence: Double) = sketchElement.setConfidence(newConfidence)
    fun confidence() = sketchElement.confidence()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GenericNode) return false
        return sketchElement == other.sketchElement && this.javaClass == other.javaClass
    }

    override fun hashCode(): Int = sketchElement.hashCode()
}
