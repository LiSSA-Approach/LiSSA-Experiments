package edu.kit.kastel.lissa.sketches.model.impl

import edu.kit.kastel.lissa.sketches.model.ISketch
import edu.kit.kastel.lissa.sketches.model.elements.IBox
import edu.kit.kastel.lissa.sketches.model.elements.IRelation
import edu.kit.kastel.lissa.sketches.model.types.SketchRelationTypes

open class Relation : AbstractElement, IRelation {

    private val connectedElements: MutableList<IBox> = mutableListOf()

    constructor() {
        // NOP
    }

    constructor(name: String, confidence: Double) {
        this.setName(name)
        this.setCurrentConfidence(confidence)
    }

    override fun connectedElements(): List<IBox> = connectedElements

    override fun currentInterpretation() = SketchRelationTypes.UNKNOWN

    override fun reloadElementMappings(sketch: ISketch) {
        val newElements = connectedElements.mapNotNull { e -> sketch.getCurrentInterpretation(e) }
        connectedElements.clear()
        connectedElements.addAll(newElements)
    }

    override fun addToAssociation(element: IBox) {
        connectedElements.add(element)
    }

    override fun delFromAssociation(element: IBox) {
        connectedElements.remove(element)
    }

    override fun toString(): String {
        return String.format(
            "%s [name=%s, confidence=%s, interpretation=%s, elements=%s]", //
            this.javaClass.simpleName,
            this.name(),
            this.currentConfidence(),
            currentInterpretation(),
            connectedElements().map { e -> e.name() })
    }
}
