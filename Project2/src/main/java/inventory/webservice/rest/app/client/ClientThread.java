package inventory.webservice.rest.app.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;

import inventory.webservice.rest.app.models.IOTThing;

public class ClientThread implements Runnable {

	private IOTThing thing;
	private static final String SERVER_NAME = "localhost";
	private final static int SERVER_PORT = 9090;
	

	public ClientThread(IOTThing thing) {
		this.thing = thing;
	}

	@Override
	public void run() {

		try (Socket clientSocket = new Socket(SERVER_NAME, SERVER_PORT);
				BufferedReader bufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);) {

			System.out.println("Connected to server");

			thing.simulateInventoryChange();

			Gson gson = new Gson();
			String thingJson = gson.toJson(thing, IOTThing.class);

			writer.println(thingJson);

			// reading the data from the stream
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
