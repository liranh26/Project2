package inventory.webservice.rest.app.models;

import java.util.UUID;

/*
 * Hardware an abstract class which sets base fields for an electrical component.
 */

public abstract class Hardware  {

	private final UUID ID;
	private HardwareType type;
	private String model;
	private String manufacturer;

	public Hardware(HardwareType type, String model, String manufacturer) {
		this.ID = UUID.randomUUID();
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
		return ID.toString();
	}

	@Override
	public String toString() {
		return "Hardware [ID=" + ID + ", type=" + type + ", model=" + model + ", manufacturer=" + manufacturer + "]";
	}
	
}
