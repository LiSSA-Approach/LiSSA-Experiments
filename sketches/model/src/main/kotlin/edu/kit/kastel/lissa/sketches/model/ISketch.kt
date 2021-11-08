package edu.kit.kastel.lissa.sketches.model

import edu.kit.kastel.lissa.sketches.model.elements.IBox
import edu.kit.kastel.lissa.sketches.model.elements.IRelation

interface ISketch {
    fun getCurrentInterpretation(element: IBox): IBox?
    fun getCurrentInterpretation(relation: IRelation): IRelation?
}