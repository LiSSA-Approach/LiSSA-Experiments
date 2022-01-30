package edu.kit.kastel.lissa.sketches.model.impl.elements.uml

import edu.kit.kastel.lissa.sketches.model.elements.IBox
import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLAssociation
import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLAssociation.AssociationType
import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLThing
import edu.kit.kastel.lissa.sketches.model.impl.Relation
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypes

class UMLAssociation : Relation(), IUMLAssociation {
    private var associationType: AssociationType

    init {
        associationType = AssociationType.UNKNOWN
    }

    override fun getAssociationType(): AssociationType = associationType
    override fun setAssociationType(newAssociationType: AssociationType) {
        associationType = newAssociationType
    }

    override fun currentInterpretation() = SketchRelationTypes.CLASS_ASSOCIATION

    override fun umlThingsInAssociation() = connectedElements().filterIsInstance<IUMLThing>()
    override fun addUMLThingToAssociation(classOrInterface: IUMLThing) =
        super.addToAssociation(classOrInterface)
    override fun delUMLThingFromAssociation(classOrInterface: IUMLThing) =
        super.delFromAssociation(classOrInterface)

    override fun addToAssociation(element: IBox): Unit =
        throw IllegalAccessError("You shall only use #addUMLThingToAssociation")
    override fun delFromAssociation(element: IBox): Unit =
        throw IllegalAccessError("You shall only use #delUMLThingFromAssociation")
}
