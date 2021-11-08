package edu.kit.kastel.lissa.sketches.model.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public final class Remapper {

	public static AbstractElement mapRaw(AbstractElement input, Class<? extends AbstractElement> inputClass, Class<? extends AbstractElement> outputClass)
			throws Exception {
		return map(input, inputClass, outputClass);
	}

	public static <I extends AbstractElement, O extends AbstractElement> O map(I input, Class<? extends I> inputClass, Class<? extends O> outputClass)
			throws Exception {
//		if (inputClass.isAssignableFrom(outputClass) || outputClass.isAssignableFrom(inputClass)) {
//			// getOwnData may be difficult ..
//			throw new IllegalArgumentException("Currently we assume no order within in/out class!");
//		}
		Map<String, Serializable> data = getData(input);

		Map<String, Serializable> dataOfI = new HashMap<>();
		getOwnData(dataOfI, input, inputClass);

		// Merge to internal data ..
		dataOfI.forEach((k, v) -> data.put(k, v));

		O newO = createOutputObject(outputClass, data);
		fillElements(newO, newO.getClass(), data);
		return newO;
	}

	private static <O extends AbstractElement> void fillElements(O newO, Class<?> currentClass, Map<String, Serializable> data)
			throws IllegalArgumentException, IllegalAccessException {
		if (currentClass == Object.class) {
			return;
		}
		var fields = currentClass.getDeclaredFields();
		for (Field f : fields) {
			if (Modifier.isStatic(f.getModifiers())) {
				continue;
			}
			if (Modifier.isTransient(f.getModifiers())) {
				continue;
			}

			if (!f.getType().isPrimitive() && !Serializable.class.isAssignableFrom(f.getType())) {
				throw new IllegalStateException("Field " + f + " is not serializable and not marked as transient!");
			}
			f.setAccessible(true);
			String key = currentClass + "->" + f.getName();
			if (data.containsKey(key)) {
				f.set(newO, data.get(key));
			}
		}

		fillElements(newO, currentClass.getSuperclass(), data);
	}

	private static <O extends AbstractElement> O createOutputObject(Class<O> outputClass, Map<String, Serializable> data) throws NoSuchMethodException,
			SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		var constructor = outputClass.getDeclaredConstructor();
		if (constructor == null) {
			throw new IllegalStateException(outputClass.getClass() + " has no suitable constructor!");
		}

		constructor.setAccessible(true);
		O o = constructor.newInstance();

		var dataField = AbstractElement.class.getDeclaredField("data");
		dataField.setAccessible(true);
		dataField.set(o, data);

		return o;
	}

	private static void getOwnData(Map<String, Serializable> dataOfI, AbstractElement input, Class<?> inputClass)
			throws IllegalArgumentException, IllegalAccessException {
		if (inputClass == Object.class) {
			return;
		}

		var fields = inputClass.getDeclaredFields();
		for (Field f : fields) {
			if (Modifier.isTransient(f.getModifiers())) {
				continue;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				continue;
			}

			if (!f.getType().isPrimitive() && !Serializable.class.isAssignableFrom(f.getType())) {
				throw new IllegalStateException("Field " + f + " is not serializable and not marked as transient!");
			}
			f.setAccessible(true);
			dataOfI.put(inputClass + "->" + f.getName(), (Serializable) f.get(input));
		}

		getOwnData(dataOfI, input, inputClass.getSuperclass());
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Serializable> getData(AbstractElement input) throws Exception {
		var dataField = AbstractElement.class.getDeclaredField("data");
		dataField.setAccessible(true);
		return (Map<String, Serializable>) dataField.get(input);
	}

}
