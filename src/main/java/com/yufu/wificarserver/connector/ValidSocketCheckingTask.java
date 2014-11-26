package com.yufu.wificarserver.connector;

public class ValidSocketCheckingTask implements Runnable {
	private final SocketCarConnector carConnector;

	public ValidSocketCheckingTask(final SocketCarConnector socketCarConnector) {
		this.carConnector = socketCarConnector;
	}

	@Override
	public void run() {
		while (true) {
			final int size = this.carConnector.getConnectionList().size();
			for (int i = size - 1; i >= 0; i--) {
				if (this.carConnector.getConnectionList().get(i).isAlive() == false) {
					this.carConnector.getConnectionList().remove(i);
				}
			}
			//System.out.println(this.carConnector.getConnectionList().size() + " valid connections");
			sleep(6);
		}
	}

	private void sleep(final int sleepSeconds) {
		try {
			Thread.sleep(sleepSeconds * 1000);
		}
		catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

}
