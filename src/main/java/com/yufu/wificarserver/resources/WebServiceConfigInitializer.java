package com.yufu.wificarserver.resources;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.yufu.wificarserver.connector.SocketCarConnector;

public class WebServiceConfigInitializer implements ServletContextListener {

	@Override
	public void contextDestroyed(final ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(final ServletContextEvent arg0) {
		try {
			SocketCarConnector.getInstance();
			System.out.println("init complete :)");
		}
		catch (final IOException e) {
			System.err.println(e);
		}
	}

}
