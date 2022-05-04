package edu.kit.kastel.lissa.linking.graphmatcher

import edu.kit.kastel.lissa.sketches.model.Sketch
import edu.kit.kastel.lissa.sketches.model.elements.SketchElementType
import edu.kit.kastel.lissa.sketches.model.elements.wrapper.AssociationNode
import edu.kit.kastel.lissa.sketches.model.elements.wrapper.ClassNode
import org.jgrapht.ListenableGraph
import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.graph.DefaultListenableGraph

fun convertSketchToGraph(sketch: Sketch): ListenableGraph<String, String> {
    val result: ListenableGraph<String, String> = DefaultListenableGraph(DefaultDirectedGraph(String::class.java))
    sketch.getElements(SketchElementType.CLASS, ClassNode::class.java).forEach { result.addVertex(it.name()) }
    sketch.getElements(SketchElementType.ASSOCIATION, AssociationNode::class.java).map { it to it.referencedClasses() }
        .also { list -> require(list.all { (_, elements) -> elements.size == 2 }) }
        .forEach { result.addEdge(it.second[0].name(), it.second[1].name(), it.first.name()) }
    return result
}
