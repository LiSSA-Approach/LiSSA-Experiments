package edu.kit.kastel.lissa.sketches.coco

import edu.kit.kastel.lissa.sketches.coco.domain.COCOAnnotation
import edu.kit.kastel.lissa.sketches.coco.domain.COCOData

class UMLDataAccessor(private val data: COCOData) {
    companion object {
        const val CLASS_CATEGORY = "ClassNode"
    }

    fun classes(): List<COCOAnnotation> {
        val classCategory = data.categories.find { c -> c.name == CLASS_CATEGORY }!!
        return data.annotations.filter { a -> a.categoryId == classCategory.id }
    }


}