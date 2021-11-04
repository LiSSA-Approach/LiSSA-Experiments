package edu.kit.kastel.lissa.sketches.model.elements.uml;

import org.eclipse.collections.api.list.ImmutableList;

import edu.kit.kastel.lissa.sketches.model.elements.IBox;

public interface IUMLThing extends IBox {
    void addAttribute(String attribute);

    void delAttribute(String attribute);

    ImmutableList<String> getAttributes();

    void addMethod(String method);

    void delMethod(String method);

    ImmutableList<String> getMethods();
}
