package edu.kit.kastel.lissa.linking.graphmatcher

import edu.kit.kastel.lissa.linking.graph.Graph
import edu.kit.kastel.lissa.linking.graph.Node
import org.apache.commons.text.similarity.LevenshteinDistance
import kotlin.math.max

class SeedGenerator {
    companion object {
        const val SIMILARITY_THRESHOLD = 0.3
    }

    fun generateSeeds(graphA: Graph, graphB: Graph): List<Pair<Node, Node>> {
        val seedsByNLP = genSeedsByNLPNodes(graphA, graphB)
        return seedsByNLP.toList()
    }

    private fun genSeedsByNLPNodes(graphA: Graph, graphB: Graph): List<Pair<Node, Node>> {
        val cross = graphA.nodes().flatMap { a -> graphB.nodes().map { b -> a to b } }
        return cross.parallelStream().map { it to compareNodesNLP(it) }.filter { it.second < SIMILARITY_THRESHOLD }.sorted { o1, o2 -> o1.second.compareTo(o2.second) }.map { it.first }.toList()
    }

    private fun compareNodesNLP(nodes: Pair<Node, Node>): Double {
        val nameA = nodes.first.name().lowercase()
        val nameB = nodes.second.name().lowercase()
        return LevenshteinDistance().apply(nameA, nameB).toDouble() / max(nameA.length, nameB.length)
    }
}
