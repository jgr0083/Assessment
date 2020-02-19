package com.mindtree.selenium.webDriver.testNG;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.annotations.Test;

public class RestTest {
	String endpoint="http://okmry52647dns.eastus.cloudapp.azure.com:8089";
	String phone="/rest/api/profile/mobile-number";
	@Test
	public void getResponse() throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request =new HttpGet(endpoint+phone);
		HttpResponse response = client.execute(request);
		BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String line="";
		StringBuffer sb= new StringBuffer();
		while((line=br.readLine())!=null) {
			sb.append(line);
		}
		System.out.println(response.getStatusLine());
		System.out.println(sb);
		String actualString=sb.toString();
		String status=response.getStatusLine().toString();
		assertTrue(actualString.contains("8768578788"));
		assertTrue(status.contains("200"));
	}
}
