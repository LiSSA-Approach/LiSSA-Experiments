package edu.kit.kastel.lissa.sketches.model

import edu.kit.kastel.lissa.sketches.model.elements.class_diagram.IClass
import edu.kit.kastel.lissa.sketches.model.elements.generic.IInterface
import edu.kit.kastel.lissa.sketches.model.impl.BoxImpl
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes
import edu.kit.kastel.lissa.sketches.model.types.SketchElementType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TestConverts : TestBase() {
    private lateinit var boxElement: BoxImpl

    @BeforeEach
    fun setup() {
        boxElement = BoxImpl("TestElement", 0.5)
        assertEquals(SketchElementType.BOX, boxElement.elementType())
    }

    @Test
    fun convertToClass() {
        val clazzRaw = SketchBoxTypes.CLASS.map(boxElement, IClass::class)
        assertEquals(0.5, clazzRaw.currentConfidence())
        assertEquals("TestElement", clazzRaw.name())
        assertEquals(SketchBoxTypes.CLASS, clazzRaw.currentInterpretation())
        assertEquals(SketchElementType.BOX, clazzRaw.elementType())
    }

    @Test
    fun convertIllegal() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            SketchBoxTypes.CLASS.map(boxElement, IInterface::class)
        }
    }
}
