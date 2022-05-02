package edu.kit.kastel.lissa.sketches.model.elements.component_diagram

import edu.kit.kastel.lissa.sketches.model.elements.IBox
import edu.kit.kastel.lissa.sketches.model.elements.generic.ICallable
import edu.kit.kastel.lissa.sketches.model.impl.elements.ComponentImpl
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypeMapping
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes

@SketchBoxTypeMapping(type = SketchBoxTypes.COMPONENT, implementation = ComponentImpl::class)
interface IComponent : IBox, ICallable
