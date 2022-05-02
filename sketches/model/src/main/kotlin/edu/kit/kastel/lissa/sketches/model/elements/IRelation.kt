package edu.kit.kastel.lissa.sketches.model.elements

import edu.kit.kastel.lissa.sketches.model.ISketch
import edu.kit.kastel.lissa.sketches.model.impl.RelationImpl
import edu.kit.kastel.lissa.sketches.model.types.SketchElementType
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypeMapping
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypes

@SketchRelationTypeMapping(type = SketchRelationTypes.UNKNOWN, implementation = RelationImpl::class)
interface IRelation : ISketchElement {
    fun currentInterpretation(): SketchRelationTypes

    fun connectedElements(): List<IBox>
    fun addToAssociation(element: IBox)
    fun delFromAssociation(element: IBox)

    fun reloadElementMappings(sketch: ISketch)

    override fun elementType(): SketchElementType = SketchElementType.RELATION
}
