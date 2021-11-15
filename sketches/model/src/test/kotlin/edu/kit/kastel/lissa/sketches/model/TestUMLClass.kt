package edu.kit.kastel.lissa.sketches.model

import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLClass
import edu.kit.kastel.lissa.sketches.model.impl.Box
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestUMLClass : TestBase() {
    private var clazz: IUMLClass? = null

    @BeforeEach
    fun setup() {
        clazz = SketchBoxTypes.CLASS.map(Box("TestElement", 0.5), IUMLClass::class)
    }

    @Test
    fun testAddMethod() {
        clazz!!.addMethod("Method")
        Assertions.assertEquals(1, clazz!!.methods().size)
        Assertions.assertEquals("Method", clazz!!.methods()[0])
    }
}