package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import models.Device;
import models.Hardware;
import models.HardwareType;

public class IOTThing extends Hardware{

	private Map<String, Device> devices;
	private static final String SERVER_NAME="localhost";
	private final static int SERVER_PORT = 8095, NUM_CLIENTS=1, SENSOR_RANDOM_RANGE=20;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		//simulate multiply clients
		ScheduledExecutorService iotClientsService = Executors.newScheduledThreadPool(NUM_CLIENTS);
		
		IOTThing iot1 = new IOTThing(HardwareType.CONTROLLER, "14AT", "Controllers");
		iot1.initDevices();
		
		//each thread will simulate a client
		//TODO --> from where the data gets ? send an id
//		for (int i = 0; i < NUM_CLIENTS; i++) {
			//3 is the start delay, 1 is fix rate
			iot1.simulateInventoryChange();

			iotClientsService.scheduleAtFixedRate(new IOTClientRunnable(iot1), 3, 1, TimeUnit.SECONDS);
//			iotClientsService.execute(new IOTClientRunnable(iot1)); 
//			
//		}
		sleep(10000);
		
		iotClientsService.shutdown();
		iotClientsService.awaitTermination(5, TimeUnit.SECONDS);
		
	}
	
	
	public IOTThing() {
		super();
	}
	
	public IOTThing(HardwareType type, String model, String manufacturer) {
		super(type, model, manufacturer);
	}

	
//	private IOTThing createReport() {
//		return this;
//	}
	
	public void setDevices(List<Device> devicesList) {
		for (Device device : devicesList) {
			this.devices.put(device.getID(), device);
		}
	}
	
	public Map<String, Device> getDevices() {
		return devices;
	}

	
	private void simulateInventoryChange() {
		devices.values().stream().forEach(d -> {
			d.simulateMeasure(Math.random() * SENSOR_RANDOM_RANGE);
		});
	}
	
	

	private void initDevices() {
		devices = new HashMap<>();
		Device device = new Device();
		devices.put(device.getID(), device);
	}
	
	
	private static class IOTClientRunnable implements Runnable{

		private IOTThing thing;
		
		public IOTClientRunnable(IOTThing thing) {
			this.thing = thing;
		}

		//run creats a client (player)
		@Override
		public void run() {

			try (Socket clientSocket = new Socket(SERVER_NAME, SERVER_PORT);
					BufferedReader bufferReader = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));
					PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);) {
				
				System.out.println("Connected to server");
				
				Gson gson = new Gson();
				String thingJson = gson.toJson(thing, IOTThing.class);
				writer.println(thingJson);
				
				//reading the data from the stream
				String line = bufferReader.readLine();
				System.out.println("Server says: " + line);
				
				
			} catch (UnknownHostException e) {
				System.err.println("Server is not found");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Socket failed!");
				e.printStackTrace();
			}
		}	
	}
	
	
	static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
//	public void setDevices(Map<String, Device> devices) {
//	this.devices = devices;
//}
	
}
