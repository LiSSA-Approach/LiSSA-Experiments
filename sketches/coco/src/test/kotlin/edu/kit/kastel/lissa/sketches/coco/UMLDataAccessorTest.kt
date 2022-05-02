package edu.kit.kastel.lissa.sketches.coco

import com.fasterxml.jackson.module.kotlin.readValue
import edu.kit.kastel.lissa.sketches.coco.domain.COCOData
import edu.kit.kastel.lissa.sketches.model.elements.class_diagram.IAssociation
import edu.kit.kastel.lissa.sketches.model.elements.class_diagram.IClass
import edu.kit.kastel.lissa.sketches.model.logger
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
        assertThat(data.sketch().getBoxElements(IClass::class)).isNotEmpty
        assertThat(data.sketch().getRelationElements(IAssociation::class)).isNotEmpty
        logger.debug("Loaded Sketch Model from COCO: ")
        logger.debug("Classes: ${data.sketch().getBoxElements(IClass::class).map { c -> c.name() }}")
        logger.debug(
            "\nAssociations:\n${
            data.sketch().getRelationElements(IAssociation::class)
                .joinToString("\n") { c -> "${c.name()}: ${c.connectedElements().map { e -> e.name() }}" }
            }"
        )
    }
}
