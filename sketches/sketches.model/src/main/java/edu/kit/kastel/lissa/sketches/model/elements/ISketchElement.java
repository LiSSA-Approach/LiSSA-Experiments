package edu.kit.kastel.lissa.sketches.model.elements;

import java.io.Serializable;

public interface ISketchElement extends Serializable {

    double getCurrentConfidence();

    String getName();
}
