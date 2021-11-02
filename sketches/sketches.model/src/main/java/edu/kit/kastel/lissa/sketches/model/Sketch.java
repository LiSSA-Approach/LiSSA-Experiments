package edu.kit.kastel.lissa.sketches.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

import edu.kit.kastel.lissa.sketches.model.impl.BoxElement;

public class Sketch implements Serializable {
    private static final long serialVersionUID = -5827389829042640316L;

    private Map<Map<String, Serializable>, ISketchElement> elements = new HashMap<>();

    public void addSketchElement(ISketchElement element) {
        BoxElement elemAsBox = elemAsBox(element);
        this.elements.put(elemAsBox.getRawData(), element);
    }

    public void delSketchElement(ISketchElement element) {
        BoxElement elemAsBox = elemAsBox(element);
        this.elements.remove(elemAsBox.getRawData());
    }

    public <O extends ISketchElement> O changeInterpretation(ISketchElement element, Class<O> clazz) {
        SketchElementType type = SketchElementType.findByClass(clazz);

        BoxElement elemAsBox = elemAsBox(element);
        O newInterpretation = type.map(elemAsBox, clazz);
        this.elements.put(elemAsBox.getRawData(), newInterpretation);
        return newInterpretation;
    }

    public ImmutableList<ISketchElement> getElements() {
        return Lists.immutable.withAll(elements.values());
    }

    public ImmutableList<ISketchElement> getElementsByType(SketchElementType type) {
        return getElements().select(e -> e.getCurrentInterpretation() == type);
    }

    private BoxElement elemAsBox(ISketchElement element) {
        if (!(element instanceof BoxElement)) {
            throw new IllegalArgumentException("You can only use elements based on " + BoxElement.class + " with " + Sketch.class);
        }
        return (BoxElement) element;
    }

    @Override
    public String toString() {
        String result = "Sketch:\n";

        for (ISketchElement element : getElements().toSortedListBy(ISketchElement::getName)) {
            result += element + "\n";
        }

        return result.trim();
    }
}
