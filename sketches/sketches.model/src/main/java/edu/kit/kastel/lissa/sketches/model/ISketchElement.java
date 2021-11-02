package edu.kit.kastel.lissa.sketches.model;

import java.io.Serializable;

public interface ISketchElement extends Serializable {
    SketchElementType getCurrentInterpretation();

    double getCurrentConfidence();

    String getName();
}
