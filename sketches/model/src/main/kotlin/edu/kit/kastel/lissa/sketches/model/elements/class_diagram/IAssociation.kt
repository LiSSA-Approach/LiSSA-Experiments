package edu.kit.kastel.lissa.sketches.model.elements.class_diagram

import edu.kit.kastel.lissa.sketches.model.elements.IRelation
import edu.kit.kastel.lissa.sketches.model.elements.ISketchElement
import edu.kit.kastel.lissa.sketches.model.elements.generic.ICallable
import edu.kit.kastel.lissa.sketches.model.elements.generic.IInterface
import edu.kit.kastel.lissa.sketches.model.impl.elements.AssociationImpl
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypeMapping
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypes

@SketchRelationTypeMapping(type = SketchRelationTypes.CLASS_ASSOCIATION, implementation = AssociationImpl::class)
interface IAssociation : IRelation {
    fun getAssociationType(): AssociationType
    fun setAssociationType(newAssociationType: AssociationType)
    fun addToAssociation(classOrInterface: IClass)
    fun addToAssociation(classOrInterface: IInterface)
    fun delFromAssociation(classOrInterface: ISketchElement)
    fun inAssociation(): List<ICallable>
    fun associatedCallable(): ICallable?
    fun setAssociatedCallable(callable: ICallable?)
}
