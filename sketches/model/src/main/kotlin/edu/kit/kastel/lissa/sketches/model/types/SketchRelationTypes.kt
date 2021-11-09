package edu.kit.kastel.lissa.sketches.model.types

import edu.kit.kastel.lissa.sketches.model.elements.ISketchElement
import edu.kit.kastel.lissa.sketches.model.impl.createCompatibleObject
import edu.kit.kastel.lissa.sketches.model.logger
import kotlin.reflect.KClass

enum class SketchRelationTypes {
    UNKNOWN, CLASS_ASSOCIATION;

    fun <O : ISketchElement> map(element: ISketchElement, type: KClass<O>): O {
        val o = createCompatibleObject(
            element,
            type,
            this,
            SketchRelationTypeMapping::class,
            annotation2EnumValue = { a -> a.type },
            annotation2Class = { a -> a.implementation })
        logger.debug("Created compatible object for $this: $o")
        return o
    }
}