package com.mindtree.selenium.webDriver.miscTests;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.sun.mail.iap.Response;
/*
 * This class is for debugging only
 */
public class RestTest {
	String endpoint="http://okmry52647dns.eastus.cloudapp.azure.com:8089";
	String phone="/rest/api/profile/mobile-number";
	String email="/rest/api/profile/email-id";
	String profile="/rest/api/profile/all";
	String specificProfile="/rest/api/profile/102";
	String order="/rest/api/order/";
	String signin="/rest/api/login/signin";

	@Ignore
	public void getResponse() throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request =new HttpGet(endpoint+specificProfile);
		HttpResponse response = client.execute(request);
		BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String line="";
		StringBuffer sb= new StringBuffer();
		while((line=br.readLine())!=null) {
			sb.append(line);
		}
		
		JSONObject myResponse= new JSONObject(sb.toString());
		
		//System.out.println(response.getStatusLine());		
		System.out.println(myResponse.getJSONObject("body"));
		
		//System.out.println(myResponse.getJSONObject("body").getInt("userId"));
		//System.out.println("Trying to find " + verify);
		String actualString=sb.toString();
		String status=response.getStatusLine().toString();
	//	assertTrue(actualString.contains("8768578788"));
		assertTrue(status.contains("200"));
	}
	
	@Test
	public void post() throws IOException {
		Properties p = new Properties();		
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\rest.properties");
		p.load(file);
	
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(endpoint+signin);

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
		String status=response.getStatusLine().toString();
		
		System.out.println(response.toString());
		System.out.println("Content: " + actualString);
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 202);
		assertTrue(actualString.contains("\"success\":true"));
		client.close();
	}
	
}
