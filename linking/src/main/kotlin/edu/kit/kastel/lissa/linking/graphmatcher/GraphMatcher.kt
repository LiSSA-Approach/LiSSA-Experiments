package edu.kit.kastel.lissa.linking.graphmatcher

import edu.kit.kastel.lissa.linking.graph.Graph
import edu.kit.kastel.lissa.linking.graph.Node
import edu.kit.kastel.lissa.utils.with
import org.slf4j.LoggerFactory

class GraphMatcher(private val graphA: Graph, private val graphB: Graph) {

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
        for (m in mappings)
            logger.info("Mapping: $m")
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
        seedInA: Node,
        activeMappingAtoB: Map<Node, Node>,
        mappings: MutableList<Map<Node, Node>>
    ) {
        val possibleMatchings = possibleMatchingNodes(seedInA, activeMappingAtoB)
        // Match With a node
        for (selectedNode in possibleMatchings) {
            matchNodes(seedInA, selectedNode, activeMappingAtoB, mappings)
        }

        // No match for seed
        ignoreNode(seedInA, activeMappingAtoB, mappings)
    }

    private fun possibleMatchingNodes(nodeInA: Node, activeMapping: Map<Node, Node>): List<Node> {
        val parentsWithMappingInA = activeMapping.keys.filter { graphA.connected(it, nodeInA) }

        val parentsInBWithEdgeType =
            parentsWithMappingInA.map { activeMapping[it]!! to graphA.edgeOf(it, nodeInA)?.type() }
                .filter { it.second != null }
        val possibleMatches = parentsInBWithEdgeType.flatMap { graphB.connectedNodes(it.first, it.second) }

        val possibleMatchesWithoutAlreadyMatchedNodes = possibleMatches.filter { it !in activeMapping.values }
        logger.debug("Found ${possibleMatchesWithoutAlreadyMatchedNodes.size} possible matching nodes in GraphB for ${nodeInA.name()}")
        return possibleMatchesWithoutAlreadyMatchedNodes
    }

    private fun matchNodes(
        seed: Node,
        selectedNode: Node,
        activeMappingAtoB: Map<Node, Node>,
        mappings: MutableList<Map<Node, Node>>
    ) {
        val activeMappingAtoBNew = activeMappingAtoB.with(seed, selectedNode)

        for (child in graphA.connectedNodes(seed, null)) {
            if (child !in activeMappingAtoB.keys) {
                match(child, activeMappingAtoBNew, mappings)
            }
        }

        // Ignore all other children
        mappings.add(activeMappingAtoBNew)
    }

    private fun ignoreNode(
        seedInA: Node,
        activeMappingAtoB: Map<Node, Node>,
        mappings: MutableList<Map<Node, Node>>
    ) {
        // End recursion
        mappings.add(activeMappingAtoB)
    }
}
