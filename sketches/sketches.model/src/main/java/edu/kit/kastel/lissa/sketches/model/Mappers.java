package edu.kit.kastel.lissa.sketches.model;

import edu.kit.kastel.lissa.sketches.model.elements.IUMLClass;
import edu.kit.kastel.lissa.sketches.model.impl.BoxElement;
import edu.kit.kastel.lissa.sketches.model.impl.elements.UMLClass;

public final class Mappers {
    private Mappers() {
        throw new IllegalAccessError();
    }

    public static final Mapper<IUMLClass> CLASS = e -> new UMLClass(asElement(e));

    private static BoxElement asElement(ISketchElement element) {
        if (!(element instanceof BoxElement)) {
            throw new IllegalArgumentException("Element " + element + " is no BoxElement!");
        }
        return (BoxElement) element;
    }

    @FunctionalInterface
    public interface Mapper<O extends ISketchElement> {
        O asType(ISketchElement element);
    }
}
