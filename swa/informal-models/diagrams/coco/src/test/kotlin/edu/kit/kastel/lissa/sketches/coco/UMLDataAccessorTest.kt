package edu.kit.kastel.lissa.sketches.coco

import com.fasterxml.jackson.module.kotlin.readValue
import edu.kit.kastel.lissa.sketches.coco.domain.COCOData
import edu.kit.kastel.lissa.sketches.model.elements.SketchElementType
import edu.kit.kastel.lissa.sketches.model.elements.wrapper.AssociationNode
import edu.kit.kastel.lissa.sketches.model.elements.wrapper.ClassNode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UMLDataAccessorTest : TestBase() {

    private lateinit var data: UMLDataAccessor

    @BeforeEach
    fun simpleLoad() {
        val multipleSketches: COCOData = objectMapper.readValue(cocoFile)
        data = UMLDataAccessor(multipleSketches.image(0))
    }

    @Test
    fun loadElements() {
        assertThat(data.sketch().getElements(SketchElementType.CLASS, ClassNode::class.java)).isNotEmpty
        assertThat(data.sketch().getElements(SketchElementType.ASSOCIATION, AssociationNode::class.java)).isNotEmpty
        logger.debug("Loaded Sketch Model from COCO: ")
        logger.debug(
            "Classes: ${
            data.sketch().getElements(SketchElementType.CLASS, ClassNode::class.java).map { c -> c.name() }
            }"
        )
        logger.debug(
            "\nAssociations:\n${
            data.sketch().getElements(SketchElementType.ASSOCIATION, AssociationNode::class.java)
                .joinToString("\n") { c -> "${c.name()}: ${c.referencedClasses().map { e -> e.name() }}" }
            }"
        )
    }
}
