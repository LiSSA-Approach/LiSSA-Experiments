package edu.kit.kastel.lissa.linking.graph

class Graph {
    private val nodes: MutableMap<String, Node> = mutableMapOf()
    private val edges: MutableMap<Pair<Node, Node>, Edge> = mutableMapOf()

    fun addVertex(id: String): Node {
        if (id in nodes.keys) {
            error("Node $id already exists")
        }
        val node = Node(id)
        nodes[id] = node
        return node
    }

    fun addEdge(id: String, type: String, nodeA: String, nodeB: String) =
        addEdge(id, type, findNode(nodeA), findNode(nodeB))

    fun addEdge(id: String, type: String, nodeA: Node, nodeB: Node): Edge {
        if (nodeA to nodeB in edges.keys) {
            error("Edge ${nodeA to nodeB} already exists")
        }

        val edge = Edge(id, type)
        edges[nodeA to nodeB] = edge
        return edge
    }

    fun nodes(): List<Node> = nodes.values.toList()
    fun edges(): List<Triple<Node, Node, Edge>> = edges.entries.map { Triple(it.key.first, it.key.second, it.value) }

    fun removeNodeWithEdges(node: Node) {
        nodes.remove(node.name())
        edges.entries.filter { it.key.first == node || it.key.second == node }.forEach { edges.remove(it.key) }
    }

    fun edgeOf(nodeA: Node, nodeB: Node) = edges().find { it.first == nodeA && it.second == nodeB }?.third

    fun connected(nodeA: Node, nodeB: Node) =
        edges().any { it.first == nodeA && it.second == nodeB || it.first == nodeB && it.second == nodeA }

    fun connectedNodes(parent: Node, typeOfEdge: String?) = edges() //
        .filter { typeOfEdge == null || it.third.type() == typeOfEdge }
        .filter { it.first == parent || it.second == parent }
        .map { if (it.first == parent) it.second else it.first }

    fun findNode(id: String) = nodes[id] ?: error("Node $id does not exist")

    operator fun contains(node: Node): Boolean = node in nodes.values
    operator fun contains(edge: Edge): Boolean = edge in edges.values
}
