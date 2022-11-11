package edu.kit.kastel.lissa.sketches.coco.domain

import com.fasterxml.jackson.module.kotlin.readValue
import edu.kit.kastel.lissa.sketches.coco.TestBase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File

class COCODataTest : TestBase() {

    @Test
    @Disabled("Just for migration purposes. This is not a test")
    fun simpleFilter() {
        val maxAmount = 10
        val data: COCOData = objectMapper.readValue(COCODataTest::class.java.getResourceAsStream("/sample/train.json")!!)
        val images = data.images.filter { i -> i.id < maxAmount }
        val annotations = data.annotations.filter { a -> a.imageId < maxAmount }
        val newData = COCOData(images, annotations, data.categories)
        objectMapper.writeValue(File("./src/test/resources/sample/sample_coco_small.json"), newData)
    }

    @Test
    fun simpleLoad() {
        val data: COCOData = objectMapper.readValue(cocoFile)
        assertThat(data).isNotNull
        assertThat(data::images).isNotEmpty()
        assertThat(data::annotations).isNotEmpty()
        assertThat(data::categories).isNotEmpty()
    }
}
