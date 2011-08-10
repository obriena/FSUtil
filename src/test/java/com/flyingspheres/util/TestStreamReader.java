package com.flyingspheres.util;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Test;

public class TestStreamReader {

	@Test
	public void testStreamReader(){
		InputStream is = this.getClass().getResourceAsStream("/testFile.txt");
		String content = null;
		try {
			content = StreamReader.convertStreamToString(is);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		Assert.assertTrue(content.equals("Hello World!"));
		
	}
	
}
