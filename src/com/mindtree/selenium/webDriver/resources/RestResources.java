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
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

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

		//System.out.println(response.getStatusLine());
		//System.out.println("Trying to find " + verify);
		String actualString=sb.toString();
		String status=response.getStatusLine().toString();
		assertTrue(actualString.contains(verify));
		assertTrue(status.contains("200"));
	}
	
	
	@Test
	public void postResponse() throws ClientProtocolException, IOException {
		Properties p = new Properties();		
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\rest.properties");
		p.load(file);
	
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(p.getProperty("endpoint")+p.getProperty("signin"));

		StringEntity entity = new StringEntity(p.getProperty("post"));
		post.setEntity(entity);
		post.setHeader("Accept", "application/json");
		post.setHeader("Content-type", "application/json");
		CloseableHttpResponse response = client.execute(post);

		BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String line="";
		StringBuffer sb= new StringBuffer();
		while((line=br.readLine())!=null) {
			sb.append(line);
		}
		String actualString=sb.toString();
		int status=response.getStatusLine().getStatusCode();
		
		//System.out.println(response.toString());
		//System.out.println(actualString);
		Assert.assertEquals(status, 202);
		assertTrue(actualString.contains("\"success\":true"));
		client.close();
	}
}
