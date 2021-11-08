package edu.kit.kastel.lissa.sketches.model.impl;

import org.eclipse.collections.api.list.ImmutableList;

import edu.kit.kastel.lissa.sketches.model.ISketch;
import edu.kit.kastel.lissa.sketches.model.elements.IBox;
import edu.kit.kastel.lissa.sketches.model.elements.IRelation;
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypes;
import edu.kit.kastel.lissa.sketches.util.ListWrapper;

public class Relation extends AbstractElement implements IRelation {
	private static final long serialVersionUID = 454341326127129061L;

	private ListWrapper<IBox> connectedElements = ListWrapper.empty();

	public Relation() {
		// NOP
	}

	public Relation(String name, double confidence) {
		this.setName(name);
		this.setConfidence(confidence);
	}

	protected ListWrapper<IBox> getConnectedElementsMod() {
		return this.connectedElements;
	}

	@Override
	public ImmutableList<IBox> getConnectedElements() {
		return this.getConnectedElementsMod().toImmutable();
	}

	@Override
	public SketchRelationTypes getCurrentInterpretation() {
		return SketchRelationTypes.UNKNOWN;
	}

	@Override
	public void reloadElementMappings(ISketch sketch) {
		var newElements = ListWrapper.wrap(this.getConnectedElementsMod().collect(e -> sketch.getCurrentInterpretation(e)));
		this.connectedElements = newElements;
	}

	@Override
	public void addToAssociation(IBox element) {
		this.getConnectedElementsMod().add(element);
	}

	@Override
	public void delFromAssociation(IBox element) {
		this.getConnectedElementsMod().remove(element);
	}

	@Override
	public String toString() {
		return String.format("%s [name=%s, confidence=%s, interpretation=%s, elements=%s]", //
				this.getClass().getSimpleName(), this.getName(), this.getCurrentConfidence(), this.getCurrentInterpretation(),
				this.getConnectedElements().collect(IBox::getName));
	}
}
