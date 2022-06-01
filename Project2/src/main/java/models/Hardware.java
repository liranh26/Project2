package models;

import java.util.UUID;

public abstract class Hardware  {

	private final String ID;
	private HardwareType type;
	private String model;
	private String manufacturer;
	
//	public Hardware() {
//		this.ID = UUID.randomUUID().toString();
//	}

	public Hardware(HardwareType type, String model, String manufacturer) {
		this.ID = UUID.randomUUID().toString();
		this.type = type;
		this.model = model;
		this.manufacturer = manufacturer;
	}

	public HardwareType getType() {
		return type;
	}

	public void setType(HardwareType type) {
		this.type = type;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getID() {
		return ID;
	}

	@Override
	public String toString() {
		return "Hardware [ID=" + ID + ", type=" + type + ", model=" + model + ", manufacturer=" + manufacturer + "]";
	}
	
}
