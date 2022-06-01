package beans;

import jakarta.ws.rs.QueryParam;
import models.HardwareType;

public class DevicesFilterBean {

	@QueryParam("type") HardwareType type;
	@QueryParam("model") String model;
	@QueryParam("manufacturer") String manufacturer;
	@QueryParam("id") String id;
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public boolean isEmpty() {
		if(model == null && type == null && manufacturer == null)
			return true;
		return false;
	}
	
	public boolean missingData() {
		if(model == null || type == null || manufacturer == null)
			return true;
		return false;
	}
	
	
	@Override
	public String toString() {
		return "DevicesFilterBean [type=" + type + ", model=" + model + ", manufacturer=" + manufacturer + ", id=" + id
				+ "]";
	}
	
	
	
}
