package edu.kit.kastel.lissa.swa.pcm.elements

import edu.kit.kastel.informalin.ontology.OntologyInterface
import org.apache.jena.ontology.Individual


data class PCMInterface(
    override val id: String,
    val name: String,
    override val type: PCMElementType = PCMElementType.INTERFACE
) : PCMElement {
    companion object {
        fun of(ontology: OntologyInterface, individual: Individual): PCMInterface {
            val id = individual.pcmId(ontology)
            val name = individual.getProperty(ontology.getProperty(ENTITY_NAME_PROPERTY).get()).string
            return PCMInterface(id, name)
        }
    }
}