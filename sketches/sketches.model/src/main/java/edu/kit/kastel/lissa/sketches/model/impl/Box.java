package edu.kit.kastel.lissa.sketches.model.impl;

import edu.kit.kastel.lissa.sketches.model.elements.IBox;
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes;

public class Box extends AbstractElement implements IBox {
	private static final long serialVersionUID = 7524099797275769182L;

	public Box() {
		// NOP
	}

	public Box(String name, double confidence) {
		this.setName(name);
		this.setConfidence(confidence);
	}

	@Override
	public SketchBoxTypes getCurrentInterpretation() {
		return SketchBoxTypes.UNKNOWN;
	}

	@Override
	public String toString() {
		return String.format("%s [name=%s, confidence=%s, interpretation=%s]", //
				this.getClass().getSimpleName(), this.getName(), this.getCurrentConfidence(), this.getCurrentInterpretation());
	}

}
