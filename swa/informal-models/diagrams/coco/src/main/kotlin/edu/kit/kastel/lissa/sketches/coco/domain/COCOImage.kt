package edu.kit.kastel.lissa.sketches.coco.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class COCOImage(
    @JsonProperty("file_name") val fileName: String,
    val height: Int,
    val width: Int,
    val id: Int
)
