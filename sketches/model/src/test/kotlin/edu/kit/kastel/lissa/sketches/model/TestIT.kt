package edu.kit.kastel.lissa.sketches.model

import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLAssociation
import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLClass
import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLThing
import edu.kit.kastel.lissa.sketches.model.impl.Box
import edu.kit.kastel.lissa.sketches.model.impl.Relation
import org.eclipse.collections.api.block.procedure.Procedure
import org.junit.jupiter.api.Test

class TestIT {
    @Test
    fun test() {
        val sketch = Sketch()
        sketch.addSketchElement(Box("Component A", 0.8))
        sketch.addSketchElement(Box("Component B", 0.6))
        sketch.changeInterpretation(sketch.getBoxElements()[0], IUMLClass::class)
        sketch.addSketchElement(Relation("Relation 1", 0.5))
        val assoc = sketch.changeInterpretation(sketch.getRelationElements()[0], IUMLAssociation::class)
        
        sketch.getBoxElements(IUMLThing::class).forEach(Procedure { e: IUMLThing -> assoc.addUMLThingToAssociation(e) })
        println(sketch)
    }
}