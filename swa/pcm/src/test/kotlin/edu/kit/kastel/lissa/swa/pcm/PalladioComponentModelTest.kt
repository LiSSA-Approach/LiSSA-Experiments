package edu.kit.kastel.lissa.swa.pcm

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class PalladioComponentModelTest : TestBase() {

    private lateinit var palladioComponentModel: PalladioComponentModel

    @BeforeEach
    fun setUp() {
        palladioComponentModel =
            PalladioComponentModel("src/test/resources/benchmark/mediastore/original_model/ms.repository")
    }

    @Test
    fun testExistence() {
        assertTrue { palladioComponentModel.interfaces().isNotEmpty() }
        assertTrue { palladioComponentModel.components().isNotEmpty() }
        palladioComponentModel.components().map { c ->
            assertTrue { c.provided.isNotEmpty() }
        }
    }
}
