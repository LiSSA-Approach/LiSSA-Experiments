package edu.kit.kastel.lissa.sketches.model.types;

import edu.kit.kastel.lissa.sketches.model.elements.ISketchElement;
import edu.kit.kastel.lissa.sketches.model.impl.AbstractElement;
import edu.kit.kastel.lissa.sketches.model.impl.Remapper;

public enum SketchRelationTypes {
	UNKNOWN, CLASS_ASSOCIATION;

	public <O extends ISketchElement> O map(ISketchElement element, Class<O> type) {
		if (!this.isValidType(type)) {
			throw new IllegalArgumentException(type.getSimpleName() + " is not marked as possible type via " + SketchBoxTypeMapping.class);
		}

		Object o = this.createCompatibleObject(type, element);
		return type.cast(o);
	}

	@SuppressWarnings("unchecked")
	private Object createCompatibleObject(Class<?> iface, ISketchElement element) {
		var type = iface.getDeclaredAnnotation(SketchRelationTypeMapping.class).implementation();

		if (!AbstractElement.class.isAssignableFrom(type) || !AbstractElement.class.isAssignableFrom(element.getClass())) {
			throw new IllegalArgumentException("Mapping is only supported for subtypes of " + AbstractElement.class);
		}
		try {
			return Remapper.mapRaw((AbstractElement) element, (Class<? extends AbstractElement>) element.getClass(), (Class<? extends AbstractElement>) type);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	private boolean isValidType(Class<?> type) {
		if (type == null || type == Object.class) {
			return false;
		}

		var mapping = type.getDeclaredAnnotation(SketchRelationTypeMapping.class);
		if (mapping == null) {
			for (Class<?> i : type.getInterfaces()) {
				if (this.isValidType(i)) {
					return true;
				}
			}

			return false;
		}
		return mapping.type() == this;
	}

	public static SketchRelationTypes findByClass(Class<? extends ISketchElement> type) {
		var mapping = type.getDeclaredAnnotation(SketchRelationTypeMapping.class);
		if (mapping != null) {
			return mapping.type();
		}
		throw new IllegalArgumentException("Can't find a suitable type for class " + type);
	}
}
