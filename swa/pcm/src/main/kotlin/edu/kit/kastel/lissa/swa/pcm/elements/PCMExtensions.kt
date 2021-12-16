package edu.kit.kastel.lissa.swa.pcm.elements

import edu.kit.kastel.informalin.ontology.OntologyInterface
import org.apache.jena.ontology.Individual

fun Individual.pcmId(ontology: OntologyInterface): String =
    this.getProperty(ontology.getProperty(ID_PROPERTY).get()).string