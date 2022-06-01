package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import models.HardwareType;
import models.IOTThing;

public class InventoryReport {

	private static final String SERVER_NAME = "localhost";
	private final static int SERVER_PORT = 9090;
	
	IOTThing iot;

	
	public InventoryReport(IOTThing iot) {
		this.iot = iot;
	}

	public void transmitReportsPeriodically(int trasmintPeriod) {

		int numOfThreads = 1;

		ScheduledExecutorService iotClientsService = Executors.newScheduledThreadPool(numOfThreads);

		iot = new IOTThing(HardwareType.CONTROLLER, "14AT", "Controllers");

		// 3 is the start delay, 1 is fix rate
		iotClientsService.scheduleAtFixedRate(new IOTClientRunnable(iot), 0, 1, TimeUnit.SECONDS);

		sleep(trasmintPeriod);

		try {
			iotClientsService.shutdown();
			iotClientsService.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO
			e.printStackTrace();
		}
	}
	
	
//	private Hardware createReport() {
//		return iot;
//	}


	private static class IOTClientRunnable implements Runnable {

		private IOTThing thing;

		public IOTClientRunnable(IOTThing thing) {
			this.thing = thing;
		}

		@Override
		public void run() {

			try (Socket clientSocket = new Socket(SERVER_NAME, SERVER_PORT);
					BufferedReader bufferReader = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));
					PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);) {

				thing.simulateInventoryChange();

				System.out.println("Connected to server");
				
				long start = System.currentTimeMillis();
				
				Gson gson = new Gson();
//				Hardware report = 
				String thingJson = gson.toJson(thing, IOTThing.class);
				writer.println(thingJson);

				// reading the data from the stream
				String line = bufferReader.readLine();
				System.out.println("Server says: " + line);
				
				long end = System.currentTimeMillis();
				System.out.println("Server says: " + line + ", After : " + (end-start) + " [ms]");

			} catch (UnknownHostException e) {
				System.err.println("Server is not found");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Socket failed!");
				e.printStackTrace();
			}
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
		InventoryReport report = new InventoryReport(new IOTThing(HardwareType.CONTROLLER, "14AT", "Controllers"));
		report.transmitReportsPeriodically(5000);
	}

}
