package edu.kit.kastel.lissa.sketches.coco.domain

data class COCOData(
    val images: List<COCOImage>,
    val annotations: List<COCOAnnotation>,
    val categories: List<COCOCategory>
)
