package com.flyingspheres.util;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class NetworkManager {
	/**
	 * Accept an http url such as http://www.google.com 
	 * perform a get request and return the response from the server
	 * @param httpUrl
	 * @return content retrieved based on param
	 */
	public static String retrieveRawContent(String string) {
        StringBuilder response = new StringBuilder();
		URL url = null;
		try{
			url = new URL(string);
			URLConnection connection = url.openConnection();

			InputStream is = connection.getInputStream();
			byte[] bytes = new byte[1024];
            int amtRead = is.read(bytes);
            while (amtRead > 0){
             	response.append(new String(bytes, 0, amtRead));
             	bytes = null;
             	bytes = new byte[1024];
             	amtRead = is.read(bytes);
            }
            connection.getInputStream().close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return response.toString();
	}
}
