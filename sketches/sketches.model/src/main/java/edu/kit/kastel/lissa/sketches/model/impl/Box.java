package edu.kit.kastel.lissa.sketches.model.impl;

import java.io.Serializable;
import java.util.Map;

import edu.kit.kastel.lissa.sketches.model.elements.IBox;
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes;

public class Box extends AbstractElement implements IBox {
    private static final long serialVersionUID = 7524099797275769182L;

    public Box(String name, double confidence) {
        super(name, confidence);
    }

    public Box(Map<String, Serializable> data) {
        super(data);
    }

    protected Box(Box parent) {
        super(parent);
    }

    @Override
    public SketchBoxTypes getCurrentInterpretation() {
        return SketchBoxTypes.UNKNOWN;
    }

    @Override
    public String toString() {
        return String.format("%s [name=%s, confidence=%s, interpretation=%s]", //
                getClass().getSimpleName(), getName(), getCurrentConfidence(), getCurrentInterpretation());
    }

}
