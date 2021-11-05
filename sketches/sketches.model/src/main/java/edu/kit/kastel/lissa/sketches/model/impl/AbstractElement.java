package edu.kit.kastel.lissa.sketches.model.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import edu.kit.kastel.lissa.sketches.model.elements.ISketchElement;

abstract class AbstractElement implements ISketchElement {
    private static final long serialVersionUID = -7524099797275769182L;

    public static final String NAME_KEY = "name";
    public static final String CONFIDENCE_KEY = "confidence";

    private Map<String, Serializable> data = new HashMap<>();

    protected AbstractElement(String name, double confidence) {
        storeInformation(NAME_KEY, name);
        storeInformation(CONFIDENCE_KEY, confidence);
    }

    protected AbstractElement(Map<String, Serializable> data) {
        this.data = new HashMap<>(data);
    }

    protected AbstractElement(AbstractElement parent) {
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

    protected <I extends Serializable> void storeInformation(String key, I information) {
        data.put(key, information);
    }

    protected <I extends Serializable> I retrieveInformation(String key, Class<I> type) {
        Object information = data.get(key);
        if (information == null) {
            return null;
        }
        return type.cast(information);
    }

    public Map<String, Serializable> getRawData() {
        return data;
    }

    protected <I extends Serializable> I retrieveInformation(String key, Class<I> type, I defaultValue) {
        I information = retrieveInformation(key, type);
        if (information == null) {
            data.put(key, defaultValue);
        }
        return information == null ? defaultValue : information;
    }

    @Override
    public String toString() {
        return String.format("%s [name=%s, confidence=%s]", //
                getClass().getSimpleName(), getName(), getCurrentConfidence());
    }

}
