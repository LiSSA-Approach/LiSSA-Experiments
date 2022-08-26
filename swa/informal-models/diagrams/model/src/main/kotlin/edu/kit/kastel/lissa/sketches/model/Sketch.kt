package edu.kit.kastel.lissa.sketches.model

import com.fasterxml.jackson.annotation.JsonProperty
import edu.kit.kastel.lissa.sketches.model.elements.SketchElement
import edu.kit.kastel.lissa.sketches.model.elements.SketchElementType
import edu.kit.kastel.lissa.sketches.model.elements.wrapper.GenericNode
import edu.kit.kastel.lissa.sketches.model.elements.wrapper.ISketchNode
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.reflect.Constructor
import java.lang.reflect.Modifier

class Sketch : ISketch {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(Sketch::class.java)
    }

    @JsonProperty
    private val elements: MutableList<SketchElement> = mutableListOf()

    internal fun init() {
        elements.forEach { it.init(this) }
    }

    override fun findById(uuid: String): SketchElement = elements.find { it.uuid() == uuid }!!

    override fun addSketchElement(name: String, confidence: Double, type: SketchElementType): SketchElement {
        val sketchElement = SketchElement()
        sketchElement.init(this)
        val node = GenericNode(sketchElement, type)
        node.setName(name)
        node.setConfidence(confidence)
        elements.add(sketchElement)
        return sketchElement
    }

    override fun <E : ISketchNode> getElements(type: SketchElementType, typeAsClass: Class<E>): List<E> {
        val elementsOfType = elements.filter { it.interpretation() == type }
        val nodes: List<E> = elementsOfType.map { create(it, typeAsClass) }
        logger.debug("Created ${nodes.size} nodes of type $typeAsClass")
        return nodes
    }

    private fun <E> create(sketchElement: SketchElement, typeAsClass: Class<E>): E {
        try {
            val constructor: Constructor<in E> = typeAsClass.getDeclaredConstructor(SketchElement::class.java)
            if (!Modifier.isPublic(constructor.modifiers)) {
                error("Constructor for ${typeAsClass.simpleName} is not public")
            }
            return typeAsClass.cast(constructor.newInstance(sketchElement))
        } catch (e: Exception) {
            logger.error(e.message, e)
            error("Cannot find constructor using ${SketchElement::class.java.simpleName}")
        }
    }

    override fun toString(): String {
        return "Sketch {\n${elements.joinToString("\n") { "\t$it" }}\n}"
    }
}
