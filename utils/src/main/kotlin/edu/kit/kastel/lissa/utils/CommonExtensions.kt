package edu.kit.kastel.lissa.utils

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.Logger

private var debug = false

fun enableDebug() {
    debug = true
}

fun Logger.syncLogLevel() = setLogLevel(if (debug) Level.DEBUG else Level.INFO)

/**
 * Set the log level of a logger.
 * @param[level] the new log level
 */
fun Logger.setLogLevel(level: Level) {
    try {
        val logger = this as org.apache.logging.log4j.core.Logger
        logger.level = level
    } catch (e: Exception) {
        println("Error while setting log level: ${e.message}")
    }
}

fun createObjectMapper(): ObjectMapper {
    val objectMapper: ObjectMapper = ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    objectMapper.setVisibility(
        objectMapper.serializationConfig.defaultVisibilityChecker //
            .withFieldVisibility(JsonAutoDetect.Visibility.ANY) //
            .withGetterVisibility(JsonAutoDetect.Visibility.NONE) //
            .withSetterVisibility(JsonAutoDetect.Visibility.NONE) //
            .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
    )
    return objectMapper.registerKotlinModule()
}
