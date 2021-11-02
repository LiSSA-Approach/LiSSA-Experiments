package edu.kit.kastel.lissa.sketches.model;

import org.junit.jupiter.api.Test;

import edu.kit.kastel.lissa.sketches.model.elements.IUMLClass;
import edu.kit.kastel.lissa.sketches.model.impl.BoxElement;

public class TestIT {
    @Test
    public void test() {
        Sketch sketch = getSketch();
        System.out.println(sketch);
    }

    private Sketch getSketch() {
        Sketch sketch = new Sketch();
        sketch.addSketchElement(new BoxElement("Component A", 0.8));
        sketch.addSketchElement(new BoxElement("Component B", 0.6));
        sketch.changeInterpretation(sketch.getElements().get(0), IUMLClass.class);
        return sketch;
    }
}
