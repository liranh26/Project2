package inventory.webservice.rest.app.client;


import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import inventory.webservice.rest.app.models.Device;
import inventory.webservice.rest.app.models.HardwareType;
import inventory.webservice.rest.app.models.IOTThing;

public class InventoryReport {
	
	private IOTThing iot;
	private final int NUM_OF_THREADS = 1;

	public InventoryReport() {
		createIOTThing();
	}

	public void createIOTThing() {
		IOTThing iot = new IOTThing(HardwareType.CONTROLLER, "14AT", "Controllers");

		iot.addDevice(new Device(HardwareType.ACTUATOR, "1234A", "Reno-Gear"));
		iot.addDevice(new Device(HardwareType.SENSOR, "2292", "Sensor"));
		iot.addDevice(new Device(HardwareType.CONTROLLER, "ZX88", "Controllers"));
		
		this.iot = iot;
	}

	public void transmitReportsPeriodically(int trasmintPeriod) {

		ScheduledExecutorService iotClientsService = Executors.newScheduledThreadPool(NUM_OF_THREADS);
		
		iotClientsService.scheduleAtFixedRate(new ClientThread(iot), 0, 1, TimeUnit.SECONDS);
		
		sleep(trasmintPeriod);

		try {
			iotClientsService.shutdown();
			iotClientsService.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO
			e.printStackTrace();
		}
	}

	private static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		int transmitTime = 2000;
		
		InventoryReport report = new InventoryReport();
		report.transmitReportsPeriodically(transmitTime);
	}

}
