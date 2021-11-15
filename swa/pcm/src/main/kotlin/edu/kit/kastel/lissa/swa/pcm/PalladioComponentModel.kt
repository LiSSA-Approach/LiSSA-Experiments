package edu.kit.kastel.lissa.swa.pcm

import edu.kit.kastel.informalin.ontology.OntologyConnector
import edu.kit.kastel.informalin.ontology.OntologyInterface
import edu.kit.kastel.lissa.swa.pcm.elements.PCMComponent
import edu.kit.kastel.lissa.swa.pcm.elements.PCMInterface

class PalladioComponentModel {
    private val ontology: OntologyInterface

    private val interfaces: MutableList<PCMInterface> = mutableListOf()
    private val components: MutableList<PCMComponent> = mutableListOf()

    constructor(path: String) : this(OntologyConnector(path))

    constructor(ontology: OntologyInterface) {
        this.ontology = ontology
        this.load()
    }

    private fun load() {
        loadInterfaces()
        loadComponents()
    }

    private fun loadComponents() {
        
    }

    private fun loadInterfaces() {

    }


}