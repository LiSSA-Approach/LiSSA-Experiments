package edu.kit.kastel.lissa.sketches.model.elements.uml;

import edu.kit.kastel.lissa.sketches.model.elements.IBox;
import edu.kit.kastel.lissa.sketches.model.impl.elements.uml.UMLClass;
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes;
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypeMapping;

@SketchBoxTypeMapping(type = SketchBoxTypes.CLASS, implementation = UMLClass.class)
public interface IUMLClass extends IUMLThing, IBox {

}
