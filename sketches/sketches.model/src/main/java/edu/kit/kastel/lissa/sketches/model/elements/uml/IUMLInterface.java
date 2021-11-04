package edu.kit.kastel.lissa.sketches.model.elements.uml;

import edu.kit.kastel.lissa.sketches.model.elements.IBox;
import edu.kit.kastel.lissa.sketches.model.impl.elements.uml.UMLInterface;
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes;
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypeMapping;

@SketchBoxTypeMapping(type = SketchBoxTypes.INTERFACE, implementation = UMLInterface.class)
public interface IUMLInterface extends IUMLThing, IBox {

}
