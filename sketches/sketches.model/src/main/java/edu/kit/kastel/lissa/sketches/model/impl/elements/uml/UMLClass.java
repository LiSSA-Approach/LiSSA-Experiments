package edu.kit.kastel.lissa.sketches.model.impl.elements.uml;

import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLClass;
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes;

public class UMLClass extends UMLThing implements IUMLClass {
	private static final long serialVersionUID = 3310996720085779684L;

	public UMLClass() {
		// NOP
	}

	@Override
	public SketchBoxTypes getCurrentInterpretation() {
		return SketchBoxTypes.CLASS;
	}

}
