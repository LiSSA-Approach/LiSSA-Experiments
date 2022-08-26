package edu.kit.kastel.lissa.sketches.model.elements.properties

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import edu.kit.kastel.lissa.sketches.model.ISketch
import edu.kit.kastel.lissa.sketches.model.elements.properties.complex.IPropertyMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory

internal class ElementProperty {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(ElementProperty::class.java)
    }

    @JsonProperty
    private var clazz: Class<in IPropertyMapper<*>>? = null

    @JsonProperty
    private var primitiveClass: Class<*>? = null

    @JsonProperty
    private var value: String = ""

    @JsonIgnore
    @Transient
    private lateinit var sketch: ISketch

    private constructor()

    constructor(clazz: Class<in IPropertyMapper<*>>, value: String) {
        this.primitiveClass = null
        this.clazz = clazz
        this.value = value
    }

    constructor(value: Any) {
        when (value.javaClass) {
            Int::class.java -> set(Int::class.java, "$value")
            Double::class.java -> set(Double::class.java, "$value")
            String::class.java -> set(String::class.java, "$value")
            else -> error("Unknown Primitive Class ${value.javaClass}")
        }
    }

    internal fun init(sketch: ISketch) {
        this.sketch = sketch
    }

    private fun set(javaClass: Class<*>, value: String) {
        this.primitiveClass = javaClass
        this.value = value
    }

    fun <V> readValue(wrapper: IPropertyMapper<V>): V {
        if (wrapper.javaClass != this.clazz) {
            logger.error("Reading $clazz for ${this.clazz} is not possible")
            error("Cannot cast $clazz to ${this.clazz}")
        }

        return wrapper.valueFromString(this.sketch, this.value)
    }

    @Suppress("UNCHECKED_CAST")
    fun <V> readPrimitiveValue(clazz: Class<V>): V {
        if (clazz != this.primitiveClass) {
            logger.error("Reading $clazz for $primitiveClass is not possible")
            error("Cannot cast $clazz to $primitiveClass")
        }

        return when (clazz) {
            Int::class.java -> clazz.cast(value.toInt()) as V
            Double::class.java -> clazz.cast(value.toDouble()) as V
            String::class.java -> clazz.cast(value) as V
            else -> error("Unknown Primitive Class ${value.javaClass}")
        }
    }

    override fun toString(): String {
        return "(type='${this.primitiveClass?.simpleName ?: this.clazz?.simpleName}', value='$value')"
    }
}
