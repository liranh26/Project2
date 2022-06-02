package project2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import inventory.webservice.rest.app.client.ClientThread;
import inventory.webservice.rest.app.client.InventoryReport;
import inventory.webservice.rest.app.dbService.IOTThingDBService;
import inventory.webservice.rest.app.models.Device;
import inventory.webservice.rest.app.models.HardwareType;
import inventory.webservice.rest.app.models.IOTThing;


@TestInstance(Lifecycle.PER_CLASS)
class InventoryServerTest {

	IOTThing iot;
	private final int NUM_OF_THREADS = 1;
	private IOTThingDBService dbService = new IOTThingDBService();
	private ArrayList<Device> testDevices;
	
	InventoryServerTest(){
		
		Device device1 = new Device(HardwareType.ACTUATOR, "1234A", "Reno-Gear");
		Device device2 = new Device(HardwareType.SENSOR, "2292", "Sensor");
		Device device3 = new Device(HardwareType.CONTROLLER, "ZX88", "Controllers");
		
		testDevices = new ArrayList<Device>();
		testDevices.add(device1);
		testDevices.add(device2);
		testDevices.add(device3);
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

	
	@Test
	void testConnectionToDB() {
		
		int seededObjectsInDB = 3;
		int transmitTime = 2000;
		
		System.out.println("------->!"+testDevices.size());
		
		InventoryReport report = new InventoryReport();

		report.transmitReportsPeriodically(transmitTime);

		assertTrue((dbService.getAllDevices().size()-seededObjectsInDB) < testDevices.size()); 
		
	}


}
