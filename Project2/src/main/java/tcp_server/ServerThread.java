package tcp_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.Lock;

import com.google.gson.Gson;

import dbService.DBService;
import models.IOTThing;

public class ServerThread implements Runnable {

	private Socket clientSocket;
	private DBService dbService;
	private Lock lock;

	public ServerThread(Socket clientSocket, Lock lock) {
		this.clientSocket = clientSocket;
		dbService = new DBService();
		this.lock = lock;
	}

	@Override
	public void run() {
		try (BufferedReader bufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);) {
			System.out.println(
					"User is connected" + clientSocket.getInetAddress() + ", port: " + clientSocket.getPort());
			
			lock.lock();
			// reading the expense json from the stream
			String json = bufferReader.readLine();
			Gson gson = new Gson();
			IOTThing iotThing = gson.fromJson(json, IOTThing.class);
			
//			System.out.println(iotThing);
			
			iotThing.simulateInventoryChange();
			dbService.updateServer(iotThing);
			lock.unlock();
			
			writer.println("Processing request completed for id: " + iotThing.getID());

		} catch (IOException e) {
			System.err.println("Connection failed!");
			e.printStackTrace();
		}
	}
	
}
