package edu.kit.kastel.lissa.linking.graphmatcher.selectors

import edu.kit.kastel.lissa.linking.graph.Graph
import edu.kit.kastel.lissa.linking.graph.Node

interface INodeSelector {
    fun identifyPossibleNodes(graphA: Graph, graphB: Graph, nodeToMatchInA: Node, activeMappingAtoB: Map<Node, Node>): List<Node>
}
