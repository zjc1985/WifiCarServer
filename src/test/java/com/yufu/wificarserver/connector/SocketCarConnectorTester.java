package com.yufu.wificarserver.connector;

import java.io.IOException;
import java.util.Random;

import org.junit.Test;

public class SocketCarConnectorTester {

	@Test
	public void test() throws IOException, InterruptedException {
		final SocketCarConnector carConnector = SocketCarConnector.getInstance();
		while (true) {
			//System.out.println("valid connection: " + carConnector.getValidConnectionNum());
			carConnector.send("server 's message " + new Random().nextInt(1000));
			Thread.sleep(20000);
		}
	}

}
