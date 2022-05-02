package edu.kit.kastel.lissa.sketches.model

import edu.kit.kastel.lissa.sketches.model.elements.class_diagram.IAssociation
import edu.kit.kastel.lissa.sketches.model.elements.class_diagram.IClass
import edu.kit.kastel.lissa.sketches.model.elements.generic.IInterface
import edu.kit.kastel.lissa.sketches.model.impl.BoxImpl
import edu.kit.kastel.lissa.sketches.model.impl.RelationImpl
import org.junit.jupiter.api.Test

internal class TestIT : TestBase() {
    @Test
    fun test() {
        val sketch = Sketch()
        sketch.addSketchElement(BoxImpl("Component A", 0.8))
        sketch.addSketchElement(BoxImpl("Component B", 0.6))
        sketch.changeInterpretation(sketch.getBoxElements()[0], IClass::class)
        sketch.addSketchElement(RelationImpl("Relation 1", 0.5))
        val assoc =
            sketch.changeInterpretation(sketch.getRelationElements()[0], IAssociation::class)

        sketch.getBoxElements(IClass::class).forEach { e: IClass ->
            assoc.addToAssociation(e)
        }
        sketch.getBoxElements(IInterface::class).forEach { e: IInterface ->
            assoc.addToAssociation(e)
        }
        println(sketch)
    }
}
