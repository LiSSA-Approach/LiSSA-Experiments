package edu.kit.kastel.lissa.sketches.model.elements.uml

import edu.kit.kastel.lissa.sketches.model.elements.IRelation
import edu.kit.kastel.lissa.sketches.model.impl.elements.uml.UMLAssociation
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypeMapping
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypes

@SketchRelationTypeMapping(type = SketchRelationTypes.CLASS_ASSOCIATION, implementation = UMLAssociation::class)
interface IUMLAssociation : IRelation {
    enum class AssociationType {
        UNKNOWN, GENERALIZATION, REALIZATION, AGGREGATION, COMPOSITION, N_ARY_ASSOCIATION
    }

    fun getAssociationType(): AssociationType?
    fun setAssociationType(newAssociationType: AssociationType)
    fun addUMLThingToAssociation(classOrInterface: IUMLThing)
    fun delUMLThingFromAssociation(classOrInterface: IUMLThing)
    fun umlThingsInAssociation(): List<IUMLThing>
}