package edu.kit.kastel.lissa.sketches.model;

import edu.kit.kastel.lissa.sketches.model.elements.IBox;
import edu.kit.kastel.lissa.sketches.model.elements.IRelation;

public interface ISketch {
	IBox getCurrentInterpretation(IBox element);

	IRelation getCurrentInterpretation(IRelation relation);
}
