package edu.kit.kastel.lissa.sketches.model.elements.uml

import edu.kit.kastel.lissa.sketches.model.elements.IBox
import edu.kit.kastel.lissa.sketches.model.impl.elements.uml.UMLInterface
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypeMapping
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes

@SketchBoxTypeMapping(type = SketchBoxTypes.INTERFACE, implementation = UMLInterface::class)
interface IUMLInterface : IUMLThing, IBox
