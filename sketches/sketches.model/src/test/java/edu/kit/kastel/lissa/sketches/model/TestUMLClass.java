package edu.kit.kastel.lissa.sketches.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.kit.kastel.lissa.sketches.model.elements.IUMLClass;
import edu.kit.kastel.lissa.sketches.model.impl.BoxElement;

public class TestUMLClass {
    private IUMLClass clazz;

    @BeforeEach
    public void setup() {
        this.clazz = Mappers.CLASS.asType(new BoxElement("TestElement", 0.5));
    }

    @Test
    public void testAddMethod() {
        this.clazz.addMethod("Method");
        assertEquals(1, clazz.getMethods().size());
        assertEquals("Method", clazz.getMethods().get(0));
    }

}
