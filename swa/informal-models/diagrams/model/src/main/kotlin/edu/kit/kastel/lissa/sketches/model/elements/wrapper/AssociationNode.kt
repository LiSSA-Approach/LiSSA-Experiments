package edu.kit.kastel.lissa.sketches.model.elements.wrapper

import edu.kit.kastel.lissa.sketches.model.elements.SketchElement
import edu.kit.kastel.lissa.sketches.model.elements.SketchElementType
import edu.kit.kastel.lissa.sketches.model.elements.properties.complex.ListOfStringPrototype

class AssociationNode(sketchElement: SketchElement) :
    GenericNode(sketchElement, SketchElementType.ASSOCIATION),
    ISketchNode {
    companion object {
        const val REFERENCED = "REFERENCED"
        const val TYPE = "AssociationType"
    }

    enum class AssociationType {
        UNKNOWN,
        GENERALIZATION,
        REALIZATION,
        AGGREGATION,
        COMPOSITION,
        N_ARY_ASSOCIATION
    }

    fun associationType() = AssociationType.valueOf(
        sketchElement.readPrimitiveValue(
            TYPE,
            String::class.java,
            AssociationType.UNKNOWN.name
        )
    )

    fun setAssociationType(type: AssociationType) {
        sketchElement.savePrimitiveValue(TYPE, type.name)
    }

    fun referencedClasses(): List<ClassNode> {
        val ids = sketchElement.readValue(REFERENCED, ListOfStringPrototype, mutableListOf())
        return ids.map { sketchElement.sketch().findById(it) }.map { ClassNode(it) }
    }

    fun setReferencedClasses(referenced: List<ClassNode>) {
        val ids = referenced.map { it.uuid() }.toMutableList()
        this.sketchElement.saveValue(REFERENCED, ListOfStringPrototype, ids)
    }

    fun addToAssociation(clazz: ClassNode) {
        val currentClasses = referencedClasses().toMutableList()
        if (currentClasses.contains(clazz)) {
            return
        }
        currentClasses.add(clazz)
        setReferencedClasses(currentClasses)
    }

    override fun toString(): String =
        "${this.javaClass.simpleName}(referencedClasses='${referencedClasses().map { it.name() }}')"
}
