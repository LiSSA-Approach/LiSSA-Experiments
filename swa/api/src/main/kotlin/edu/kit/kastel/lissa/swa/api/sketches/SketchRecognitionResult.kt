package edu.kit.kastel.lissa.swa.api.sketches

data class SketchRecognitionResult(val boxes: List<Box>, val textBoxes: List<TextBox>, val edges: List<Edge>)
