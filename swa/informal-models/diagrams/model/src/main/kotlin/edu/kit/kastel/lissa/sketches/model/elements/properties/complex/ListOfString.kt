package edu.kit.kastel.lissa.sketches.model.elements.properties.complex

import com.fasterxml.jackson.module.kotlin.readValue
import edu.kit.kastel.lissa.sketches.model.ISketch
import edu.kit.kastel.lissa.utils.createObjectMapper

class ListOfString : IPropertyMapper<MutableList<String>> {
    private val oom = createObjectMapper()
    override fun valueFromString(sketch: ISketch, value: String): MutableList<String> = oom.readValue(value)
    override fun valueToString(v: MutableList<String>): String = oom.writeValueAsString(v)
}
