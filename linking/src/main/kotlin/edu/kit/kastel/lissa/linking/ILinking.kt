package edu.kit.kastel.lissa.linking

import edu.kit.kastel.lissa.sketches.model.ISketch
import edu.kit.kastel.lissa.swa.pcm.PalladioComponentModel

interface ILinking {
    fun createLinks(palladioComponentModel: PalladioComponentModel, sketch: ISketch)
}