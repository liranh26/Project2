package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import models.Device;
import models.Hardware;
import models.HardwareType;

public class IOTThing extends Hardware{

	private Map<String, Device> devices;
	private static final String SERVER_NAME="localhost";
	private final static int SERVER_PORT = 8080, NUM_CLIENTS=10;
	
	public IOTThing() {
		super();
	}
	
	public IOTThing(HardwareType type, String model, String manufacturer) {
		super(type, model, manufacturer);
	}


	public static void main(String[] args) throws IOException, InterruptedException {
		
		//simulate multiply clients
		ExecutorService iotClientsService = Executors.newCachedThreadPool();
		
		//each thread will simulate a client
		//TODO --> from where the data gets ? send an id
//		for (int i = 0; i < NUM_CLIENTS; i++) {
//			iotClientsService.execute(new IOTClientRunnable(i));
//		}
		
		iotClientsService.shutdown();
		iotClientsService.awaitTermination(30, TimeUnit.SECONDS);
		
	}
	
	
	
	
	
	public Map<String, Device> getDevices() {
		return devices;
	}

	public void setDevices(Map<String, Device> devices) {
		this.devices = devices;
	}

	private void initDevices() {
		devices = new HashMap<>();
		Device device = new Device();
		devices.put(device.getID(), device);
	}
	
	
	private static class IOTClientRunnable implements Runnable{

		private String id;
		
		public IOTClientRunnable(String id) {
			this.id = id;
		}

		//run creats a client (player)
		@Override
		public void run() {

			try (Socket clientSocket = new Socket(SERVER_NAME, SERVER_PORT);
					BufferedReader bufferReader = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));
					PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);) {
				
				
				System.out.println("Connected to server");
				
				
				//TODO send to server report as json
				
				writer.println("Client " +id+ " sent request");
				
				
				
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
}
