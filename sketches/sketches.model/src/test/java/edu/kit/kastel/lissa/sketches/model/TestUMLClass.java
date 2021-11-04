package edu.kit.kastel.lissa.sketches.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLClass;
import edu.kit.kastel.lissa.sketches.model.impl.Box;
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes;

public class TestUMLClass {
    private IUMLClass clazz;

    @BeforeEach
    public void setup() {
        this.clazz = SketchBoxTypes.CLASS.map(new Box("TestElement", 0.5), IUMLClass.class);
    }

    @Test
    public void testAddMethod() {
        this.clazz.addMethod("Method");
        assertEquals(1, clazz.getMethods().size());
        assertEquals("Method", clazz.getMethods().get(0));
    }

}
