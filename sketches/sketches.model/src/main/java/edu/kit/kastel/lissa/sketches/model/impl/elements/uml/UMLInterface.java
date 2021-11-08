package edu.kit.kastel.lissa.sketches.model.impl.elements.uml;

import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLInterface;
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes;

public class UMLInterface extends UMLThing implements IUMLInterface {
	private static final long serialVersionUID = 3310996720085779684L;

	public UMLInterface() {
		// NOP
	}

	@Override
	public SketchBoxTypes getCurrentInterpretation() {
		return SketchBoxTypes.INTERFACE;
	}

}
