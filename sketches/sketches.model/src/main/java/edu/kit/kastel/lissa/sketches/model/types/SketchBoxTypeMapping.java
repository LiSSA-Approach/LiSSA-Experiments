package edu.kit.kastel.lissa.sketches.model.types;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import edu.kit.kastel.lissa.sketches.model.elements.ISketchElement;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface SketchBoxTypeMapping {
    SketchBoxTypes type();

    Class<? extends ISketchElement> implementation();
}
