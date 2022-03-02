package edu.kit.kastel.lissa.sketches.coco.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class COCOCategory(
    val supercategory: String,
    val id: Int,
    val name: String,
    @JsonProperty("longname") val displayName: String
)
