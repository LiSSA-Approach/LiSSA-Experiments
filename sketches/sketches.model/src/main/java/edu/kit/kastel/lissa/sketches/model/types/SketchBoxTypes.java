package edu.kit.kastel.lissa.sketches.model.types;

import java.lang.reflect.Modifier;

import edu.kit.kastel.lissa.sketches.model.elements.ISketchElement;

public enum SketchBoxTypes {
    CLASS, INTERFACE, METHOD, COMPONENT, UNKNOWN;

    public <O extends ISketchElement> O map(ISketchElement element, Class<O> type) {
        if (!this.isValidType(type)) {
            throw new IllegalArgumentException(type.getSimpleName() + " is not marked as possible type via " + SketchBoxTypeMapping.class);
        }

        Object o = createCompatibleObject(type, element);
        return type.cast(o);
    }

    private Object createCompatibleObject(Class<?> iface, ISketchElement element) {
        var type = iface.getDeclaredAnnotation(SketchBoxTypeMapping.class).implementation();

        var constructors = type.getDeclaredConstructors();
        if (constructors == null || constructors.length == 0) {
            throw new IllegalStateException(type.getClass() + " has no suitable constructor!");
        }

        try {
            for (var constructor : constructors) {
                if (!Modifier.isPublic(constructor.getModifiers())) {
                    continue;
                }
                if (constructor.getParameterCount() != 1) {
                    continue;
                }
                if (!constructor.getParameters()[0].getType().isAssignableFrom(element.getClass())) {
                    continue;
                }
                return constructor.newInstance(element);
            }
        } catch (Exception e) {
            throw new IllegalStateException(type.getClass() + " has no suitable constructor .. " + e.getMessage());
        }

        throw new IllegalStateException(type.getClass() + " has no suitable constructor!");
    }

    private boolean isValidType(Class<?> type) {
        if (type == null || type == Object.class) {
            return false;
        }

        var mapping = type.getDeclaredAnnotation(SketchBoxTypeMapping.class);
        if (mapping == null) {
            for (Class<?> i : type.getInterfaces()) {
                if (isValidType(i)) {
                    return true;
                }
            }

            return false;
        }
        return mapping.type() == this;
    }

    public static SketchBoxTypes findByClass(Class<? extends ISketchElement> type) {
        var mapping = type.getDeclaredAnnotation(SketchBoxTypeMapping.class);
        if (mapping != null) {
            return mapping.type();
        }
        throw new IllegalArgumentException("Can't find a suitable type for class " + type);
    }

}
