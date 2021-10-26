package edu.kit.kastel.lissa.sketches.model.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import edu.kit.kastel.lissa.sketches.model.ISketchElement;
import edu.kit.kastel.lissa.sketches.model.SketchElementType;

public class BoxElement implements ISketchElement {

    public static final String NAME_KEY = "name";
    public static final String CONFIDENCE_KEY = "confidence";

    private Map<String, Serializable> data = new HashMap<>();

    public BoxElement(String name, double confidence) {
        addInformation(NAME_KEY, name);
        addInformation(CONFIDENCE_KEY, confidence);
    }

    public BoxElement(Map<String, Serializable> data) {
        this.data = new HashMap<>(data);
    }

    protected BoxElement(BoxElement parent) {
        this.data = parent.data;
    }

    @Override
    public double getCurrentConfidence() {
        return retrieveInformation(CONFIDENCE_KEY, Double.class, 0.0);
    }

    @Override
    public String getName() {
        return retrieveInformation(NAME_KEY, String.class);
    }

    @Override
    public SketchElementType getCurrentInterpretation() {
        return SketchElementType.UNKNOWN;
    }

    protected <I extends Serializable> void addInformation(String key, I information) {
        data.put(key, information);
    }

    protected <I extends Serializable> I retrieveInformation(String key, Class<I> type) {
        Object information = data.get(key);
        if (information == null) {
            return null;
        }
        return type.cast(information);
    }

    protected <I extends Serializable> I retrieveInformation(String key, Class<I> type, I defaultValue) {
        I information = retrieveInformation(key, type);
        return information == null ? defaultValue : information;
    }

}
