package edu.kit.kastel.lissa.sketches.model;

public interface ISketchElement {
    SketchElementType getCurrentInterpretation();

    double getCurrentConfidence();

    String getName();
}
