package edu.kit.kastel.lissa.sketches.model;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.kit.kastel.lissa.sketches.model.elements.IUMLClass;
import edu.kit.kastel.lissa.sketches.model.impl.BoxElement;

public class TestConverts {
    private BoxElement boxElement;

    @BeforeEach
    public void setup() {
        this.boxElement = new BoxElement("TestElement", 0.5);
    }

    @Test
    public void convertToClass() {
        IUMLClass clazz = Mappers.CLASS.asType(boxElement);
        assertTrue(clazz instanceof IUMLClass);

        IUMLClass clazzRaw = SketchElementType.CLASS.map(boxElement, IUMLClass.class);
        assertTrue(clazzRaw instanceof IUMLClass);
    }
}
