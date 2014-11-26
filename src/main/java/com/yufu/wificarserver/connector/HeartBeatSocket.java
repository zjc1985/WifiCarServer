package com.yufu.wificarserver.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HeartBeatSocket {
	private boolean isAlive = false;
	private PrintWriter writer;
	private BufferedReader reader;

	public HeartBeatSocket(final Socket socket, final long interval) throws IOException {
		if (!socket.isClosed() && socket.isConnected()) {
			setAlive(true);
			this.writer = new PrintWriter(socket.getOutputStream());
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (isAlive()) {
						try {
							Thread.sleep(interval);
						}
						catch (final InterruptedException e) {
							e.printStackTrace();
						}

						try {
							socket.sendUrgentData(0);
						}
						catch (final Exception e) {
							System.out.println("dead connection detected,prepare to close");
							setAlive(false);
							try {
								HeartBeatSocket.this.writer.close();
								HeartBeatSocket.this.reader.close();
								socket.close();
							}
							catch (final IOException e1) {
							}
						}

					}
					System.out.println("prepare to exit heartBeat Thread");
				}
			}).start();
		}
		else {
			throw new RuntimeException("need live socket to init");
		}
	}

	public void writeLine(final String message) {
		this.writer.println(message);
		this.writer.flush();
	}

	public String readLine() throws IOException {
		return this.reader.readLine();
	}

	public synchronized boolean isAlive() {
		return this.isAlive;
	}

	public synchronized void setAlive(final boolean isAlive) {
		this.isAlive = isAlive;
	}

}
