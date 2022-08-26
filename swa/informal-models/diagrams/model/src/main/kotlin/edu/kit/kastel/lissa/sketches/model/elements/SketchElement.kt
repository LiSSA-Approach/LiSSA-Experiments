package edu.kit.kastel.lissa.sketches.model.elements

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import edu.kit.kastel.lissa.sketches.model.ISketch
import edu.kit.kastel.lissa.sketches.model.elements.properties.ElementProperty
import edu.kit.kastel.lissa.sketches.model.elements.properties.complex.IPropertyMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

class SketchElement {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(SketchElement::class.java)
    }

    @JsonProperty
    private var uuid: String = UUID.randomUUID().toString()

    @JsonProperty
    private val properties: MutableMap<String, ElementProperty> = mutableMapOf()

    @JsonProperty
    private var elementInterpretation: SketchElementType = SketchElementType.UNKNOWN

    @JsonProperty
    private var confidence: Double = Double.NaN

    @JsonIgnore
    @Transient
    private lateinit var sketch: ISketch

    internal fun init(sketch: ISketch) {
        this.sketch = sketch
        properties.forEach { (_, v) -> v.init(sketch) }
    }

    fun checkInterpretation(type: SketchElementType) {
        if (elementInterpretation != type) error("$this has not the type $type")
    }

    fun setInterpretation(elementInterpretation: SketchElementType) {
        this.elementInterpretation = elementInterpretation
    }

    fun interpretation() = elementInterpretation

    fun confidence() = confidence
    fun setConfidence(newConfidence: Double) {
        this.confidence = newConfidence
    }

    fun <V> readPrimitiveValue(key: String, clazz: Class<V>, defaultValue: V): V {
        if (!properties.containsKey(key)) {
            logger.debug("Cannot find value for key $key")
            return defaultValue
        }
        val value = properties[key]!!
        return value.readPrimitiveValue(clazz)
    }

    fun savePrimitiveValue(key: String, value: Any) {
        val property = ElementProperty(value)
        property.init(this.sketch)
        properties[key] = property
    }

    fun <V> readValue(key: String, wrapper: IPropertyMapper<V>, defaultValue: V): V {
        if (!properties.containsKey(key)) {
            logger.debug("Cannot find value for key $key")
            return defaultValue
        }
        val value = properties[key]!!
        return value.readValue(wrapper)
    }

    fun <V> saveValue(key: String, wrapper: IPropertyMapper<V>, value: V) {
        val property = ElementProperty(wrapper.javaClass, wrapper.valueToString(value))
        property.init(this.sketch)
        properties[key] = property
    }

    internal fun sketch() = sketch

    internal fun uuid() = uuid
    internal fun setUuid(uuid: String) {
        this.uuid = uuid
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SketchElement) return false
        return uuid == other.uuid
    }

    override fun hashCode(): Int = uuid.hashCode()

    override fun toString(): String =
        "SketchElement(uuid='$uuid', properties=$properties, elementInterpretation=$elementInterpretation, confidence=$confidence)"
}
