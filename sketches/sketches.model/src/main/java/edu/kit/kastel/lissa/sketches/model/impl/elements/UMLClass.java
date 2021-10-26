package edu.kit.kastel.lissa.sketches.model.impl.elements;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

import edu.kit.kastel.lissa.sketches.model.SketchElementType;
import edu.kit.kastel.lissa.sketches.model.elements.IUMLClass;
import edu.kit.kastel.lissa.sketches.model.impl.BoxElement;

public class UMLClass extends BoxElement implements IUMLClass {

    public UMLClass(BoxElement element) {
        super(element);
    }

    @Override
    public SketchElementType getCurrentInterpretation() {
        return SketchElementType.CLASS;
    }

    @Override
    public ImmutableList<String> getAttributes() {
        // TODO Implement me ..
        return Lists.immutable.empty();
    }

    @Override
    public ImmutableList<String> getMethods() {
        // TODO Implement me ..
        return Lists.immutable.empty();
    }
}
