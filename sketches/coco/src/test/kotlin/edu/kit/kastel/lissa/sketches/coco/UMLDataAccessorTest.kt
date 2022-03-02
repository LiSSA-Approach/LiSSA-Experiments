package edu.kit.kastel.lissa.sketches.coco

import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UMLDataAccessorTest : TestBase() {

    private var data: UMLDataAccessor? = null

    @BeforeEach
    fun simpleLoad() {
        data = UMLDataAccessor(objectMapper.readValue(cocoFile!!))
    }

    @Test
    fun loadClasses() {
        assertThat(data!!.classes()).isNotEmpty
    }
}
