package edu.kit.kastel.lissa.sketches.coco

import edu.kit.kastel.lissa.sketches.coco.domain.COCOAnnotation
import edu.kit.kastel.lissa.sketches.coco.domain.COCOData
import edu.kit.kastel.lissa.sketches.model.Sketch
import edu.kit.kastel.lissa.sketches.model.elements.SketchElementType
import edu.kit.kastel.lissa.sketches.model.elements.wrapper.AssociationNode
import edu.kit.kastel.lissa.sketches.model.elements.wrapper.ClassNode

class UMLDataAccessor(private val data: COCOData) {
    companion object {
        const val CLASS_CATEGORY = "ClassNode"
        val ASSOCIATIONS = listOf(
            "Aggregation",
            "Composition",
            "Extension",
            "Dependency",
            "Realization",
            "AssociationUnidirectional",
            "AssociationBidirectional"
        )
        const val LABEL_CATEGORY = "Label"
    }

    private val sketch: Sketch = Sketch()

    init {
        createSketchRepresentation()
    }

    fun sketch() = sketch

    private fun createSketchRepresentation() {
        addClasses()
        addAssociations()
    }

    private fun addAssociations() {
        val associations = ASSOCIATIONS.flatMap { c -> findByCategory(c) }
        associations.forEach { createAssociation(it) }
    }

    private fun addClasses() {
        val classes = findByCategory(CLASS_CATEGORY)
        val labels = findByCategory(LABEL_CATEGORY)
        classes.forEach { createClass(it, labels) }
    }

    private fun createAssociation(association: COCOAnnotation) {
        val element1 = findClassById(association.arrowStart!!)
        val element2 = findClassById(association.arrowEnd!!)
        val relation = sketch.addSketchElement(association.umlId, 1.0, SketchElementType.ASSOCIATION)
        val relationNode = AssociationNode(relation)
        relationNode.setUuid(association.umlId)
        relationNode.addToAssociation(element1)
        relationNode.addToAssociation(element2)
        relationNode.setAssociationType(AssociationNode.AssociationType.UNKNOWN)
    }

    private fun findClassById(id: Int): ClassNode {
        val classes = findByCategory(CLASS_CATEGORY)
        val clazz = classes.find { c -> c.id == id }!!
        return sketch.getElements(SketchElementType.CLASS, ClassNode::class.java)
            .find { c -> c.uuid() == clazz.umlId }!!
    }

    private fun createClass(clazz: COCOAnnotation, labels: List<COCOAnnotation>) {
        val name = labels.find { l -> l.belongsTo == clazz.id && l.labelType == "name" }?.name
        val node = sketch.addSketchElement(name ?: clazz.umlId, 1.0, SketchElementType.CLASS)
        val classNode = ClassNode(node)
        classNode.setUuid(clazz.umlId)
    }

    private fun findByCategory(category: String): List<COCOAnnotation> {
        val classCategory = data.categories.find { c -> c.name == category }!!
        return data.annotations.filter { a -> a.categoryId == classCategory.id }
    }
}
