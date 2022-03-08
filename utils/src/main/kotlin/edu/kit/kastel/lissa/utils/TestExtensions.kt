package edu.kit.kastel.lissa.utils

fun runInCI() = "true" == System.getenv("CI")
