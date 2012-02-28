package com.flyingspheres.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;

public class NetworkManager {
	/**
	 * Accept an http url such as http://www.google.com 
	 * perform a get request and return the response from the server
	 * @param httpUrl
	 * @return content retrieved based on param
	 * @throws IOException 
	 */
	public static String retrieveRawContent(String string) throws IOException {
        StringBuilder response = new StringBuilder();
		URL url = null;
		url = new URL(string);
		URLConnection connection = url.openConnection();

		InputStream is = connection.getInputStream();
		byte[] bytes = new byte[1024];
        int amtRead = is.read(bytes);
        while (amtRead > 0){
         	response.append(new String(bytes, 0, amtRead, Charset.forName("UTF-8")));
         	bytes = null;
         	bytes = new byte[1024];
         	amtRead = is.read(bytes);
        }
        connection.getInputStream().close();
		return response.toString();
	}
	
	public static String postData(String postUrl, Map<String, String>paramterMap, Map<String, String>headerMap) throws Exception{
		return postDataWithFile(postUrl, null, null, paramterMap, headerMap);
	}
	public static String postDataWithFile(String postUrl, String fileVarName, File fileToLoad, Map<String, String>paramterMap, Map<String, String>headerMap) throws Exception{
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		
		HttpPost httpPost = new HttpPost(postUrl);
		MultipartEntity multiEntity = new MultipartEntity();
		
		if (headerMap != null)
		{
			Set<String> keys = headerMap.keySet();
			for (String key : keys)
			{
				httpPost.setHeader(key, headerMap.get(key));
			}
		}
		
		
		if (fileToLoad != null){
			httpPost.setHeader("enctype", "multipart/form-data");
			multiEntity.addPart(new FormBodyPart(fileVarName, new InputStreamBody(new FileInputStream(fileToLoad), fileToLoad.getName())));

			for (String key : paramterMap.keySet()){
				multiEntity.addPart(key, new StringBody(paramterMap.get(key)));
				httpPost.setEntity(multiEntity);
			}
			
		} else {
			httpPost.setHeader("enctype", "application/x-www-form-urlencoded");
			List<NameValuePair> nvp = new ArrayList<NameValuePair>();
			for (String key : paramterMap.keySet()){
				nvp.add(new BasicNameValuePair(key, paramterMap.get(key)));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvp));
		}


		HttpResponse response = client.execute(httpPost);
		System.out.println(response.getStatusLine().getStatusCode() + response.getStatusLine().getReasonPhrase());
		InputStream is= response.getEntity().getContent();
		return StreamReader.convertStreamToString(is);
		
	}
	
	public static String callServiceViaGet(String urlString, Map<String, String> parameters, Map<String, String> headers){
		StringBuilder response = new StringBuilder();
		URL url = null;
		try{
			url = new URL(urlString);
			URLConnection connection = url.openConnection();
			
			connection.setConnectTimeout(5000);
			connection.setDoOutput(true);//I think this is really for post requests
			for (String key : headers.keySet()){
				connection.setRequestProperty(key, headers.get(key));
			}
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
