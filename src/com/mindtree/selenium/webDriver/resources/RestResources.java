package com.mindtree.selenium.webDriver.resources;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class RestResources {

	HttpClient client = HttpClientBuilder.create().build();
	
	public void getResponse(String endpoint, String resource, String verify) throws ClientProtocolException, IOException {
		HttpGet request =new HttpGet(endpoint+resource);
		HttpResponse response = client.execute(request);
		BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String line="";
		StringBuffer sb= new StringBuffer();
		while((line=br.readLine())!=null) {
			sb.append(line);
		}
		System.out.println(response.getStatusLine());
		System.out.println(sb);
		System.out.println("Trying to find " + verify);
		String actualString=sb.toString();
		String status=response.getStatusLine().toString();
		assertTrue(actualString.contains(verify));
		assertTrue(status.contains("200"));
	}
}
