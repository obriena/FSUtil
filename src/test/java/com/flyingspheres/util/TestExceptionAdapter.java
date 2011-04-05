package com.flyingspheres.util;

import junit.framework.Assert;

import org.junit.Test;

public class TestExceptionAdapter {
	ExceptionAdapter adapter = new ExceptionAdapter();
	
	@Test
	public void testAdapter(){
		Exception ex = null;
		try{
			throw new RuntimeException();
		} catch (Exception e){
			ex = e;
		}
		String eString = ExceptionAdapter.convertThrowable(ex);
		Assert.assertNotNull(eString);
		Assert.assertTrue(eString.contains("com.flyingspheres.util.TestExceptionAdapter.testAdapter"));
	}
}
