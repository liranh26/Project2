package inventory.webservice.rest.app.tcp_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerThread extends Thread {

	private final int PORT;
	private ExecutorService executorService;
	Lock lock;

	public ServerThread(int port) {
		this.PORT = port;
		executorService = Executors.newCachedThreadPool();
	}

	@Override
	public void run() {
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
		}
	}
	
	public void kill() {
		executorService.shutdown();
		try {
			executorService.awaitTermination(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
