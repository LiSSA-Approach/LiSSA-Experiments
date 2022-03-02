package edu.kit.kastel.lissa.sketches.model.elements.uml

import edu.kit.kastel.lissa.sketches.model.elements.IBox
import edu.kit.kastel.lissa.sketches.model.impl.elements.uml.UMLClass
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypeMapping
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes

@SketchBoxTypeMapping(type = SketchBoxTypes.CLASS, implementation = UMLClass::class)
interface IUMLClass : IUMLThing, IBox
