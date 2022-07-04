package edu.kit.kastel.lissa.swa.pcm

import edu.kit.kastel.lissa.utils.enableDebug
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class PalladioComponentModelTest {

    private lateinit var palladioComponentModel: PalladioComponentModel

    @BeforeEach
    fun setUp() {
        enableDebug()
        palladioComponentModel =
            PalladioComponentModel("../benchmark/mediastore/pcm/ms.repository")
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
