package edu.kit.kastel.lissa.sketches.model;

import java.io.Serializable;
import java.util.IdentityHashMap;
import java.util.Map;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

import edu.kit.kastel.lissa.sketches.model.elements.IBox;
import edu.kit.kastel.lissa.sketches.model.elements.IRelation;
import edu.kit.kastel.lissa.sketches.model.elements.ISketchElement;
import edu.kit.kastel.lissa.sketches.model.impl.Box;
import edu.kit.kastel.lissa.sketches.model.impl.Relation;
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes;
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypes;

public class Sketch implements Serializable, ISketch {
	private static final long serialVersionUID = -5827389829042640316L;

	private IdentityHashMap<Map<String, Serializable>, IBox> boxElements = new IdentityHashMap<>();
	private IdentityHashMap<Map<String, Serializable>, IRelation> relationElements = new IdentityHashMap<>();

	public void addSketchElement(IBox element) {
		Box elemAsBox = this.elemAsBox(element);
		this.boxElements.put(elemAsBox.getRawData(), element);
	}

	public void addSketchElement(IRelation element) {
		Relation elemAsRelation = this.elemAsRelation(element);
		this.relationElements.put(elemAsRelation.getRawData(), element);
	}

	public void delSketchElement(IBox element) {
		Box elemAsBox = this.elemAsBox(element);
		this.boxElements.remove(elemAsBox.getRawData());
	}

	public void delSketchElement(IRelation element) {
		Relation elemAsRelation = this.elemAsRelation(element);
		this.relationElements.remove(elemAsRelation.getRawData());
	}

	public <O extends IBox> O changeInterpretation(IBox element, Class<O> clazz) {
		SketchBoxTypes type = SketchBoxTypes.findByClass(clazz);

		Box elemAsBox = this.elemAsBox(element);
		O newInterpretation = type.map(elemAsBox, clazz);
		this.boxElements.put(elemAsBox.getRawData(), newInterpretation);
		return newInterpretation;
	}

	public <O extends IRelation> O changeInterpretation(IRelation element, Class<O> clazz) {
		SketchRelationTypes type = SketchRelationTypes.findByClass(clazz);

		Relation elemAsBox = this.elemAsRelation(element);
		O newInterpretation = type.map(elemAsBox, clazz);
		this.relationElements.put(elemAsBox.getRawData(), newInterpretation);
		return newInterpretation;
	}

	public ImmutableList<IBox> getBoxElements() {
		return Lists.immutable.withAll(this.boxElements.values());
	}

	public <B extends IBox> ImmutableList<B> getBoxElements(Class<B> type) {
		return this.getBoxElements().select(e -> type.isAssignableFrom(e.getClass())).collect(type::cast);
	}

	public ImmutableList<IRelation> getRelationElements() {
		return Lists.immutable.withAll(this.relationElements.values());
	}

	public <R extends IRelation> ImmutableList<R> getRelationElements(Class<R> type) {
		return this.getRelationElements().select(e -> type.isAssignableFrom(e.getClass())).collect(type::cast);
	}

	public ImmutableList<IBox> getElementsByType(SketchBoxTypes type) {
		return this.getBoxElements().select(e -> e.getCurrentInterpretation() == type);
	}

	public ImmutableList<IRelation> getElementsByType(SketchRelationTypes type) {
		return this.getRelationElements().select(e -> e.getCurrentInterpretation() == type);
	}

	private Box elemAsBox(ISketchElement element) {
		if (!(element instanceof Box)) {
			throw new IllegalArgumentException("You can only use elements based on " + Box.class + " with " + Sketch.class);
		}
		return (Box) element;
	}

	private Relation elemAsRelation(IRelation element) {
		if (!(element instanceof Relation)) {
			throw new IllegalArgumentException("You can only use elements based on " + Relation.class + " with " + Sketch.class);
		}
		return (Relation) element;
	}

	@Override
	public IBox getCurrentInterpretation(IBox element) {
		return this.boxElements.get(this.elemAsBox(element).getRawData());
	}

	@Override
	public IRelation getCurrentInterpretation(IRelation relation) {
		return this.relationElements.get(this.elemAsRelation(relation).getRawData());
	}

	@Override
	public String toString() {
		String result = "Sketch:\n";

		for (ISketchElement element : this.getBoxElements().toSortedListBy(ISketchElement::getName)) {
			result += element + "\n";
		}
		result += "\n";

		for (ISketchElement element : this.getRelationElements().toSortedListBy(ISketchElement::getName)) {
			result += element + "\n";
		}

		return result.trim();
	}
}
