package edu.kit.kastel.lissa.sketches.model.impl.elements.uml;

import org.eclipse.collections.api.list.ImmutableList;

import edu.kit.kastel.lissa.sketches.model.elements.uml.IUMLThing;
import edu.kit.kastel.lissa.sketches.model.impl.Box;
import edu.kit.kastel.lissa.sketches.util.ListWrapper;

abstract class UMLThing extends Box implements IUMLThing {
	private static final long serialVersionUID = -8954737446536608236L;

	private ListWrapper<String> attributes;
	private ListWrapper<String> methods;

	protected UMLThing() {
		this.attributes = ListWrapper.empty();
		this.methods = ListWrapper.empty();
	}

	@Override
	public void addAttribute(String attribute) {
		this.attributes.add(attribute);
	}

	@Override
	public void delAttribute(String attribute) {
		this.attributes.remove(attribute);
	}

	@Override
	public ImmutableList<String> getAttributes() {
		return this.attributes.toImmutable();
	}

	@Override
	public void addMethod(String method) {
		this.methods.add(method);
	}

	@Override
	public void delMethod(String method) {
		this.methods.remove(method);
	}

	@Override
	public ImmutableList<String> getMethods() {
		return this.methods.toImmutable();
	}
}
