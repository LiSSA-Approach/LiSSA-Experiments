package edu.kit.kastel.lissa.sketches.model

import edu.kit.kastel.lissa.utils.enableDebug
import org.junit.jupiter.api.BeforeAll

open class TestBase {
    companion object {
        @BeforeAll
        @JvmStatic
        fun setupLogger() {
            enableDebug()
        }
    }
}