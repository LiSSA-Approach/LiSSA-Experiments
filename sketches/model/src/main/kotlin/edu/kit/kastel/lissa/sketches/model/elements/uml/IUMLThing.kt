package edu.kit.kastel.lissa.sketches.model.elements.uml

import edu.kit.kastel.lissa.sketches.model.elements.IBox

interface IUMLThing : IBox {
    fun addAttribute(attribute: String)
    fun delAttribute(attribute: String)
    fun attributes(): List<String>
    fun addMethod(method: String)
    fun delMethod(method: String)
    fun methods(): List<String>
}