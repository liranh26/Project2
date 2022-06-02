package inventory.webservice.rest.app.tcp_server;


import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class InventoryServer {
	
	private final static int PORT = 9090;
	Lock lock;
	
	public void runServer() {
		lock = new ReentrantLock();
		ExecutorService socketsService = Executors.newCachedThreadPool();

		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("Server started on port " + PORT);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				socketsService.execute(new ServerSocketThread(clientSocket, lock));
			}

		} catch (IOException e) {
			System.out.println("User Server failed to start on port" + PORT);
		} finally {
			try {
				socketsService.shutdown();
				socketsService.awaitTermination(30, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		InventoryServer server = new InventoryServer();
		server.runServer();
	}

}
	

