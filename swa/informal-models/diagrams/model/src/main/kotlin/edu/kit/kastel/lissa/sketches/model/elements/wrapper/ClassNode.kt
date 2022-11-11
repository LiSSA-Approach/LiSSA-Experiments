package edu.kit.kastel.lissa.sketches.model.elements.wrapper

import edu.kit.kastel.lissa.sketches.model.elements.SketchElement
import edu.kit.kastel.lissa.sketches.model.elements.SketchElementType
import edu.kit.kastel.lissa.sketches.model.elements.properties.complex.ListOfStringPrototype

class ClassNode(sketchElement: SketchElement) : GenericNode(sketchElement, SketchElementType.CLASS), ISketchNode {
    companion object {
        const val ATTRIBUTES = "ATTRIBUTES"
        const val METHODS = "METHODS"
    }

    fun attributes(): List<String> {
        return sketchElement.readValue(ATTRIBUTES, ListOfStringPrototype, mutableListOf())
    }

    fun setAttributes(attributes: List<String>) {
        return sketchElement.saveValue(ATTRIBUTES, ListOfStringPrototype, attributes.toMutableList())
    }

    fun methods(): List<String> {
        return sketchElement.readValue(METHODS, ListOfStringPrototype, mutableListOf())
    }

    fun setMethods(attributes: List<String>) {
        return sketchElement.saveValue(METHODS, ListOfStringPrototype, attributes.toMutableList())
    }

    fun addMethod(methodName: String) {
        val currentMethods = methods().toMutableList()
        if (currentMethods.contains(methodName)) {
            return
        }
        currentMethods.add(methodName)
        setMethods(currentMethods)
    }

    override fun toString(): String =
        "${this.javaClass.simpleName}(attributes='${attributes()}', methods='${methods()}')"
}
