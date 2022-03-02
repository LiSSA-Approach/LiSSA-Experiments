package edu.kit.kastel.lissa.sketches.model

import edu.kit.kastel.lissa.sketches.model.elements.IBox
import edu.kit.kastel.lissa.sketches.model.elements.IRelation
import edu.kit.kastel.lissa.sketches.model.elements.ISketchElement
import edu.kit.kastel.lissa.sketches.model.impl.AbstractElement
import edu.kit.kastel.lissa.sketches.model.impl.findByClass
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypeMapping
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypeMapping
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypes
import java.io.Serializable
import java.util.*
import kotlin.reflect.KClass

class Sketch : Serializable, ISketch {
    private val boxElements = IdentityHashMap<MutableMap<String, Any>, IBox>()
    private val relationElements = IdentityHashMap<MutableMap<String, Any>, IRelation>()

    fun addSketchElement(element: IBox) {
        val elemAsBox = elemAsData(element)
        boxElements[elemAsBox.rawData] = element
    }

    fun addSketchElement(element: IRelation) {
        val elemAsRelation = elemAsData(element)
        relationElements[elemAsRelation.rawData] = element
    }

    fun delSketchElement(element: IBox) {
        val elemAsBox = elemAsData(element)
        boxElements.remove(elemAsBox.rawData)
    }

    fun delSketchElement(element: IRelation) {
        val elemAsRelation = elemAsData(element)
        relationElements.remove(elemAsRelation.rawData)
    }

    fun <O : IBox> changeInterpretation(element: IBox, clazz: KClass<O>): O {
        val type = findByClass(clazz, SketchBoxTypeMapping::class) { a -> a.type }
        val elemAsBox = elemAsData(element)
        val newInterpretation = type.map(elemAsBox, clazz)
        boxElements[elemAsBox.rawData] = newInterpretation
        return newInterpretation
    }

    fun <O : IRelation> changeInterpretation(element: IRelation, clazz: KClass<O>): O {
        val type = findByClass(clazz, SketchRelationTypeMapping::class) { a -> a.type }
        val elemAsBox = elemAsData(element)
        val newInterpretation = type.map(elemAsBox, clazz)
        relationElements[elemAsBox.rawData] = newInterpretation
        return newInterpretation
    }

    fun getBoxElements(): List<IBox> {
        return boxElements.values.toList()
    }

    fun <B : IBox> getBoxElements(type: KClass<B>): List<B> {
        return this.getBoxElements().filterIsInstance(type.java)
    }

    fun getRelationElements(): List<IRelation> {
        return relationElements.values.toList()
    }

    fun <R : IRelation> getRelationElements(type: KClass<R>): List<R> {
        return this.getRelationElements().filterIsInstance(type.java)
    }

    fun getElementsByType(type: SketchBoxTypes): List<IBox> {
        return this.getBoxElements().filter { e: IBox -> e.currentInterpretation() == type }
    }

    fun getElementsByType(type: SketchRelationTypes): List<IRelation> {
        return this.getRelationElements().filter { e: IRelation ->
            e.currentInterpretation() == type
        }
    }

    private fun elemAsData(element: ISketchElement): AbstractElement {
        require(element is AbstractElement) {
            "You can only use elements based on " +
                AbstractElement::class.java +
                " with " +
                Sketch::class.java
        }
        return element
    }

    override fun getCurrentInterpretation(element: IBox): IBox? {
        return boxElements[elemAsData(element).rawData]
    }

    override fun getCurrentInterpretation(relation: IRelation): IRelation? {
        return relationElements[elemAsData(relation).rawData]
    }

    override fun toString(): String {
        var result = "Sketch:\n"
        for (element in this.getBoxElements().sortedBy { e -> e.name() }) {
            result += "$element\n"
        }
        result += "\n"
        for (element in this.getRelationElements().sortedBy { e -> e.name() }) {
            result += "$element\n"
        }
        return result.trim()
    }
}
