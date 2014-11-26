package com.yufu.wificarserver.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class CarStatus extends JSONObject {
	private static String key_wifi = "wifi";
	private static String key_battery = "battery";
	private static String key_isAlive = "isAlive";
	private static String key_isStreaming = "isStreaming";
	private static String key_rtspUrl = "rtspUrl";

	public CarStatus(final String jsonString) throws JSONException {
		super(jsonString);
	}

	public CarStatus() {
		try {
			setWifi(-200);
			setBattery(0);
			setAlive(false);
			setStreaming(false);
			setRtspUrl("unknown");
		}
		catch (final Exception e) {
			// TODO: handle exception
		}
	}

	public void setWifi(final int wifiStrength) throws JSONException {
		put(key_wifi, wifiStrength);
	}

	public int getWifi() throws JSONException {
		return getInt(key_wifi);
	}

	public void setBattery(final int battery) throws JSONException {
		put(key_battery, battery);
	}

	public int getBattery() throws JSONException {
		return getInt(key_battery);
	}

	public boolean isAlive() throws JSONException {
		return getBoolean(key_isAlive);
	}

	public void setAlive(final boolean isAlive) throws JSONException {
		put(key_isAlive, isAlive);
	}

	public boolean isStreaming() throws JSONException {
		return getBoolean(key_isStreaming);
	}

	private void setStreaming(final boolean isStreaming) throws JSONException {
		put(key_isStreaming, isStreaming);
	}

	public String getRtspUrl() throws JSONException {
		return getString(key_rtspUrl);
	}

	private void setRtspUrl(final String rtspUrl) throws JSONException {
		put(key_rtspUrl, rtspUrl);
	}
}
