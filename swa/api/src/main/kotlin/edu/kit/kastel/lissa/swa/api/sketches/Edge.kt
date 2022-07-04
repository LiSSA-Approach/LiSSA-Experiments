package edu.kit.kastel.lissa.swa.api.sketches

import com.fasterxml.jackson.annotation.JsonProperty

data class Edge(
    @JsonProperty val uuid: String,
    @JsonProperty val connectedBoxes: List<String>,
    @Transient val texts: MutableList<TextBox> = mutableListOf()
)
