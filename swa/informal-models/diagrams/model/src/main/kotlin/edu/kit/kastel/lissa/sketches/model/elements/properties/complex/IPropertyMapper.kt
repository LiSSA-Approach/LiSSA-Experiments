package edu.kit.kastel.lissa.sketches.model.elements.properties.complex

import edu.kit.kastel.lissa.sketches.model.ISketch

interface IPropertyMapper<V> {
    fun valueFromString(sketch: ISketch, value: String): V
    fun valueToString(v: V): String
}
