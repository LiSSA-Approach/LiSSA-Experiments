package edu.kit.kastel.lissa.sketches.model

import edu.kit.kastel.lissa.sketches.model.elements.SketchElement
import edu.kit.kastel.lissa.sketches.model.elements.wrapper.ClassNode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TestUMLClass : TestBase() {
    private lateinit var clazz: ClassNode

    @BeforeEach
    fun setup() {
        val sketch = Sketch()
        val element = SketchElement()
        element.init(sketch)
        clazz = ClassNode(element)
    }

    @Test
    fun testAddMethod() {
        clazz.addMethod("Method")
        Assertions.assertEquals(1, clazz.methods().size)
        Assertions.assertEquals("Method", clazz.methods()[0])
    }
}
