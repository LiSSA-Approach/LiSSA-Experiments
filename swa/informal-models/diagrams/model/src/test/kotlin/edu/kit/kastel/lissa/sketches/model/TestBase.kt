package edu.kit.kastel.lissa.sketches.model

import edu.kit.kastel.lissa.utils.enableDebug
import org.junit.jupiter.api.BeforeAll

internal open class TestBase {
    companion object {
        @BeforeAll
        @JvmStatic
        fun setupLogger() {
            enableDebug()
        }
    }
}
