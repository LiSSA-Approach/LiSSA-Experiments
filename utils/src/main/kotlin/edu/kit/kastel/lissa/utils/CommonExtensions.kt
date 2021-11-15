package edu.kit.kastel.lissa.utils

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
