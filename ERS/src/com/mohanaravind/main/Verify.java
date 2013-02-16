package com.mohanaravind.main;


import com.mohanaravind.dbutility.DataStore;
import com.mohanaravind.entity.User;
import com.mohanaravind.utility.SMSVerifier;
import com.mohanaravind.utility.SMSVerifier.SMSVerifierListener;
import com.mohanaravind.utility.SMSVerifier.Status;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;


public class Verify extends Activity {

	
	ProgressDialog mProgress;
	private static Verify mInstance;
	private static String mMessage = "";
	private static Status mStatus = Status.Idle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Set the instance
		mInstance = this;
		
		//Hide the title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_verify);
				
	}

	// Gets triggered when OK button is clicked
	public void btnOK_clicked(View view) {					    
	   		
		//Get the verifier instance
	    SMSVerifier smsVerifier = SMSVerifier.getSMSVerifier(new SMSVerifierHandler());	    
		
	    String phoneNumber = getPhoneNumber();
	    
	    //Set the device phone number
	    smsVerifier.setPhoneNumber(phoneNumber);
			    
	    //Create the verifier thread
		Thread verifierThread = new Thread(smsVerifier, smsVerifier.getClass().getName());
		
		//Start the verification thread
		verifierThread.start();

		//Display progress
		showProgress(phoneNumber);		    
		
	}
	

		
	/**
	 * Retrieves the phone number in the required UI
	 * @return
	 */
	private String getPhoneNumber(){
		
		//Get the Phone number field
		EditText txtPhoneNumber = (EditText) findViewById(R.id.txtPhone);
		
		//Get the phone number which was entered
		String phoneNumber = txtPhoneNumber.getText().toString();
		
				
		//TODO: Validate and format
		
		
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
		mProgress.setTitle("SMS Verification");
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
			    		  //If it was successfully verified
			    		  if(mStatus == Status.Verified){
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
			    user.setUserID(getPhoneNumber());			    
			    DataStore.commitUser(mInstance);
			    
			    mMessage = "Congratulations! Your number is verified.";
			}
				    	    		   
			mProgress.dismiss();
						
			if(status != Status.Verified)
				mMessage = "Unable to verify.\nPlease make sure you have a working SMS plan.";

		}
		

		
	}



}
