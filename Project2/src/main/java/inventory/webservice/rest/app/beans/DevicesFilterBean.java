package inventory.webservice.rest.app.beans;

import inventory.webservice.rest.app.models.HardwareType;
import jakarta.ws.rs.QueryParam;

public class DevicesFilterBean {

	@QueryParam("type") HardwareType type;
	@QueryParam("model") String model;
	@QueryParam("manufacturer") String manufacturer;
	@QueryParam("id") String iotThing;
	
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
	public String getIOTThingId() {
		return iotThing;
	}
	public void setId(String id) {
		this.iotThing = id;
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
		return "DevicesFilterBean [type=" + type + ", model=" + model + ", manufacturer=" + manufacturer + ", id=" + iotThing
				+ "]";
	}
	
	
	
}
