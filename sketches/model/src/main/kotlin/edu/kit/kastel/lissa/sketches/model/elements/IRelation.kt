package edu.kit.kastel.lissa.sketches.model.elements

import edu.kit.kastel.lissa.sketches.model.ISketch
import edu.kit.kastel.lissa.sketches.model.impl.Relation
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypeMapping
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypes

@SketchRelationTypeMapping(type = SketchRelationTypes.UNKNOWN, implementation = Relation::class)
interface IRelation : ISketchElement {
    fun currentInterpretation(): SketchRelationTypes

    fun connectedElements(): List<IBox>
    fun addToAssociation(element: IBox)
    fun delFromAssociation(element: IBox)

    fun reloadElementMappings(sketch: ISketch)
}