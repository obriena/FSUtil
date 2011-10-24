package com.flyingspheres.util;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class TestJsonComparator {
	JsonComparator comp = new JsonComparator();
	JSONObject testOne;
	JSONObject testMatchingOne;
	JSONObject testNotMatchingOne;
	
	JSONObject complexOne;
	JSONObject complexMatch;
	JSONObject complexNotMatch;
	
	@Before
	public void setup(){
		try {
			testOne = new JSONObject();
			testOne.put("DeviceId", "1c416d06ad7371d66bdaf397c35773e296a781c7");
			testOne.put("UserId","jw02221");
			testOne.put("Token","1e9d115b-510e-45af-98a2-f9e6dc4c9415");
			testOne.put("CreateDate", 1319126726093l);
			
			testMatchingOne = new JSONObject();
			testMatchingOne.put("DeviceId", "1c416d06ad7371d66bdaf397c35773e296a781c7");
			testMatchingOne.put("UserId","jw02221");
			testMatchingOne.put("Token","1e9d115b-510e-45af-98a2-f9e6dc4c9415");
			testMatchingOne.put("CreateDate", 1319126726093l);
			
			testNotMatchingOne = new JSONObject();
			testNotMatchingOne.put("DeviceId", "1c416d06ad7371d66bdaf397c35773e296a781c7");
			testNotMatchingOne.put("UserId","jw02221");
			testNotMatchingOne.put("Token","1e9d115b-510e-45af-98a2-f9e6dc4c9417");
			testNotMatchingOne.put("CreateDate", 1319126726093l);
			
			complexOne = new JSONObject();
			complexMatch = new JSONObject();
			complexNotMatch = new JSONObject();
			
			complexOne.put("Record", testOne);
			complexMatch.put("Record", testMatchingOne);
			complexNotMatch.put("Record", testNotMatchingOne);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testComparator(){
		int match =	comp.compare(testOne, testMatchingOne);
		Assert.assertTrue(match == 1);
	}
	
	@Test
	public void testNotMatchingComparator(){
		int match =	comp.compare(testOne, testNotMatchingOne);
		Assert.assertTrue(match != 1);
	}
	@Test
	public void testComplexComparator(){
		int match =	comp.compare(complexOne, complexMatch);
		Assert.assertTrue(match == 1);
	}
	
	@Test
	public void testComplexNotMatchingComparator(){
		int match =	comp.compare(complexOne, complexNotMatch);
		Assert.assertTrue(match != 1);
	}
	
	@Test
	public void testMisMatchObject(){
		int match =	comp.compare(complexOne, testNotMatchingOne);
		Assert.assertTrue(match != 1);
	}
	
	@Test
	public void testMatchingCompareWithJsonarray(){
		JSONArray arrayOne = new JSONArray();
		arrayOne.put("Frog");
		arrayOne.put("Cow");
		arrayOne.put("Horse");
		
		JSONArray arrayTwo = new JSONArray();
		arrayTwo.put("Frog");
		arrayTwo.put("Cow");
		arrayTwo.put("Horse");
		
		try {
			testOne.put("array", arrayOne);
			testMatchingOne.put("array", arrayTwo);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		int match =	comp.compare(testOne, testMatchingOne);
		Assert.assertTrue(match == 1);
	}
	
	@Test
	public void testMisMatchingCompareWithJsonarray(){
		JSONArray arrayOne = new JSONArray();
		arrayOne.put("Frog");
		arrayOne.put("Cow");
		arrayOne.put("Horse");
		
		JSONArray arrayTwo = new JSONArray();
		arrayTwo.put("Frog");
		arrayTwo.put("Pig");
		arrayTwo.put("Horse");
		
		try {
			testOne.put("array", arrayOne);
			testNotMatchingOne.put("array", arrayTwo);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		int match =	comp.compare(testOne, testMatchingOne);
		Assert.assertTrue(match != 1);
	}
}
