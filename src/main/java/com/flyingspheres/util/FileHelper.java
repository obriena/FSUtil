package com.flyingspheres.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileHelper {

	public static String readFileContents(File testFile) throws IOException  {
		StringBuffer buffer = new StringBuffer();
		FileInputStream fis = new FileInputStream(testFile);
		byte[] data = new byte[1024];
		int amtRead = fis.read(data);
		while (amtRead > -1){
			buffer.append(new String(data, 0, amtRead));
			data = null;
			data = new byte[1024];
			amtRead = fis.read(data);
		}
		return buffer.toString();
	}
	
	public static void writeContentsToFile(String content, File fileToWrite) throws IOException{
		OutputStream fos = new FileOutputStream(fileToWrite);
		fos.write(content.getBytes());
	}
}
