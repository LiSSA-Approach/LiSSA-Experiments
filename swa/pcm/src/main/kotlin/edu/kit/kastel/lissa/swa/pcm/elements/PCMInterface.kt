package edu.kit.kastel.lissa.swa.pcm.elements

import edu.kit.kastel.informalin.ontology.OntologyInterface
import org.apache.jena.ontology.Individual


data class PCMInterface(
    val id: String,
    val name: String
) {
    companion object {
        fun of(ontology: OntologyInterface, individual: Individual): PCMInterface {
            val id = individual.getProperty(ontology.getProperty(ID_PROPERTY).get()).string
            val name = individual.getProperty(ontology.getProperty(ENTITY_NAME_PROPERTY).get()).string
            return PCMInterface(id, name)
        }
    }
}