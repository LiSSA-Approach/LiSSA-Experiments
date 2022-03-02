package edu.kit.kastel.lissa.cli

import edu.kit.kastel.lissa.utils.enableDebug
import edu.kit.kastel.lissa.utils.syncLogLevel
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import kotlinx.cli.required
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

val logger: Logger = LogManager.getLogger("LiSSA-CLI")

fun main(args: Array<String>) {
    val parser = ArgParser("LiSSA-CLI")
    val inputOntology by parser.option(ArgType.String, shortName = "i", description = "Path to the Model Ontology").required()
    val inputSketch by parser.option(ArgType.String, shortName = "s", description = "Path to the Sketch").required()
    val debug by parser.option(ArgType.Boolean, shortName = "d", description = "indicator of debugging").default(false)
    parser.parse(args)
    if (debug) enableDebug()
    startLiSSA(inputOntology, inputSketch)
}

private fun startLiSSA(inputOntology: String, inputSketch: String) {
    logger.syncLogLevel()
    logger.info("Starting LiSSA-CLI @$inputOntology @$inputSketch")
}
