package edu.kit.kastel.lissa.sketches.model.elements;

import org.eclipse.collections.api.list.ImmutableList;

public interface IUMLThing {
    void addAttribute(String attribute);

    void delAttribute(String attribute);

    ImmutableList<String> getAttributes();

    void addMethod(String method);

    void delMethod(String method);

    ImmutableList<String> getMethods();
}
