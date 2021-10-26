package edu.kit.kastel.lissa.sketches.model;

import edu.kit.kastel.lissa.sketches.model.Mappers.Mapper;

public enum SketchElementType {
    UNKNOWN(e -> e), CLASS(Mappers.CLASS), INTERFACE(null), METHOD(null), COMPONENT(null);

    private Mapper<?> mapper;

    SketchElementType(Mapper<?> mapper) {
        this.mapper = mapper;
    }

    public <O extends ISketchElement> Mapper<O> getMapper(Class<O> type) {
        return element -> {
            Object o = mapper.asType(element);
            return type.cast(o);
        };
    }
}
