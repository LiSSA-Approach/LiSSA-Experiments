package edu.kit.kastel.lissa.sketches.model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLClass;
import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLInterface;
import edu.kit.kastel.lissa.sketches.model.impl.Box;
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes;

public class TestConverts {
	private Box boxElement;

	@BeforeEach
	public void setup() {
		this.boxElement = new Box("TestElement", 0.5);
	}

	@Test
	public void convertToClass() {
		IUMLClass clazzRaw = SketchBoxTypes.CLASS.map(this.boxElement, IUMLClass.class);
		assertTrue(clazzRaw instanceof IUMLClass);
	}

	@Test
	public void convertIllegal() {
		assertThrows(IllegalArgumentException.class, () -> SketchBoxTypes.CLASS.map(this.boxElement, IUMLInterface.class));
	}

}
