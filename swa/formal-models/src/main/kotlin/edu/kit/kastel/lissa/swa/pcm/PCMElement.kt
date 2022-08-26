package edu.kit.kastel.lissa.swa.pcm

import edu.kit.kastel.informalin.framework.models.pcm.PCMComponent
import edu.kit.kastel.informalin.framework.models.pcm.PCMInterface

data class PCMElement(val pcmComponent: PCMComponent?, val pcmInterface: PCMInterface?) {
    constructor(pcmInterface: PCMInterface) : this(null, pcmInterface)
    constructor(pcmComponent: PCMComponent) : this(pcmComponent, null)
}
