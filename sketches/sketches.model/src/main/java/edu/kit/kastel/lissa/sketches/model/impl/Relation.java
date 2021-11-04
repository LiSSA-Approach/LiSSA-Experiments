package edu.kit.kastel.lissa.sketches.model.impl;

import java.io.Serializable;
import java.util.Map;

import org.eclipse.collections.api.list.ImmutableList;

import edu.kit.kastel.lissa.sketches.model.elements.IRelation;
import edu.kit.kastel.lissa.sketches.model.elements.ISketchElement;
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypes;
import edu.kit.kastel.lissa.sketches.util.ListWrapper;

public class Relation extends AbstractElement implements IRelation {
    private static final long serialVersionUID = 454341326127129061L;

    public static final String RELATIONS_KEY = "relations";

    public Relation(String name, double confidence) {
        super(name, confidence);
    }

    public Relation(Map<String, Serializable> data) {
        super(data);
    }

    protected Relation(Box parent) {
        super(parent);
    }

    @Override
    public ImmutableList<ISketchElement> getConnectedElements() {
        @SuppressWarnings("unchecked")
        ListWrapper<ISketchElement> list = retrieveInformation(RELATIONS_KEY, ListWrapper.class, ListWrapper.empty());
        return list.toImmutable();
    }

    @Override
    public SketchRelationTypes getCurrentInterpretation() {
        return SketchRelationTypes.UNKNOWN;
    }

    @Override
    public String toString() {
        return String.format("%s [name=%s, confidence=%s, interpretation=%s]", //
                getClass().getSimpleName(), getName(), getCurrentConfidence(), getCurrentInterpretation());
    }

}
