package edu.kit.kastel.lissa.sketches.model;

import edu.kit.kastel.lissa.sketches.model.Mappers.Mapper;
import edu.kit.kastel.lissa.sketches.model.elements.IUMLClass;
import edu.kit.kastel.lissa.sketches.model.elements.IUMLInterface;

public enum SketchElementType {
    CLASS(Mappers.CLASS, IUMLClass.class), //
    INTERFACE(null, IUMLInterface.class), //
    METHOD(null, null), //
    COMPONENT(null, null), //
    UNKNOWN(e -> e, ISketchElement.class);

    private Mapper<?> mapper;
    private Class<? extends ISketchElement> baseInterface;

    SketchElementType(Mapper<?> mapper, Class<? extends ISketchElement> baseInterface) {
        this.mapper = mapper;
        this.baseInterface = baseInterface;
    }

    public <O extends ISketchElement> O map(ISketchElement element, Class<O> type) {
        if (!this.baseInterface.isAssignableFrom(type)) {
            throw new IllegalArgumentException(type.getSimpleName() + " is not a subclass of " + this.baseInterface.getSimpleName());
        }
        Object o = mapper.asType(element);
        return type.cast(o);
    }

    public static SketchElementType findByClass(Class<? extends ISketchElement> clazz) {
        for (SketchElementType type : SketchElementType.values()) {
            if (type.baseInterface == null) {
                continue;
            }
            if (type.baseInterface.isAssignableFrom(clazz)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Can't find a suitable type for class " + clazz);
    }

}
