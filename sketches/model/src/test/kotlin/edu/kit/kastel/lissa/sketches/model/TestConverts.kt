package edu.kit.kastel.lissa.sketches.model

import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLClass
import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLInterface
import edu.kit.kastel.lissa.sketches.model.impl.Box
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes
import org.junit.Assert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestConverts {
    private var boxElement: Box? = null

    @BeforeEach
    fun setup() {
        boxElement = Box("TestElement", 0.5)
    }

    @Test
    fun convertToClass() {
        val clazzRaw = SketchBoxTypes.CLASS.map(boxElement!!, IUMLClass::class)
        Assert.assertTrue(clazzRaw is IUMLClass)
    }

    @Test
    fun convertIllegal() {
        Assertions.assertThrows(IllegalArgumentException::class.java) { SketchBoxTypes.CLASS.map(boxElement!!, IUMLInterface::class) }
    }
}