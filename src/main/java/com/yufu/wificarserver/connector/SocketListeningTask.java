package com.yufu.wificarserver.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.yufu.wificarserver.util.Constants;

public class SocketListeningTask implements Runnable {
	SocketCarConnector carConnector;

	public SocketListeningTask(final SocketCarConnector socketCarConnector) {
		this.carConnector = socketCarConnector;
	}

	@Override
	public void run() {
		System.out.println("car socket listen Task started");
		while (true) {
			try {
				final Socket connection = this.carConnector.getServerSocket().accept();
				System.out.println("one connection connected");
				final HeartBeatSocket heartBeatSocket = new HeartBeatSocket(connection, Constants.heartBeatInterval);
				this.carConnector.getConnectionList().add(heartBeatSocket);

				this.carConnector.getExec().execute(new Runnable() {
					@Override
					public void run() {
						try {
							final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
							String inputString = null;
							while ((inputString = reader.readLine()) != null) {
								//System.out.println("isClosed: " + connection.isClosed() + " isConnected: " + connection.isConnected());
								SocketListeningTask.this.carConnector.handleMessage(inputString);
							}
						}
						catch (final Exception e) {
							System.out.println("isClosed: " + connection.isClosed() + " isConnected: " + connection.isConnected());
							System.out.println("exception happened when reading from one socket");
							System.err.println(e);

						}

					}
				});

			}
			catch (final IOException e) {

			}
		}
	}

}
