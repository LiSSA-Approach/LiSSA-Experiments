package edu.kit.kastel.lissa.linking.graph

data class Edge(private val name: String, private val type: String) {
    fun name() = name
    fun type() = type
}
