package edu.kit.kastel.lissa.swa.pcm.elements

import edu.kit.kastel.informalin.ontology.OntologyInterface
import org.apache.jena.ontology.Individual

data class PCMComponent(
    val id: String,
    val name: String
) {
    companion object {
        fun of(ontology: OntologyInterface, individual: Individual): PCMComponent {
            val id = individual.getProperty(ontology.getProperty(ID_PROPERTY).get()).string
            val name = individual.getProperty(ontology.getProperty(ENTITY_NAME_PROPERTY).get()).string
            return PCMComponent(id, name)
        }
    }
}