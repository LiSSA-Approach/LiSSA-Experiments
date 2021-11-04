package edu.kit.kastel.lissa.sketches.model.impl.elements.uml;

import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLInterface;
import edu.kit.kastel.lissa.sketches.model.impl.Box;
import edu.kit.kastel.lissa.sketches.model.types.SketchBoxTypes;

public class UMLInterface extends UMLThing implements IUMLInterface {
    private static final long serialVersionUID = 3310996720085779684L;

    public static final String ATTRIBUTE_KEY = "iface_attributes";
    public static final String METHOD_KEY = "iface_methods";

    public UMLInterface(Box element) {
        super(element, ATTRIBUTE_KEY, METHOD_KEY);
    }

    @Override
    public SketchBoxTypes getCurrentInterpretation() {
        return SketchBoxTypes.INTERFACE;
    }

}
