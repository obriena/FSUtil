package com.flyingspheres.util;

import junit.framework.Assert;

import org.junit.Test;

public class TestNetworkManager {
	
	private static final String TEST_URL = "http://www.google.com";

	@Test
	public void testHttpRetrieval(){
		String response = NetworkManager.retrieveRawContent(TEST_URL);
		Assert.assertNotNull(response);
		Assert.assertTrue(response.contains("I&#39;m Feeling Lucky"));
	}
}
