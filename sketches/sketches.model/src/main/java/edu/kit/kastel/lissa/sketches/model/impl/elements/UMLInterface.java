package edu.kit.kastel.lissa.sketches.model.impl.elements;

import edu.kit.kastel.lissa.sketches.model.SketchElementType;
import edu.kit.kastel.lissa.sketches.model.elements.IUMLInterface;
import edu.kit.kastel.lissa.sketches.model.impl.BoxElement;

public class UMLInterface extends UMLThing implements IUMLInterface {
    private static final long serialVersionUID = 3310996720085779684L;

    public static final String ATTRIBUTE_KEY = "iface_attributes";
    public static final String METHOD_KEY = "iface_methods";

    public UMLInterface(BoxElement element) {
        super(element, ATTRIBUTE_KEY, METHOD_KEY);
    }

    @Override
    public SketchElementType getCurrentInterpretation() {
        return SketchElementType.INTERFACE;
    }

}
