package com.flyingspheres.util;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

public class FunctionalTestNetworkManager {
	
	private static final String TEST_URL = "http://www.google.com";

	@Test
	public void testHttpRetrieval(){
		String response = NetworkManager.retrieveRawContent(TEST_URL);
		Assert.assertNotNull(response);
		Assert.assertTrue(response.contains("I&#39;m Feeling Lucky"));
	}
	
	@Test
	public void testHttpPost(){
		File testFile = new File("README");
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("author", "aaron");
		parameterMap.put("comment", "test Upload: " + Calendar.getInstance().toString());
		String response;
		try {
			response = NetworkManager.postData("http://localhost:8080/Services/services/upload/picture/", testFile, parameterMap);
			Assert.assertNotNull(response);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
}
