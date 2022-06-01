package beans;

import jakarta.ws.rs.QueryParam;
import models.Hardware;

public class IOTThingFilterBean {
	
	@QueryParam("type") Hardware type;
	@QueryParam("model") String model;
	@QueryParam("manufacturer") String manufacturer;
	
	public Hardware getType() {
		return type;
	}
	public void setType(Hardware type) {
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
	
	public boolean isEmpty() {
		if(model == null)
			return true;
		return false;
	}
	
}
