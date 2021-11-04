package edu.kit.kastel.lissa.sketches.model.impl.elements.uml;

import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLClass;
import edu.kit.kastel.lissa.sketches.model.impl.Box;
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes;

public class UMLClass extends UMLThing implements IUMLClass {
    private static final long serialVersionUID = 3310996720085779684L;

    public static final String ATTRIBUTE_KEY = "cls_attributes";
    public static final String METHOD_KEY = "cls_methods";

    public UMLClass(Box element) {
        super(element, ATTRIBUTE_KEY, METHOD_KEY);
    }

    @Override
    public SketchBoxTypes getCurrentInterpretation() {
        return SketchBoxTypes.CLASS;
    }

}
