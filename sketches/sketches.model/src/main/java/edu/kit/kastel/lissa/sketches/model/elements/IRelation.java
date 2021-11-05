package edu.kit.kastel.lissa.sketches.model.elements;

import org.eclipse.collections.api.list.ImmutableList;

import edu.kit.kastel.lissa.sketches.model.ISketch;
import edu.kit.kastel.lissa.sketches.model.impl.Relation;
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypeMapping;
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypes;

@SketchRelationTypeMapping(type = SketchRelationTypes.UNKNOWN, implementation = Relation.class)
public interface IRelation extends ISketchElement {
	SketchRelationTypes getCurrentInterpretation();

	void addToAssociation(IBox element);

	void delFromAssociation(IBox element);

	ImmutableList<IBox> getConnectedElements();

	void reloadElementMappings(ISketch sketch);
}
