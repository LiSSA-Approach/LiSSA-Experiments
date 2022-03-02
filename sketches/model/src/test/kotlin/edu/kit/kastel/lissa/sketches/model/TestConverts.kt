package edu.kit.kastel.lissa.sketches.model

import edu.kit.kastel.lissa.sketches.model.elements.SketchElementType
import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLClass
import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLInterface
import edu.kit.kastel.lissa.sketches.model.impl.Box
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes
import kotlin.test.assertEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestConverts : TestBase() {
    private var boxElement: Box? = null

    @BeforeEach
    fun setup() {
        boxElement = Box("TestElement", 0.5)
        assertEquals(SketchElementType.BOXOID, boxElement!!.elementType())
    }

    @Test
    fun convertToClass() {
        val clazzRaw = SketchBoxTypes.CLASS.map(boxElement!!, IUMLClass::class)
        assertEquals(0.5, clazzRaw.currentConfidence())
        assertEquals("TestElement", clazzRaw.name())
        assertEquals(SketchBoxTypes.CLASS, clazzRaw.currentInterpretation())
        assertEquals(SketchElementType.BOXOID, clazzRaw.elementType())
    }

    @Test
    fun convertIllegal() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            SketchBoxTypes.CLASS.map(boxElement!!, IUMLInterface::class)
        }
    }
}
