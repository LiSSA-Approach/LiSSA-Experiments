package edu.kit.kastel.lissa.sketches.model;

import java.io.Serializable;
import java.util.HashMap;
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

public class Sketch implements Serializable {
    private static final long serialVersionUID = -5827389829042640316L;

    private Map<Map<String, Serializable>, IBox> boxElements = new HashMap<>();
    private Map<Map<String, Serializable>, IRelation> relationElements = new HashMap<>();

    public void addSketchElement(IBox element) {
        Box elemAsBox = elemAsBox(element);
        this.boxElements.put(elemAsBox.getRawData(), element);
    }

    public void addSketchElement(IRelation element) {
        Relation elemAsRelation = elemAsRelation(element);
        this.relationElements.put(elemAsRelation.getRawData(), element);
    }

    public void delSketchElement(IBox element) {
        Box elemAsBox = elemAsBox(element);
        this.boxElements.remove(elemAsBox.getRawData());
    }

    public void delSketchElement(IRelation element) {
        Relation elemAsRelation = elemAsRelation(element);
        this.relationElements.remove(elemAsRelation.getRawData());
    }

    public <O extends IBox> O changeInterpretation(IBox element, Class<O> clazz) {
        SketchBoxTypes type = SketchBoxTypes.findByClass(clazz);

        Box elemAsBox = elemAsBox(element);
        O newInterpretation = type.map(elemAsBox, clazz);
        this.boxElements.put(elemAsBox.getRawData(), newInterpretation);
        return newInterpretation;
    }

    public <O extends IRelation> O changeInterpretation(IRelation element, Class<O> clazz) {
        SketchRelationTypes type = SketchRelationTypes.findByClass(clazz);

        Relation elemAsBox = elemAsRelation(element);
        O newInterpretation = type.map(elemAsBox, clazz);
        this.relationElements.put(elemAsBox.getRawData(), newInterpretation);
        return newInterpretation;
    }

    public ImmutableList<IBox> getBoxElements() {
        return Lists.immutable.withAll(boxElements.values());
    }

    public ImmutableList<IRelation> getRelationElements() {
        return Lists.immutable.withAll(relationElements.values());
    }

    public ImmutableList<IBox> getElementsByType(SketchBoxTypes type) {
        return getBoxElements().select(e -> e.getCurrentInterpretation() == type);
    }

    public ImmutableList<IRelation> getElementsByType(SketchRelationTypes type) {
        return getRelationElements().select(e -> e.getCurrentInterpretation() == type);
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
    public String toString() {
        String result = "Sketch:\n";

        for (ISketchElement element : getBoxElements().toSortedListBy(ISketchElement::getName)) {
            result += element + "\n";
        }
        result += "\n";

        for (ISketchElement element : getRelationElements().toSortedListBy(ISketchElement::getName)) {
            result += element + "\n";
        }

        return result.trim();
    }
}
