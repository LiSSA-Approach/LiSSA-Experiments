package edu.kit.kastel.lissa.sketches.model.impl.elements.uml;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLThing;
import edu.kit.kastel.lissa.sketches.model.impl.Box;
import edu.kit.kastel.lissa.sketches.util.ListWrapper;

abstract class UMLThing extends Box implements IUMLThing {
    private static final long serialVersionUID = -8954737446536608236L;

    private String attrKey;
    private String methKey;

    protected UMLThing(Box element, String attrKey, String methKey) {
        super(element);
        this.attrKey = attrKey;
        this.methKey = methKey;
    }

    @Override
    public void addAttribute(String attribute) {
        getAttributesMod().add(attribute);
    }

    @Override
    public void delAttribute(String attribute) {
        getAttributesMod().remove(attribute);
    }

    @Override
    public ImmutableList<String> getAttributes() {
        return getAttributesMod().toImmutable();
    }

    @Override
    public ImmutableList<String> getMethods() {
        return getMethodsMod().toImmutable();
    }

    @Override
    public void addMethod(String method) {
        getMethodsMod().add(method);
    }

    @Override
    public void delMethod(String method) {
        getMethodsMod().remove(method);
    }

    @SuppressWarnings("unchecked")
    private MutableList<String> getAttributesMod() {
        return retrieveInformation(attrKey, ListWrapper.class, ListWrapper.empty());
    }

    @SuppressWarnings("unchecked")
    private MutableList<String> getMethodsMod() {
        return retrieveInformation(methKey, ListWrapper.class, ListWrapper.empty());
    }
}
