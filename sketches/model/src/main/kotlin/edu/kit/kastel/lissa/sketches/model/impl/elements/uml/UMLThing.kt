package edu.kit.kastel.lissa.sketches.model.impl.elements.uml

import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLThing
import edu.kit.kastel.lissa.sketches.model.impl.Box

abstract class UMLThing protected constructor() : Box(), IUMLThing {
    private val attributes: MutableList<String> = mutableListOf()
    private val methods: MutableList<String> = mutableListOf()
    private var umlId: String? = null

    override fun addAttribute(attribute: String) {
        attributes.add(attribute)
    }

    override fun delAttribute(attribute: String) {
        attributes.remove(attribute)
    }

    override fun attributes(): List<String> {
        return attributes
    }

    override fun addMethod(method: String) {
        methods.add(method)
    }

    override fun delMethod(method: String) {
        methods.remove(method)
    }

    override fun methods(): List<String> {
        return methods
    }

    override fun setUMLId(umlId: String) {
        this.umlId = umlId
    }

    override fun umlId(): String {
        return umlId!!
    }
}
