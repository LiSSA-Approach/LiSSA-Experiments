package edu.kit.kastel.lissa.linking

import edu.kit.kastel.lissa.sketches.model.elements.SketchElement
import edu.kit.kastel.lissa.swa.pcm.PCMElement

data class Link(val pcmElement: PCMElement, val sketchElement: SketchElement, var confidence: Double = 0.0)
