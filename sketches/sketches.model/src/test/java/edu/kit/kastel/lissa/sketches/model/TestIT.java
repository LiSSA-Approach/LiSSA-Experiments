package edu.kit.kastel.lissa.sketches.model;

import org.junit.jupiter.api.Test;

import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLClass;
import edu.kit.kastel.lissa.sketches.model.impl.Box;
import edu.kit.kastel.lissa.sketches.model.impl.Relation;

public class TestIT {
    @Test
    public void test() {
        Sketch sketch = getSketch();
        System.out.println(sketch);
    }

    private Sketch getSketch() {
        Sketch sketch = new Sketch();
        sketch.addSketchElement(new Box("Component A", 0.8));
        sketch.addSketchElement(new Box("Component B", 0.6));
        sketch.changeInterpretation(sketch.getBoxElements().get(0), IUMLClass.class);

        sketch.addSketchElement(new Relation("Relation 1", 0.5));

        return sketch;
    }
}
