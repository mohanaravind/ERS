package com.mohanaravind.main;


import org.json.JSONException;
import org.json.JSONObject;

import com.mohanaravind.dbutility.DataStore;
import com.mohanaravind.entity.User;
import com.mohanaravind.utility.DeviceInfoProvider;
import com.mohanaravind.utility.HTTPUtility;
import com.mohanaravind.utility.SMSVerifier;
import com.mohanaravind.utility.HTTPUtility.HTTPResult;
import com.mohanaravind.utility.HTTPUtility.IHTTPUtilityListener;
import com.mohanaravind.utility.HTTPUtility.RequestType;
import com.mohanaravind.utility.SMSVerifier.SMSVerifierListener;
import com.mohanaravind.utility.SMSVerifier.Status;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;


public class Verify extends Activity {

	
	ProgressDialog mProgress;
	private static Verify mInstance;
	private static String mMessage = "";
	private static Status mStatus = Status.Idle;
	private static HTTPUtility.Status mHTTPStatus = HTTPUtility.Status.idle;
	
	
	private String mPhoneNumber;
	private String mEmailID;
	private String mDeviceID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Set the instance
		mInstance = this;
		
		//Get the required device information
		getDeviceInfo();
						
		//Hide the title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_verify);
				
		//display the informatoin to UI
		displayInfo();
				
	}
	
	/**
	 * Gets the device related information
	 */
	private void getDeviceInfo(){
    	try {
			//Get the device id            
			DeviceInfoProvider deviceInfoProvider = new DeviceInfoProvider(this);
			this.mDeviceID = deviceInfoProvider.getDeviceID(this);
			
			//Get the user's primary mail id
			this.mEmailID = deviceInfoProvider.getEmailID(this);
		} catch (Exception e) {
			mEmailID = "";
			Log.v("Ara", e.getMessage());
		}
	}

	//Displays the mail id and other verify description messages to UI
	private void displayInfo(){
		//Display the mail id information
		TextView tvVerifyDescription = (TextView)findViewById(R.id.txtVerifyDescription);
		String displayText = tvVerifyDescription.getText().toString();
		displayText += "\n\n2. After verification a passphrase will be sent to " + mEmailID + " for your reference.";
		displayText += "You would need this passphrase to sign into your web ERS account";
		displayText += "\n\nPlease enter your phone number."; 
		tvVerifyDescription.setText(displayText);
	}
	
	
	// Gets triggered when OK button is clicked
	public void btnOK_clicked(View view) {					    
	   		
		//Get the verifier instance
	    SMSVerifier smsVerifier = SMSVerifier.getSMSVerifier(new SMSVerifierHandler());	    
		
	    //Get the phone number
	    this.mPhoneNumber = getPhoneNumber();
	        	    
	    //If its a valid phone number
	    if(this.mPhoneNumber != ""){		    
		    //Set the device phone number
		    smsVerifier.setPhoneNumber(this.mPhoneNumber);
				    
		    //Create the verifier thread
			Thread verifierThread = new Thread(smsVerifier, smsVerifier.getClass().getName());
			
			//Start the verification thread
			verifierThread.start();
	
			//Display progress
			showProgress(this.mPhoneNumber);
	    }
		
	}
	

	
	
		
	/**
	 * Retrieves the phone number in the required UI
	 * @return
	 */
	private String getPhoneNumber(){
		//Declarations
		String phoneNumber = "";
		
		
		try {
			//Get the Phone number field
			EditText txtPhoneNumber = (EditText) findViewById(R.id.txtPhoneNumber);
			
			//Get the phone number which was entered
			phoneNumber = txtPhoneNumber.getText().toString().trim();
		} catch (Exception e) {						
			phoneNumber = "";
			Log.v("Ara", e.getMessage());
		}
		
				
		//TODO: Validate and format the phone number and verify whether there is an accessible internet connection
		
		
		return phoneNumber;
	}
	
	/**
	 * To display the progress
	 * @param phoneNumber
	 */
	private void showProgress(String phoneNumber){
	    String message = "Verifying " + phoneNumber; 
		mProgress = new ProgressDialog(this);		
		mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);		
		mProgress.setTitle("Verification");
		mProgress.setMessage(message);	
		mProgress.setCancelable(false);
		
		mProgress.setOnDismissListener(new DialogInterface.OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				//Display the result
				displayAlert(mMessage);				
			}
		});

		
		mProgress.show();
	}
	
	/**
	 * Alert message to show the final result
	 * @param strMessage
	 */
	private  void displayAlert(String strMessage)
    {
	     new AlertDialog.Builder(this).setMessage(strMessage)  
	           .setTitle("Verification Result")  
	           .setCancelable(true)  
	           .setNeutralButton(android.R.string.ok,  
		          new DialogInterface.OnClickListener() {  
			    	  public void onClick(DialogInterface dialog, int whichButton){
			    		  //If it was successfully verified and registered
			    		  if(mStatus == Status.Verified && mHTTPStatus == HTTPUtility.Status.success){
			    			   Intent intent = new Intent();
			    			   intent.setClass(mInstance, Configure.class);
			    			    
			    			   //Start the verifying activity
			    			   startActivity(intent);
			    			   
			    			   mInstance.finish();
			    		  }		    		  
			          }  
		          })  
		          .show(); 
    }

	
	
	/**
	 * SMS Verifier handler 
	 * @author Aravind
	 *
	 */
	private class SMSVerifierHandler implements SMSVerifierListener{

		@Override
		public void awaitingSMS(int elapsedTime) {				
			mProgress.setProgress(elapsedTime * 10);
		}

		@Override
		public void completedVerification(Status status) {														
			
			//Set the status
			mStatus = status;
			
			if(status == Status.Verified){
				//Store the phone number as user's id
			    User user = DataStore.getUser(mInstance);
			    			
			    //Set the user id
			    user.setUserID(mPhoneNumber);
			    
			    //Set the device id
			    user.setDeviceID(mDeviceID);
			    
			    //set the e mail id
			    user.setEmailID(mEmailID);
			    
			    //Register as a new user
			    registerUser();			    
			}
				    	    		   
						
			if(status != Status.Verified)
				mMessage = "Unable to verify.\nPlease make sure you have a working SMS plan.";

		}
		
		/**
		 * Registers the user as a new user to Cloud
		 */
		private void registerUser(){
			HTTPUtilityHandler myListener = new HTTPUtilityHandler();
        	Resources resources = getResources();
			StringBuilder url = new StringBuilder();
			
			//Get the URI
        	String URI = resources.getString(R.string.url_regiseruser);
        	String apiKey = resources.getString(R.string.apiKey);
                        
        	//Build the URL
        	url.append(URI);
        	
        	//Set the api key
        	url.append("?");
        	url.append("apiKey=");
        	url.append(apiKey);
        	
        	//Set the device id
        	url.append("&");
        	url.append("deviceId=");
        	url.append(mDeviceID);
        	
        	//Set the phone number
        	url.append("&");
        	url.append("phoneNumber=");
        	url.append(mPhoneNumber);
        	
        	//Set the email id
        	url.append("&");
        	url.append("emailId=");
        	url.append(mEmailID);
        	        	
        	//Get the HTTPUtility
        	HTTPUtility httpUtility = new HTTPUtility(url.toString(), myListener, RequestType.GET);
        	
        	//Create the HTTPUtility thread
        	Thread thread = new Thread(httpUtility);
        	
        	//Start the HTTP Request
        	thread.start();
		}
		

		
	}
	
	/**
	 *  Handles the events triggered from HTTPUtility
	 * @author Aravind
	 *
	 */
	private class HTTPUtilityHandler implements IHTTPUtilityListener{

		@Override
		public void responseReceived(HTTPResult httpResult) {
			String passPhrase = "Error 100: Server error! Please contact the developer.";
			String result;
			JSONObject jsonResult;
			Resources resources;
						
			
			try {
				//Get the user instance
				User user = DataStore.getUser(mInstance);
				
				//Get the JSON object from the response
				jsonResult = httpResult.getResponseAsJSONObject();
				
				//Get the resources
				resources = getResources();
				
				//result = jsonResult.getString("Result");
				
				
				//If the result was OK
				//if(result.equals(resources.getString(R.string.ok_verify))){
					//Get the pass phrase
					passPhrase = jsonResult.getString(resources.getString(R.string.json_name_passphrase));
					
					//Set user details
					user.setPassPhrase(passPhrase);
					user.setSeed(jsonResult.getString(resources.getString(R.string.json_name_seed)));
					user.setToken(jsonResult.getString(resources.getString(R.string.json_name_token)));
					
					//Display the passphrase
					mMessage = "Sucessfully verified.\n\nYour passphrase:\n" + passPhrase;		
								
					//Uncomment this
					DataStore.commitUser(mInstance);	
				//}				
								
			
			} catch (JSONException e) {
				mMessage = "Error 100: Server error! Please contact the developer.";
				Log.v("Ara", httpResult.getRawResponse());
			} finally {
				//Set the HTTP Status
				mHTTPStatus = httpResult.getStatus();
				Log.v("Ara", httpResult.getStatus().toString());
			}
					
			//Dismiss the progress dialog
			mProgress.dismiss();
		}

		@Override
		public void responseFailed(HTTPResult httpResult) {
			mHTTPStatus = httpResult.getStatus();
			
			mMessage = "Error 101: Server error! Please contact the developer.";
			
			//Dismiss the progress dialog
			mProgress.dismiss();
		}
		
	}



}
