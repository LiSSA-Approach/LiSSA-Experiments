package edu.kit.kastel.lissa.sketches.model.impl.elements.uml;

import org.eclipse.collections.api.list.ImmutableList;

import edu.kit.kastel.lissa.sketches.model.elements.IBox;
import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLAssociation;
import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLThing;
import edu.kit.kastel.lissa.sketches.model.impl.Relation;
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypes;

public class UMLAssociation extends Relation implements IUMLAssociation {

	private static final long serialVersionUID = -2742080398473971647L;

	private AssociationType associationType;

	public UMLAssociation() {
		this.associationType = AssociationType.UNKNOWN;
	}

	@Override
	public AssociationType getAssociationType() {
		return this.associationType;
	}

	@Override
	public void setAssociationType(AssociationType newAssociationType) {
		this.associationType = newAssociationType;
	}

	@Override
	public SketchRelationTypes getCurrentInterpretation() {
		return SketchRelationTypes.CLASS_ASSOCIATION;
	}

	@Override
	public void addUMLThingToAssociation(IUMLThing classOrInterface) {
		super.addToAssociation(classOrInterface);
	}

	@Override
	public void delUMLThingFromAssociation(IUMLThing classOrInterface) {
		super.delFromAssociation(classOrInterface);
	}

	@Override
	public void addToAssociation(IBox element) {
		throw new IllegalAccessError("You shall only use #addUMLThingToAssociation");
	}

	@Override
	public void delFromAssociation(IBox element) {
		throw new IllegalAccessError("You shall only use #delUMLThingFromAssociation");
	}

	@Override
	public ImmutableList<IUMLThing> getUMLThingsInAssociation() {
		return this.getConnectedElements().select(e -> e instanceof IUMLThing).collect(e -> (IUMLThing) e);
	}
}
