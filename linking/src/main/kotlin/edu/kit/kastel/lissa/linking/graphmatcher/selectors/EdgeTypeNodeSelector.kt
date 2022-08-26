package edu.kit.kastel.lissa.linking.graphmatcher.selectors

import edu.kit.kastel.lissa.linking.graph.Graph
import edu.kit.kastel.lissa.linking.graph.Node
import org.slf4j.LoggerFactory

class EdgeTypeNodeSelector : INodeSelector {
    companion object {
        private val logger = LoggerFactory.getLogger(EdgeTypeNodeSelector::class.java)
    }

    override fun identifyPossibleNodes(graphA: Graph, graphB: Graph, nodeToMatchInA: Node, activeMappingAtoB: Map<Node, Node>): List<Node> {
        val parentsWithMappingInA = activeMappingAtoB.keys.filter { possibleParentInA -> graphA.connected(possibleParentInA, nodeToMatchInA) }

        val parentsInBWithEdgeType = parentsWithMappingInA //
            .map { parentInA -> activeMappingAtoB[parentInA]!! to graphA.edgeOf(parentInA, nodeToMatchInA)?.type() } //
            .filter { it.second != null }
        val possibleMatches = parentsInBWithEdgeType.flatMap { (parentInB, edgeType) -> graphB.connectedNodes(parentInB, edgeType) }

        val possibleMatchesWithoutAlreadyMatchedNodes = possibleMatches.filter { possibleMatchInB -> possibleMatchInB !in activeMappingAtoB.values }
        logger.debug("Found ${possibleMatchesWithoutAlreadyMatchedNodes.size} possible matching nodes in GraphB for ${nodeToMatchInA.name()}")
        return possibleMatchesWithoutAlreadyMatchedNodes
    }
}
