package com.flyingspheres.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamReader {

	public static String convertStreamToString(InputStream is) throws IOException{
		byte[] buff = new byte[1024];
		int amtRead = is.read(buff);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while (amtRead > -1){
			bos.write(buff, 0, amtRead);
			buff = null;
			buff = new byte[1024];
			amtRead = is.read(buff);
		}
		return new String(bos.toByteArray());
	}
}
