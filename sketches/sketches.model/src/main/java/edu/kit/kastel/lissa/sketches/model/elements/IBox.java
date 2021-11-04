package edu.kit.kastel.lissa.sketches.model.elements;

import edu.kit.kastel.lissa.sketches.model.impl.Box;
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes;
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypeMapping;

@SketchBoxTypeMapping(type = SketchBoxTypes.UNKNOWN, implementation = Box.class)
public interface IBox extends ISketchElement {
    SketchBoxTypes getCurrentInterpretation();
}
