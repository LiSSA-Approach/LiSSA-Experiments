package edu.kit.kastel.lissa.swa.documentation.recognition.model

data class SketchRecognitionResult(val boxes: List<Box>, val textBoxes: List<TextBox>, val edges: List<Edge>)
