package edu.kit.kastel.lissa.linking.graphmatcher

import edu.kit.kastel.lissa.linking.graph.Graph
import edu.kit.kastel.lissa.linking.graph.Node
import edu.kit.kastel.lissa.linking.graphmatcher.selectors.INodeSelector
import edu.kit.kastel.lissa.utils.with
import org.slf4j.LoggerFactory

class GraphMatcher(private val nodeSelector: INodeSelector, private val graphA: Graph, private val graphB: Graph) {

    companion object {
        private val logger = LoggerFactory.getLogger(GraphMatcher::class.java)
    }

    fun matchUntilNoSeeds(seeds: List<Pair<Node, Node>>): List<Pair<Node, Node>> {
        val matches = mutableListOf<Pair<Node, Node>>()

        for (seed in seeds) {
            if (seed.first !in graphA || seed.second !in graphB) {
                continue
            }

            val matched = match(seed.first, seed.second)
            matched.forEach { graphA.removeNodeWithEdges(it.first) }
            matched.forEach { graphB.removeNodeWithEdges(it.second) }
            matches += matched
        }

        return matches
    }

    fun match(seedA: Node, seedB: Node): List<Pair<Node, Node>> {
        val mappings = mutableListOf<Map<Node, Node>>()
        for (childOfSeed in graphA.connectedNodes(seedA, null)) match(childOfSeed, mapOf(seedA to seedB), mappings)

        if (mappings.isEmpty()) {
            return listOf()
        }
        val bestMapping = findBestMapping(mappings)
        for (m in mappings) logger.info("Mapping: $m")
        logger.info("Best Mapping: $bestMapping")
        return bestMapping
    }

    private fun findBestMapping(mappings: MutableList<Map<Node, Node>>): List<Pair<Node, Node>> {
        var bestScore = -1
        var bestMapping = mappings[0]

        for (mapping in mappings) {
            val score = mapping.size
            if (score > bestScore) {
                bestScore = score
                bestMapping = mapping
            }
        }

        return bestMapping.entries.map { it.key to it.value }
    }

    private fun match(
        nodeToMatchInA: Node, activeMappingAtoB: Map<Node, Node>, finalMappings: MutableList<Map<Node, Node>>
    ) {
        val possibleMatchings = nodeSelector.identifyPossibleNodes(graphA, graphB, nodeToMatchInA, activeMappingAtoB)
        // Match With a node
        for (selectedNode in possibleMatchings) {
            matchNodes(nodeToMatchInA, selectedNode, activeMappingAtoB, finalMappings)
        }

        // No match for seed
        ignoreNode(nodeToMatchInA, activeMappingAtoB, finalMappings)
    }


    private fun matchNodes(
        nodeToMatchInA: Node, selectedNodeInB: Node, activeMappingAtoB: Map<Node, Node>, finalMappings: MutableList<Map<Node, Node>>
    ) {
        val activeMappingAtoBNew = activeMappingAtoB.with(nodeToMatchInA, selectedNodeInB)

        for (child in graphA.connectedNodes(nodeToMatchInA, null)) {
            if (child !in activeMappingAtoB.keys) {
                match(child, activeMappingAtoBNew, finalMappings)
            }
        }

        // Ignore all other children
        finalMappings.add(activeMappingAtoBNew)
    }

    private fun ignoreNode(
        nodeToMatchInA: Node, activeMappingAtoB: Map<Node, Node>, finalMappings: MutableList<Map<Node, Node>>
    ) {
        // End recursion
        finalMappings.add(activeMappingAtoB)
    }
}
