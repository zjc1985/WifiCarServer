package com.yufu.wificarserver.util;

import java.util.PropertyResourceBundle;

public class Constants {
	public static int socketServerPort = 10000;
	public static int heartBeatInterval = 60000;

	static {
		final PropertyResourceBundle bundle = (PropertyResourceBundle) PropertyResourceBundle.getBundle("common");
		socketServerPort = Integer.parseInt(bundle.getString("socketPort"));
		heartBeatInterval = Integer.parseInt(bundle.getString("socketHeartBeatInterval"));
	}
}
