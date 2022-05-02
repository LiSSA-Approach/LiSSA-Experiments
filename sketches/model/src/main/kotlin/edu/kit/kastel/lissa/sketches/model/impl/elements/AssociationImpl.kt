package edu.kit.kastel.lissa.sketches.model.impl.elements

import edu.kit.kastel.lissa.sketches.model.elements.IBox
import edu.kit.kastel.lissa.sketches.model.elements.ISketchElement
import edu.kit.kastel.lissa.sketches.model.elements.class_diagram.AssociationType
import edu.kit.kastel.lissa.sketches.model.elements.class_diagram.IAssociation
import edu.kit.kastel.lissa.sketches.model.elements.class_diagram.IClass
import edu.kit.kastel.lissa.sketches.model.elements.generic.ICallable
import edu.kit.kastel.lissa.sketches.model.elements.generic.IInterface
import edu.kit.kastel.lissa.sketches.model.impl.RelationImpl
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypes

class AssociationImpl : RelationImpl(), IAssociation {
    private var associationType: AssociationType
    private var associatedCallable: ICallable?

    init {
        associationType = AssociationType.UNKNOWN
        associatedCallable = null
    }

    override fun getAssociationType(): AssociationType = associationType

    override fun setAssociationType(newAssociationType: AssociationType) {
        this.associationType = newAssociationType
    }

    override fun addToAssociation(classOrInterface: IClass) {
        super.addToAssociation(classOrInterface)
    }

    override fun addToAssociation(classOrInterface: IInterface) {
        super.addToAssociation(classOrInterface)
    }

    override fun delFromAssociation(classOrInterface: ISketchElement) {
        if (classOrInterface is IBox)
            super.delFromAssociation(classOrInterface)
    }

    override fun inAssociation(): List<ICallable> {
        return this.connectedElements().filterIsInstance<ICallable>()
    }

    override fun associatedCallable(): ICallable? = associatedCallable

    override fun setAssociatedCallable(callable: ICallable?) {
        this.associatedCallable = callable
    }

    override fun currentInterpretation(): SketchRelationTypes = SketchRelationTypes.CLASS_ASSOCIATION

    override fun addToAssociation(element: IBox): Unit =
        throw IllegalAccessError("You shall only use #addToAssociation(classOrInterface)")

    override fun delFromAssociation(element: IBox): Unit =
        throw IllegalAccessError("You shall only use #delFromAssociation(classOrInterface)")
}
