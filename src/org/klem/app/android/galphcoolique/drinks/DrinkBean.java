package org.klem.app.android.galphcoolique.drinks;

import java.io.Serializable;
import java.util.HashMap;

public class DrinkBean implements Serializable {
	
	private int id;
	private String label;
	private String type;
	private float volume;
	private float proof;
	private float units;
	public static final String DRINK_INTENT_NAME = "editDrink";
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getVolume() {
		return volume;
	}
	public void setVolume(float volume) {
		this.volume = volume;
	}
	public float getProof() {
		return proof;
	}
	public void setProof(float proof) {
		this.proof = proof;
	}
	public float getUnits() {
		return units;
	}
	public void setUnits(float units) {
		this.units = units;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Drinks [id=");
		builder.append(id);
		builder.append(", label=");
		builder.append(label);
		builder.append(", type=");
		builder.append(type);
		builder.append(", volume=");
		builder.append(volume);
		builder.append(", proof=");
		builder.append(proof);
		builder.append(", units=");
		builder.append(units);
		builder.append("]");
		return builder.toString();
	}

}
