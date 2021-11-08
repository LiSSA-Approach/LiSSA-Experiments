package edu.kit.kastel.lissa.sketches.model.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import edu.kit.kastel.lissa.sketches.model.elements.ISketchElement;

public abstract class AbstractElement implements ISketchElement {
	private static final long serialVersionUID = -7524099797275769182L;

	@SuppressWarnings("unused")
	private transient Map<String, Serializable> data = new HashMap<>();

	private String name;
	private double confidence;

	protected AbstractElement() {
		// NOP
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	@Override
	public double getCurrentConfidence() {
		return this.confidence;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public Map<String, Serializable> getRawData() {
		return this.data;
	}

	@Override
	public String toString() {
		return String.format("%s [name=%s, confidence=%s]", //
				this.getClass().getSimpleName(), this.getName(), this.getCurrentConfidence());
	}

}
