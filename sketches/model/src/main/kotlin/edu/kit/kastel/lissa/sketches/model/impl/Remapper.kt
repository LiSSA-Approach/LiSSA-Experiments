package edu.kit.kastel.lissa.sketches.model.impl

import edu.kit.kastel.lissa.sketches.model.elements.ISketchElement
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypeMapping
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes
import java.lang.reflect.Modifier
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.isAccessible

private const val DATA = "rawData"

/**
 * This function converts an [AbstractElement][element] to a specific type [R].
 * [typeEnumValue] defines the expected value from an enum of all possible types (identified by [annotation2EnumValue]).
 * [annotationType] defines the type of annotation to use to extract meta information from the [targetType].
 * [annotation2Class] defines a function to extract the actual implementation of [R] from [A]
 */
@Suppress("UNCHECKED_CAST")
fun <A : Annotation, T : Any, R : ISketchElement> createCompatibleObject(
    element: ISketchElement,
    targetType: KClass<R>,
    typeEnumValue: T,
    annotationType: KClass<A>,
    annotation2Class: (A) -> KClass<out ISketchElement>,
    annotation2EnumValue: (A) -> T
): R {

    require(isValidType(annotationType, annotation2EnumValue, targetType.java, typeEnumValue)) { targetType.simpleName + " is not marked as possible type via " + annotationType.java }
    val type: KClass<out ISketchElement> = annotation2Class(targetType.java.getDeclaredAnnotation(annotationType.java))
    require(type.isSubclassOf(AbstractElement::class) && element::class.isSubclassOf(AbstractElement::class)) { "Mapping is only supported for subtypes of " + AbstractElement::class.java }
    return try {
        map(element as AbstractElement, element::class as KClass<out AbstractElement>, type as KClass<out AbstractElement>) as R
    } catch (e: Exception) {
        throw IllegalArgumentException(e)
    }
}


fun <A : Annotation, T : Any> findByClass(type: KClass<out ISketchElement>, annotationType: KClass<A>, annotation2EnumValue: (A) -> T): T {
    val mapping = type.annotations.find { a -> annotationType.isInstance(a) }
    if (mapping != null) {
        @Suppress("UNCHECKED_CAST")
        return annotation2EnumValue(mapping as A)
    }
    throw IllegalArgumentException("Can't find a suitable type for class $type")
}

private fun <A : Annotation, T : Any> isValidType(annotationType: KClass<A>, annotation2EnumValue: (A) -> T, type: Class<*>?, typeEnumValue: Any): Boolean {
    if (type == null || type == Any::class.java) {
        return false
    }
    val mapping = type.getDeclaredAnnotation(annotationType.java)
    if (mapping == null) {
        for (i in type.interfaces) {
            if (isValidType(annotationType, annotation2EnumValue, i, typeEnumValue)) {
                return true
            }
        }
        return false
    }
    return annotation2EnumValue(mapping) == typeEnumValue
}


fun <I : AbstractElement, O : AbstractElement> map(input: I, inputClass: KClass<out I>, outputClass: KClass<out O>): O {
    val data = getData(input)
    val dataOfInput: MutableMap<String, Any> = HashMap()
    getOwnData(dataOfInput, input, inputClass)

    // Merge to internal data ..
    dataOfInput.forEach { (k: String, v: Any) -> data[k] = v }
    val newO = createOutputObject(outputClass, data)
    fillElements(newO, newO::class, data)
    return newO
}


private fun <O : AbstractElement> createOutputObject(outputClass: KClass<O>, data: MutableMap<String, Any>): O {
    val constructor = outputClass.constructors.find { c -> c.parameters.isEmpty() } ?: throw IllegalStateException("${outputClass.java} has no suitable constructor!")
    constructor.isAccessible = true
    val o = constructor.call()
    val dataField = AbstractElement::class.java.getDeclaredField(DATA)
    dataField.isAccessible = true
    dataField[o] = data
    return o
}

@Throws(IllegalArgumentException::class, IllegalAccessException::class)
private fun getOwnData(dataOfI: MutableMap<String, Any>, input: AbstractElement, inputClass: KClass<*>?) {
    if (inputClass == null || inputClass == Any::class.java) {
        return
    }
    val fields = inputClass.java.declaredFields
    for (f in fields) {
        if (Modifier.isTransient(f.modifiers)) {
            continue
        }
        if (Modifier.isStatic(f.modifiers)) {
            continue
        }
        f.isAccessible = true
        dataOfI[inputClass.toString() + "->" + f.name] = f[input] as Any
    }
    getOwnData(dataOfI, input, inputClass.superclass())
}

private fun <O : AbstractElement?> fillElements(newO: O, currentClass: KClass<*>?, data: MutableMap<String, Any>) {
    if (currentClass == null || currentClass == Any::class.java) {
        return
    }
    val fields = currentClass.java.declaredFields
    for (f in fields) {
        if (Modifier.isStatic(f.modifiers)) {
            continue
        }
        if (Modifier.isTransient(f.modifiers)) {
            continue
        }
        f.isAccessible = true

        val key = currentClass.toString() + "->" + f.name
        if (data.containsKey(key)) {
            f[newO] = data[key]
        }
    }
    fillElements(newO, currentClass.superclass(), data)
}

@Suppress("UNCHECKED_CAST")
private fun getData(input: AbstractElement): MutableMap<String, Any> {
    val dataField = AbstractElement::class.java.getDeclaredField(DATA)
    dataField.isAccessible = true
    return dataField[input] as MutableMap<String, Any>
}

private fun KClass<*>.superclass(): KClass<*>? {
    return supertypes.find { e -> e is KClass<*> } as? KClass<*>
}