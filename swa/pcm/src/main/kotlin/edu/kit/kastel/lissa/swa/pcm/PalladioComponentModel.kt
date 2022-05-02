package edu.kit.kastel.lissa.swa.pcm

import edu.kit.kastel.informalin.framework.models.pcm.PCMComponent
import edu.kit.kastel.informalin.framework.models.pcm.PCMInterface
import edu.kit.kastel.informalin.framework.models.pcm.PCMModel
import edu.kit.kastel.informalin.framework.models.pcm.PCMRepository
import java.io.File

class PalladioComponentModel {
    private val pcmRepository: PCMRepository

    private val interfaces: MutableList<PCMInterface> = mutableListOf()
    private val components: MutableList<PCMComponent> = mutableListOf()

    constructor(pcmRepositoryFilePath: String) : this(File(pcmRepositoryFilePath))

    constructor(pcmRepositoryFile: File) {
        val pcmModel = PCMModel(pcmRepositoryFile)
        this.pcmRepository = pcmModel.repository
        this.interfaces.addAll(pcmRepository.interfaces)
        this.components.addAll(pcmRepository.components)
    }

    fun interfaces() = interfaces.toList()
    fun components() = components.toList()
}
