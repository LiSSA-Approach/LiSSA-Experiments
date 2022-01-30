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
        val basicComponentClass = ontology.getClass("BasicComponent").get()
        val basicComponents = ontology.getIndividualsOfClass(basicComponentClass)
        basicComponents.forEach { components.add(PCMComponent.of(ontology, it)) }
    }

    private fun loadInterfaces() {
        val interfaceClass = ontology.getClass("OperationInterface").get()
        val foundInterfaces = ontology.getIndividualsOfClass(interfaceClass)
        foundInterfaces.forEach { interfaces.add(PCMInterface.of(ontology, it)) }
    }

    fun interfaces() = interfaces.toList()
    fun components() = components.toList()

    fun requiredInterfaces(component: PCMComponent) =
        component.requiredInterfaceIds.map { id -> interfaces.find { it.id == id }!! }

    fun providedInterfaces(component: PCMComponent) =
        component.providedInterfaceIds.map { id -> interfaces.find { it.id == id }!! }
}
