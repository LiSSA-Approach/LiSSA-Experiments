package edu.kit.kastel.lissa.linking.graphmatcher

import edu.kit.kastel.lissa.sketches.model.Sketch
import org.jgrapht.ListenableGraph
import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.graph.DefaultListenableGraph

fun convertSketchToGraph(sketch: Sketch): ListenableGraph<String, String> {
    val result: ListenableGraph<String, String> = DefaultListenableGraph(DefaultDirectedGraph(String::class.java))
    sketch.getBoxElements().forEach { result.addVertex(it.name()) }
    sketch.getRelationElements().map { it to it.connectedElements() }
        .also { list -> list.all { (_, elements) -> elements.size == 2 } }
        .forEach { result.addEdge(it.second[0].name(), it.second[1].name(), it.first.name()) }
    return result
}
