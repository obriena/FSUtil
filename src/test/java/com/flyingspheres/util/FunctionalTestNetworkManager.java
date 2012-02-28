package com.flyingspheres.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

public class FunctionalTestNetworkManager {
	
	private static final String TEST_URL = "http://www.google.com";

	@Test
	public void testHttpRetrieval(){
		try {
			String response = NetworkManager.retrieveRawContent(TEST_URL);
			Assert.assertNotNull(response);
			Assert.assertTrue(response.contains("I&#39;m Feeling Lucky"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testHttpPostWithFile(){
		File testFile = new File("README");
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("author", "aaron");
		parameterMap.put("comment", "test Upload: " + Calendar.getInstance().toString());
		String response;
		try {
			response = NetworkManager.postDataWithFile("http://localhost:8080/Services/services/upload/picture/", "fileToLoad", testFile, parameterMap, null);
			Assert.assertNotNull(response);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testHttpPost(){
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("userIds", "[\"frog\",\"toad\"]");
		parameterMap.put("message", "test Upload: " + Calendar.getInstance().getTime().toLocaleString());
		parameterMap.put("applicationId", "SAS");
		String response;
		try {
			response = NetworkManager.postData("http://mci0lmobd001.empbenins.com:8080/MobileSecurity/services/notificationValidation/sendNotificationToUsers", parameterMap, null);
			Assert.assertNotNull(response);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
}
