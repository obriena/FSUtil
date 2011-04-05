package com.flyingspheres.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class TestJsonAdapter {
	SimpleTester simpleTester;
	ComplexTester complexTester;
	VeryComplexTester vComplex;
	
	@Before
	public void setup(){
		simpleTester = new SimpleTester();
		simpleTester.setStringValue("Hello World");
		simpleTester.setIntValue(42);
		simpleTester.setLongValue(123123123123123l);
		simpleTester.setFloatValue(0.5f);
		simpleTester.setDoubleValue(22d/7);
		
		List<String> strings = new ArrayList<String>();
		strings.add("Aaron");strings.add("Amy");strings.add("Tristan");strings.add("Rhiannon");strings.add("Anastasia");
		complexTester = new ComplexTester();
		complexTester.setCount(1);
		complexTester.setSimpleTester(simpleTester);
		complexTester.setNames(strings);
		
		vComplex = new VeryComplexTester();
		vComplex.setCount(5);
		List<SimpleTester> testList = new ArrayList<SimpleTester>();
		testList.add(simpleTester);
		List<ComplexTester> cTestList = new ArrayList<ComplexTester>();
		cTestList.add(complexTester);
		vComplex.setComplexTesterList(cTestList);
		vComplex.setSimpleTesterList(testList);
	}
	
	@Test
	public void testStringConversion(){
		JSONObject jObj = null;
		try {
			jObj = JsonAdapter.objectTypeToJson("hello", null, false);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (JSONException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (InstantiationException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertNotNull(jObj);
		try{
			System.out.println(jObj.toString(3));
		} catch(Exception e){}
	}
	
	@Test
	public void testVeryComplexTester(){
		try {
			JSONObject jObj = JsonAdapter.objectTypeToJson(vComplex, Convertible.class, true);
			Assert.assertNotNull(jObj);
			System.out.println(jObj.toString(3));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (JSONException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (InstantiationException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	
	@Test
	public void testComplexTester(){
		try {
			JSONObject jObj = JsonAdapter.objectTypeToJson(complexTester, Convertible.class, true);
			Assert.assertNotNull(jObj);
			System.out.println(jObj.toString(3));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (JSONException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (InstantiationException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testSimpleTester(){
		try {
			JSONObject jObj = JsonAdapter.objectTypeToJson(simpleTester, Convertible.class, false);
			Assert.assertNotNull(jObj);
			System.out.println(jObj.toString(3));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (JSONException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (InstantiationException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}	
	
	private class SimpleTester implements Convertible{
		private String stringValue;
		private int intValue;
		private long longValue;
		private float floatValue;
		private double doubleValue;
		public String getStringValue() {
			return stringValue;
		}
		public void setStringValue(String stringValue) {
			this.stringValue = stringValue;
		}
		public int getIntValue() {
			return intValue;
		}
		public void setIntValue(int intValue) {
			this.intValue = intValue;
		}
		public long getLongValue() {
			return longValue;
		}
		public void setLongValue(long longValue) {
			this.longValue = longValue;
		}
		public float getFloatValue() {
			return floatValue;
		}
		public void setFloatValue(float floatValue) {
			this.floatValue = floatValue;
		}
		public double getDoubleValue() {
			return doubleValue;
		}
		public void setDoubleValue(double doubleValue) {
			this.doubleValue = doubleValue;
		}
		
	}
	
	private class VeryComplexTester implements Convertible{
		List<SimpleTester> simpleTesterList;
		List<ComplexTester> complexTesterList;
		int count;
		public List<SimpleTester> getSimpleTesterList() {
			return simpleTesterList;
		}
		public void setSimpleTesterList(List<SimpleTester> simpleTester) {
			this.simpleTesterList = simpleTester;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public List<ComplexTester> getComplexTesterList() {
			return complexTesterList;
		}
		public void setComplexTesterList(List<ComplexTester> complexTesterList) {
			this.complexTesterList = complexTesterList;
		}
		
	}
	
	private class ComplexTester implements Convertible{
		SimpleTester simpleTester;
		List<String> names;
		int count;
		public SimpleTester getSimpleTester() {
			return simpleTester;
		}
		public void setSimpleTester(SimpleTester simpleTester) {
			this.simpleTester = simpleTester;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public List<String> getNames() {
			return names;
		}
		public void setNames(List<String> names) {
			this.names = names;
		}
		
	}
	
	private interface Convertible{};
}

