package edu.kit.kastel.lissa.sketches.coco

import com.fasterxml.jackson.databind.ObjectMapper
import edu.kit.kastel.lissa.sketches.coco.domain.COCODataTest
import edu.kit.kastel.lissa.sketches.model.logger
import edu.kit.kastel.lissa.utils.createObjectMapper
import edu.kit.kastel.lissa.utils.setLogLevel
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.slf4j.spi.LocationAwareLogger
import java.io.InputStream

open class TestBase {

    protected val objectMapper: ObjectMapper = createObjectMapper()

    protected lateinit var cocoFile: InputStream

    companion object {
        @BeforeAll
        @JvmStatic
        fun loggerStateDebug() {
            logger.setLogLevel(LocationAwareLogger.DEBUG_INT)
        }
    }

    @BeforeEach
    fun loadFile() {
        cocoFile = COCODataTest::class.java.getResourceAsStream("/sample/sample_coco_small.json")!!
    }
}
