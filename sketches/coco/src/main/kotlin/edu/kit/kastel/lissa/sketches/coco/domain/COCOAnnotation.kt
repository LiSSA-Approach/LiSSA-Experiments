package edu.kit.kastel.lissa.sketches.coco.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class COCOAnnotation(
    // Generic COCO Annotations
    val id: Int,
    @JsonProperty("image_id") val imageId: Int,
    @JsonProperty("category_id") val categoryId: Int,
    @JsonProperty("bbox") val boundingBox: List<Int>,
    val area: Double,
    @JsonProperty("keypoints") val keypoints: List<Int>,
    @JsonProperty("bpmn_id") val umlId: String,

    // Properties for Labels
    @JsonProperty("belongs_to") val belongsTo: Int,
    @JsonProperty("label_type") val labelType: String?,
    val category: String,
    val name: String?,

    // Properties for Arrows
    val directed: Boolean?,
    @JsonProperty("has_arrowhead") val hasArrowhead: Boolean?,
    @JsonProperty("arrow_prev") val arrowStart: Int?,
    @JsonProperty("arrow_next") val arrowEnd: Int?,
    // List<Pair<Double, Double>>
    @JsonProperty("waypoints") val waypoints: List<List<Double>>?
)
