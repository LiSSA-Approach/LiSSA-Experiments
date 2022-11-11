package edu.kit.kastel.lissa.sketches.coco

import com.fasterxml.jackson.databind.ObjectMapper
import edu.kit.kastel.lissa.sketches.coco.domain.COCODataTest
import edu.kit.kastel.lissa.utils.createObjectMapper
import edu.kit.kastel.lissa.utils.setLogLevel
import org.junit.jupiter.api.BeforeEach
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.spi.LocationAwareLogger
import java.io.InputStream

open class TestBase {

    protected val logger: Logger = LoggerFactory.getLogger(this::class.java)
    protected val objectMapper: ObjectMapper = createObjectMapper()

    protected lateinit var cocoFile: InputStream

    @BeforeEach
    fun setLogger() {
        logger.setLogLevel(LocationAwareLogger.DEBUG_INT)
    }

    @BeforeEach
    fun loadFile() {
        cocoFile = COCODataTest::class.java.getResourceAsStream("/sample/sample_coco_small.json")!!
    }
}
