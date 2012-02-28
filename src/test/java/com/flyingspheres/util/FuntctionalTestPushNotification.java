package com.flyingspheres.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class FuntctionalTestPushNotification {
	private static final String USER_ID = "userIds";
	private static final String MESSAGE = "message";
	private static final String APP_ID = "appId"; 
	
	
	private static final String BASE_URL = "http://mci0lmobd001.empbenins.com:8080/MobileSecurity/services/notificationValidation/sendNotificationToUsers";
	
	
	
	String userIds;
	String appId = "SAS";
	String message;
	@Before
	public void setup(){
		JSONArray array = new JSONArray();
		array.put("Frog");
		array.put("Cat");
		array.put("Cow");
		userIds = array.toString();
		message = "Test Message: " + Calendar.getInstance().getTime().toString();
	}
	
	@Test
	public void pushNotificationsToUsers()
	{
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(USER_ID, userIds);
		parameters.put(MESSAGE, message);
		parameters.put(APP_ID, appId);
		try {
			String response = NetworkManager.postData(BASE_URL, parameters, null);
			Assert.assertNotNull(response);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
