package edu.kit.kastel.lissa.sketches.model.elements.class_diagram

import edu.kit.kastel.lissa.sketches.model.elements.IBox
import edu.kit.kastel.lissa.sketches.model.elements.generic.ICallable
import edu.kit.kastel.lissa.sketches.model.impl.elements.ClassImpl
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypeMapping
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes

@SketchBoxTypeMapping(type = SketchBoxTypes.CLASS, implementation = ClassImpl::class)
interface IClass : IBox, ICallable
