package edu.kit.kastel.lissa.linking.graphmatcher

import edu.kit.kastel.lissa.linking.graph.Graph
import edu.kit.kastel.lissa.sketches.model.Sketch
import edu.kit.kastel.lissa.sketches.model.elements.SketchElementType
import edu.kit.kastel.lissa.sketches.model.elements.wrapper.AssociationNode
import edu.kit.kastel.lissa.sketches.model.elements.wrapper.ClassNode

fun convertSketchToGraph(sketch: Sketch): Graph {
    val result = Graph()

    sketch.getElements(SketchElementType.CLASS, ClassNode::class.java).forEach { result.addVertex(it.name()) }
    sketch.getElements(SketchElementType.ASSOCIATION, AssociationNode::class.java)
        .also { associationNodes -> require(associationNodes.all { element -> element.referencedClasses().size == 2 }) }
        .forEach { result.addEdge(it.name(), SketchElementType.ASSOCIATION.name, it.referencedClasses()[0].name(), it.referencedClasses()[1].name()) }
    return result
}
