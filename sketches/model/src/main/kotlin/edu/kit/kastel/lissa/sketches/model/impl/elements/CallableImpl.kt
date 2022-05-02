package edu.kit.kastel.lissa.sketches.model.impl.elements

import edu.kit.kastel.lissa.sketches.model.elements.generic.ICallable
import edu.kit.kastel.lissa.sketches.model.impl.AbstractElement

abstract class CallableImpl : AbstractElement(), ICallable {
    private val attributes: MutableList<String> = mutableListOf()
    private val methods: MutableList<String> = mutableListOf()

    override fun addAttribute(attribute: String) {
        attributes.add(attribute)
    }

    override fun delAttribute(attribute: String) {
        attributes.remove(attribute)
    }

    override fun attributes(): List<String> = attributes.toList()

    override fun addMethod(method: String) {
        methods.add(method)
    }

    override fun delMethod(method: String) {
        methods.remove(method)
    }

    override fun methods(): List<String> = methods.toList()
}
