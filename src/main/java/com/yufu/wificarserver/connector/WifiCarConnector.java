package com.yufu.wificarserver.connector;

import java.io.IOException;

public interface WifiCarConnector {

	public void send(String message) throws IOException;

	public void handleMessage(String receivedMessage);
}
