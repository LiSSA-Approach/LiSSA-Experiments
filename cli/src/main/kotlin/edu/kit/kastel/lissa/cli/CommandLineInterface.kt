package edu.kit.kastel.lissa.cli

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

val logger: Logger = LogManager.getLogger("LiSSA-CLI")

fun main() {
    logger.info("Starting CLI")
}