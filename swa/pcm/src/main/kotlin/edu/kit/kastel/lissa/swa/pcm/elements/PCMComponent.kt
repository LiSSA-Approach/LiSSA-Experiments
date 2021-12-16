package edu.kit.kastel.lissa.swa.pcm.elements

import edu.kit.kastel.informalin.ontology.OntologyInterface
import org.apache.jena.ontology.Individual

data class PCMComponent(
    val id: String,
    val name: String,
    val providedInterfaceIds: List<String>,
    val requiredInterfaceIds: List<String>
) {
    companion object {
        fun of(ontology: OntologyInterface, individual: Individual): PCMComponent {
            val id = individual.pcmId(ontology)
            val name = individual.getProperty(ontology.getProperty(ENTITY_NAME_PROPERTY).get()).string

            val providedInterfaceIds = handleRoles(
                individual,
                ontology,
                "providedRoles_InterfaceProvidingEntity_-_InterfaceProvidingEntity",
                "providedInterface__OperationProvidedRole_-_OperationProvidedRole"
            )

            val requiredInterfaceIds = handleRoles(
                individual,
                ontology,
                "requiredRoles_InterfaceRequiringEntity_-_InterfaceRequiringEntity",
                "requiredInterface__OperationRequiredRole_-_OperationRequiredRole"
            )

            return PCMComponent(id, name, providedInterfaceIds, requiredInterfaceIds)
        }

        private fun handleRoles(
            component: Individual,
            ontology: OntologyInterface,
            roleEntityId: String,
            interfaceProperty: String
        ): List<String> {
            
            val roleEntities = component.listProperties(
                ontology.getProperty(roleEntityId).get()
            ).mapWith { s -> ontology.getIndividualByIri(s.`object`.asResource().uri).get() }.toList()

            val interfaceIds = roleEntities.map { r ->
                r.getProperty(
                    ontology.getProperty(interfaceProperty).get()
                ).`object`.asResource().uri
            }.map { iri ->
                ontology.getIndividualByIri(iri).get().pcmId(ontology)
            }

            return interfaceIds

        }
    }
}


