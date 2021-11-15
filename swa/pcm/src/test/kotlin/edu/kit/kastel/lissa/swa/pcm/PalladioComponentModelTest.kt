package edu.kit.kastel.lissa.swa.pcm

import org.junit.jupiter.api.BeforeEach

class PalladioComponentModelTest {

    private var palladioComponentModel: PalladioComponentModel? = null

    @BeforeEach
    fun setUp() {
        palladioComponentModel = PalladioComponentModel("src/test/resources/models/mediastore.owl")
    }


}