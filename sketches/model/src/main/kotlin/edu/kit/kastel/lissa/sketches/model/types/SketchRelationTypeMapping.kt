package edu.kit.kastel.lissa.sketches.model.types

import edu.kit.kastel.lissa.sketches.model.elements.ISketchElement
import kotlin.reflect.KClass

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
annotation class SketchRelationTypeMapping(
    val type: SketchRelationTypes,
    val implementation: KClass<out ISketchElement>
)
