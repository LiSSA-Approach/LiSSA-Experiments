package edu.kit.kastel.lissa.linking.graphmatcher

import edu.kit.kastel.lissa.linking.graph.Graph
import edu.kit.kastel.lissa.linking.graph.Node
import edu.kit.kastel.lissa.utils.with

class GraphMatcher(private val graphA: Graph, private val graphB: Graph) {

    // TODO Later allow links of edges as well
    private val linksOfNodes = mutableListOf<Pair<Node, Node>>()

    fun addLinks(nodeA: Node, nodeB: Node) {
        // TODO add information about confidence here later
        if (nodeA !in graphA) {
            error("nodeA is not in graphA")
        }
        if (nodeB !in graphB) {
            error("nodeB is not in graphB")
        }

        if (nodeA to nodeB !in linksOfNodes) {
            linksOfNodes.add(nodeA to nodeB)
        }
    }

    fun links() = linksOfNodes.toList()

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
        for (childOfSeed in graphA.connectedNodes(seedA))
            match(childOfSeed, mapOf(seedA to seedB), mappings)

        if (mappings.isEmpty()) {
            return listOf()
        }
        val bestMapping = findBestMapping(mappings)
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

    private fun possibleMatchingNodes(seedInA: Node, activeMapping: Map<Node, Node>): List<Node> {
        val parentsWithMappingInA =
            graphA.connectedNodes(seedInA).filter { it in activeMapping.keys }

        val parentsInB = parentsWithMappingInA.map { activeMapping[it]!! }
        val possibleChildren = parentsInB.flatMap { graphB.connectedNodes(it) }
        val possibleChildrenWithoutMatched = possibleChildren.filter { it !in activeMapping.values }
        return possibleChildrenWithoutMatched
    }

    private fun matchNodes(
        seed: Node,
        selectedNode: Node,
        activeMappingAtoB: Map<Node, Node>,
        mappings: MutableList<Map<Node, Node>>
    ) {
        val activeMappingAtoBNew = activeMappingAtoB.with(seed, selectedNode)

        for (child in graphA.connectedNodes(seed)) {
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
