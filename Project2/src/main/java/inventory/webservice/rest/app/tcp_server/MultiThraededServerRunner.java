package inventory.webservice.rest.app.tcp_server;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/*
 * MultiThraededServerRunner merges the activation of the TCP server and the Tomcat server.
 */

@WebListener
public class MultiThraededServerRunner implements ServletContextListener {

	private final int PORT = 9090;
	InventoryServerThread server;
	
	public void contextInitialized(ServletContextEvent event) {
		 server = new InventoryServerThread(PORT);
		 server.start();
	}

	
	public void contextDestroyed(ServletContextEvent event) {
		server.kill();
	}
	
}
