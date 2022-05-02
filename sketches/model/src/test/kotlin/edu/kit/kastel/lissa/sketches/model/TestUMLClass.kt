package edu.kit.kastel.lissa.sketches.model

import edu.kit.kastel.lissa.sketches.model.elements.class_diagram.IClass
import edu.kit.kastel.lissa.sketches.model.impl.BoxImpl
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TestUMLClass : TestBase() {
    private lateinit var clazz: IClass

    @BeforeEach
    fun setup() {
        clazz = SketchBoxTypes.CLASS.map(BoxImpl("TestElement", 0.5), IClass::class)
    }

    @Test
    fun testAddMethod() {
        clazz.addMethod("Method")
        Assertions.assertEquals(1, clazz.methods().size)
        Assertions.assertEquals("Method", clazz.methods()[0])
    }
}
