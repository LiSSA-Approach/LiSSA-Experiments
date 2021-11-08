package edu.kit.kastel.lissa.sketches.model.elements.uml

import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypeMapping
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypes
import edu.kit.kastel.lissa.sketches.model.impl.elements.uml.UMLAssociation
import edu.kit.kastel.lissa.sketches.model.elements.IRelation
import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLAssociation.AssociationType
import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLThing
import org.eclipse.collections.api.list.ImmutableList
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypeMapping
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes
import edu.kit.kastel.lissa.sketches.model.impl.elements.uml.UMLClass
import edu.kit.kastel.lissa.sketches.model.elements.IBox
import edu.kit.kastel.lissa.sketches.model.impl.elements.uml.UMLInterface

@SketchBoxTypeMapping(type = SketchBoxTypes.INTERFACE, implementation = UMLInterface::class)
interface IUMLInterface : IUMLThing, IBox