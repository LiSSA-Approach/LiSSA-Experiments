package edu.kit.kastel.lissa.sketches.model.elements.generic

import edu.kit.kastel.lissa.sketches.model.elements.IBox
import edu.kit.kastel.lissa.sketches.model.impl.elements.InterfaceImpl
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypeMapping
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes

@SketchBoxTypeMapping(type = SketchBoxTypes.INTERFACE, implementation = InterfaceImpl::class)
interface IInterface : IBox, ICallable
