package edu.kit.kastel.lissa.sketches.coco.domain

data class COCOData(
    val images: List<COCOImage>,
    val annotations: List<COCOAnnotation>,
    val categories: List<COCOCategory>
) {
    fun image(imageId: Int): COCOData {
        val images = this.images.filter { i -> i.id == imageId }
        val annotations = this.annotations.filter { a -> a.imageId == imageId }
        return COCOData(images, annotations, categories)
    }
}
