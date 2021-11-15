package edu.kit.kastel.lissa.swa.pcm

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class PalladioComponentModelTest : TestBase() {

    private var palladioComponentModel: PalladioComponentModel? = null

    @BeforeEach
    fun setUp() {
        palladioComponentModel = PalladioComponentModel("src/test/resources/models/mediastore.owl")
    }

    @Test
    fun testExistence() {
        assertTrue { palladioComponentModel!!.interfaces().isNotEmpty() }
        assertTrue { palladioComponentModel!!.components().isNotEmpty() }
    }

}