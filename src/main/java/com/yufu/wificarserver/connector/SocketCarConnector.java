package com.yufu.wificarserver.connector;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.json.JSONException;

import com.yufu.wificarserver.domain.CarStatus;
import com.yufu.wificarserver.util.Constants;

public class SocketCarConnector implements WifiCarConnector {
	private final ArrayList<HeartBeatSocket> connectionList;
	private final ServerSocket serverSocket;
	private final Executor exec;
	private CarStatus carStatus;

	private static SocketCarConnector instance = null;

	public static SocketCarConnector getInstance() throws IOException {
		if (instance == null) {
			synchronized (SocketCarConnector.class) {
				if (instance == null) {
					instance = new SocketCarConnector(Constants.socketServerPort);
				}
			}
		}
		return instance;
	}

	private SocketCarConnector(final int port) throws IOException {
		this.carStatus = new CarStatus();
		this.connectionList = new ArrayList<HeartBeatSocket>();
		this.serverSocket = new ServerSocket(port);
		this.exec = Executors.newCachedThreadPool();
		this.exec.execute(new SocketListeningTask(this));
		this.exec.execute(new ValidSocketCheckingTask(this));
	}

	@Override
	public void send(final String message) {
		for (final HeartBeatSocket eachConnection : this.connectionList) {
			if (eachConnection.isAlive()) {
				try {
					eachConnection.writeLine(message);
				}
				catch (final Exception e) {
					eachConnection.setAlive(false);
				}
			}
		}
	}

	@Override
	public void handleMessage(final String receivedMessage) {
		try {
			this.carStatus = new CarStatus(receivedMessage);
		}
		catch (final Exception e) {
			System.out.println("received an unknown message: " + receivedMessage);
		}
	}

	public int getValidConnectionNum() {
		return this.connectionList.size();
	}

	public synchronized boolean isAvailable() {
		if (this.connectionList.size() == 0) {
			return false;
		}
		else {
			return true;
		}
	}

	//getters
	ArrayList<HeartBeatSocket> getConnectionList() {
		return this.connectionList;
	}

	Executor getExec() {
		return this.exec;
	}

	ServerSocket getServerSocket() {
		return this.serverSocket;
	}

	public CarStatus getCarStatus() {
		try {
			this.carStatus.setAlive(isAvailable());
		}
		catch (final JSONException e) {

		}
		return this.carStatus;
	}
}
