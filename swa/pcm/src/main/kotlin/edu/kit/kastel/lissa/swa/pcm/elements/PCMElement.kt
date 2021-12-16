package edu.kit.kastel.lissa.swa.pcm.elements

interface PCMElement {
    val id: String
    val type: PCMElementType
}

enum class PCMElementType {
    COMPONENT, INTERFACE
}