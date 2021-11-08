package edu.kit.kastel.lissa.sketches.model.types

import edu.kit.kastel.lissa.sketches.model.elements.ISketchElement
import edu.kit.kastel.lissa.sketches.model.impl.createCompatibleObject
import kotlin.reflect.KClass
import kotlin.reflect.cast

enum class SketchBoxTypes {
    CLASS, INTERFACE, METHOD, COMPONENT, UNKNOWN;

    fun <O : ISketchElement> map(element: ISketchElement, type: KClass<O>): O {
        val o = createCompatibleObject(element, type, this, SketchBoxTypeMapping::class, annotation2EnumValue = { a -> a.type }, annotation2Class = { a -> a.implementation })
        return type.cast(o)
    }

    companion object {
        fun findByClass(type: KClass<out ISketchElement>): SketchBoxTypes {
            val mapping = type.java.getDeclaredAnnotation(SketchBoxTypeMapping::class.java)
            if (mapping != null) {
                return mapping.type
            }
            throw IllegalArgumentException("Can't find a suitable type for class $type")
        }
    }
}