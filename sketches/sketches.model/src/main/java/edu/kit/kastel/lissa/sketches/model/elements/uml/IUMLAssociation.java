package edu.kit.kastel.lissa.sketches.model.elements.uml;

import org.eclipse.collections.api.list.ImmutableList;

import edu.kit.kastel.lissa.sketches.model.elements.IRelation;
import edu.kit.kastel.lissa.sketches.model.impl.elements.uml.UMLAssociation;
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypeMapping;
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypes;

@SketchRelationTypeMapping(type = SketchRelationTypes.CLASS_ASSOCIATION, implementation = UMLAssociation.class)
public interface IUMLAssociation extends IRelation {
	enum AssociationType {
		UNKNOWN, GENERALIZATION, REALIZATION, AGGREGATION, COMPOSITION, N_ARY_ASSOCIATION
	}

	AssociationType getAssociationType();

	void setAssociationType(AssociationType newAssociationType);

	void addUMLThingToAssociation(IUMLThing classOrInterface);

	void delUMLThingFromAssociation(IUMLThing classOrInterface);

	ImmutableList<IUMLThing> getUMLThingInAssociation(IUMLThing classOrInterface);

}
