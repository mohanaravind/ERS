/**
 * 
 */
package com.mohanaravind.utility;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * @author Aravind
 *
 * Make sure to hae this permission in Android manifest file
 * <uses-permission android:name="android.permission.INTERNET"/>
 */
public class HTTPUtility implements Runnable {
	

	
	//Inputs
	String _URI;
	RequestType _RequestType;
	IHTTPUtilityListener _httpUtilityListener;
	HashMap<String, String> _PostParameters;
	
	public enum Status{
		success, failed, starting, idle
	}
	
	public enum RequestType{
		GET, POST
	}
	
	/**
	 * Sets the post parameters which would be used for the HTTP Post request
	 */
	public void setPostParameters(HashMap<String, String> postParameters){
		this._PostParameters = postParameters;
	}

	
	/**
	 * Constructor
	 * @param URI
	 * @param httpUtilityListener
	 */
	public HTTPUtility(String URI, IHTTPUtilityListener httpUtilityListener, RequestType requestType){
		this._httpUtilityListener = httpUtilityListener;
		this._URI = URI;
		this._RequestType = requestType;
	}
	
	
	
	
	@Override
	public void run() {		
		try {
			switch (_RequestType) {
				case GET:
					getHTTPData();
					break;
				case POST:
					sendHTTPPost();
					break;				
			}
		} catch (Exception e) {
			Log.v("Ara",e.getMessage());
		}
	}
	
	/**
	 * Gets the HTTP data through HTTP Get request
	 * @param URI
	 * @return
	 */
	private void getHTTPData() {
	    StringBuilder builder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet(this._URI);
	    HTTPResult httpResult = null;
	    try {
	      HttpResponse response = client.execute(httpGet);
	      StatusLine statusLine = response.getStatusLine();
	      int statusCode = statusLine.getStatusCode();
	      	     	      
	      //If the request was successful
	      if (statusCode == 200) {
	        HttpEntity entity = response.getEntity();
	        InputStream content = entity.getContent();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
	        String line;
	        
	        //Read the data
	        while ((line = reader.readLine()) != null) {
	          builder.append(line);
	        }
	        	        
	        //Set the result
	        httpResult = new HTTPResult(builder.toString(), Status.success);
	        _httpUtilityListener.responseReceived(httpResult);
	        
	      } else {
		        //Set the result
		        httpResult = new HTTPResult("", Status.failed);
		        _httpUtilityListener.responseFailed(httpResult);
	      }	    
	    }catch (Exception ex){
	        //Set the result
	        httpResult = new HTTPResult("", Status.failed);
	        _httpUtilityListener.responseFailed(httpResult);
	    }
	    
	  }
	
	/**
	 * Makes an HTTP Post request
	 */
	private void sendHTTPPost(){
	    StringBuilder builder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpPost httpPOST = new HttpPost(this._URI);
	    
	    //Add the post parameters
	    addPostParameters(httpPOST);
	    	    
	    HTTPResult httpResult = null;
	    try {
	      HttpResponse response = client.execute(httpPOST);
	      StatusLine statusLine = response.getStatusLine();
	      int statusCode = statusLine.getStatusCode();
	      	     	      
	      //If the request was successful
	      if (statusCode == 200) {
	        HttpEntity entity = response.getEntity();
	        InputStream content = entity.getContent();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
	        String line;
	        
	        //Read the data
	        while ((line = reader.readLine()) != null) {
	          builder.append(line);
	        }
	        	        
	        //Set the result
	        httpResult = new HTTPResult(builder.toString(), Status.success);
	        _httpUtilityListener.responseReceived(httpResult);
	        
	      } else {
		        //Set the result
		        httpResult = new HTTPResult("", Status.failed);
		        _httpUtilityListener.responseFailed(httpResult);
	      }	    
	    }catch (Exception ex){
	        //Set the result
	        httpResult = new HTTPResult("", Status.failed);
	        _httpUtilityListener.responseFailed(httpResult);
	    }
	}
	
	
	/**
	 * Add the post parameters
	 * @param httpPost
	 * @return
	 */
	private HttpPost addPostParameters(HttpPost httpPost){
		
		try {
			for (Entry<String,String> parameter : _PostParameters.entrySet()) {
				httpPost.addHeader(parameter.getKey(), parameter.getValue());
			}
		} catch (Exception e) {
			Log.v("Ara","Error while adding the post parameters");
		}
		
		return httpPost;
	}
	
	/**
	 * The class definition which has the HTTP Result which was fetched 
	 * @author Aravind
	 *
	 */
	public class HTTPResult{
		String _RawResponse;
		Status _Status = Status.idle;
		
		/**
		 * Constructor
		 * @param response
		 * @param success
		 */
		public HTTPResult(String response, Status success) {
			this._RawResponse = response;
			this._Status = success;
		}
		
		/**
		 * Fields
		 * @return
		 */
		public String getRawResponse(){return this._RawResponse;}
		public Status getStatus(){return this._Status;}
		
		/**
		 * Returns JSON object of the response
		 * @return
		 */
		public JSONObject getResponseAsJSONObject(){
			JSONObject result = null;
			
			try {
				result = new JSONObject(this._RawResponse);
			} catch (JSONException e) {
				result = null;				
			}
			
			return result;
		}
		
	}
	
	
	
	
	/**
	 * The interface which has to be implemented to listen to HTTPUtility responses
	 * @author Aravind
	 *
	 */
	public interface IHTTPUtilityListener{
		public void responseReceived(HTTPResult httpResult);
		
		public void responseFailed(HTTPResult httpResult);
	}
	


}
