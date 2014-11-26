package com.yufu.wificarserver.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConstantsTester {

	@Test
	public void test() {
		assertEquals(10000, Constants.socketServerPort);
		assertEquals(60000, Constants.heartBeatInterval);
	}

}
