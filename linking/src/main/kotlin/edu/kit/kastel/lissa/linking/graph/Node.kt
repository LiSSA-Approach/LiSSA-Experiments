package edu.kit.kastel.lissa.linking.graph

data class Node(private val name: String) {
    fun name() = name

    override fun toString() = "($name)"
}
