package edu.kit.kastel.lissa.sketches.model

import edu.kit.kastel.lissa.sketches.model.elements.SketchElementType
import edu.kit.kastel.lissa.sketches.model.elements.wrapper.AssociationNode
import edu.kit.kastel.lissa.sketches.model.elements.wrapper.ClassNode
import org.junit.jupiter.api.Test

internal class TestIT : TestBase() {
    @Test
    fun test() {
        val sketch: ISketch = Sketch()
        sketch.addSketchElement("Component A", 0.8, SketchElementType.CLASS)
        sketch.addSketchElement("Component B", 0.6, SketchElementType.CLASS)
        val association = sketch.addSketchElement("Relation 1", 0.5, SketchElementType.ASSOCIATION)
        val associationNode = AssociationNode(association)

        sketch.getElements(SketchElementType.CLASS, ClassNode::class.java).forEach { e: ClassNode ->
            associationNode.addToAssociation(e)
        }
        println(sketch)
    }
}
