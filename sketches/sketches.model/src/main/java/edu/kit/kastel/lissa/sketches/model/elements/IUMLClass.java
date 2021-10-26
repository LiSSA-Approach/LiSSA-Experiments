package edu.kit.kastel.lissa.sketches.model.elements;

import org.eclipse.collections.api.list.ImmutableList;

import edu.kit.kastel.lissa.sketches.model.ISketchElement;

public interface IUMLClass extends ISketchElement {

    ImmutableList<String> getAttributes();

    ImmutableList<String> getMethods();
}
