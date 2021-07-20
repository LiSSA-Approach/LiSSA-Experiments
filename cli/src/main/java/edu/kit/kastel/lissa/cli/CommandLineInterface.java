package edu.kit.kastel.lissa.cli;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class CommandLineInterface {
    private static final Logger logger = LogManager.getLogger(CommandLineInterface.class);

    public static void main(String[] args) {
        logger.info("Hello World");
    }
}
