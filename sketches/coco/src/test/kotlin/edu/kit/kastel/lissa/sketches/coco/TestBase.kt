package edu.kit.kastel.lissa.sketches.coco

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import edu.kit.kastel.lissa.sketches.coco.domain.COCODataTest
import java.io.InputStream
import org.junit.jupiter.api.BeforeEach

open class TestBase {

    protected val objectMapper: ObjectMapper = createObjectMapper()

    protected var cocoFile: InputStream? = null

    @BeforeEach
    fun loadFile() {
        cocoFile = COCODataTest::class.java.getResourceAsStream("/sample/sample_coco_small.json")!!
    }

    private fun createObjectMapper(): ObjectMapper {
        val objectMapper: ObjectMapper =
            ObjectMapper()
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.setVisibility(
            objectMapper
                .serializationConfig
                .defaultVisibilityChecker //
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY) //
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE) //
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE) //
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE))
        return objectMapper.registerKotlinModule()
    }
}
