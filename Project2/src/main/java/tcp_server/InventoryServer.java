package tcp_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import DB.DBMock;
import client.IOTThing;

public class InventoryServer {
	
	private final static int PORT = 8095;
	private DBMock db;
	private static Map<String, IOTThing> things;
	
	public static void main(String[] args) throws InterruptedException {

		
		// using the cache servies in order to use max thread --> max clients
		ExecutorService socketsService = Executors.newCachedThreadPool();

		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("Server started on port " + PORT);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				socketsService.execute(new UserRunnable(clientSocket, things));
			}

		} catch (IOException e) {
			System.out.println("User Server failed to start on port" + PORT);
		} finally {
			socketsService.shutdown();
			socketsService.awaitTermination(30, TimeUnit.SECONDS);
		}

	}
	
	
	
	
	
	public InventoryServer() {
		db = DBMock.getInstance();
		things = db.getStudents();
	}




	static class UserRunnable implements Runnable {

		private Socket clientSocket;

		public UserRunnable(Socket clientSocket, Map<String, IOTThing> things) {
			this.clientSocket = clientSocket;
		}

		@Override
		public void run() {
			try (BufferedReader bufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);) {
				System.out.println(
						"User is connected" + clientSocket.getInetAddress() + ", port: " + clientSocket.getPort());


				// reading the expense json from the stream
				String json = bufferReader.readLine();
				Gson gson = new Gson();
				IOTThing iotThing = gson.fromJson(json, IOTThing.class);

				System.out.println(iotThing);
				
//				updateDB(iotThing);
				
				writer.println("Processing request completed for id: " + iotThing.getID());

			} catch (IOException e) {
				System.out.println("Failed to start on port " + PORT);
				e.printStackTrace();
			}
		}
		
		private void updateDB(IOTThing iotThing) {
			if(things.containsKey(iotThing.getID()))
				things.put(iotThing.getID(), iotThing);
			//TODO - throws exception
		}
	}


}
	

