/**
 * 
 */
package com.mohanaravind.entity;

import android.app.Activity;

import com.mohanaravind.dbutility.PreferencesHandler;

/**
 * @author Aravind
 *
 */
public class User {

	public enum Status{
		Registered, Unregistered
	}
	
	//Phone number of the user is the USER ID
	private String mUserID;
	
	//Email id of the user
	private String mEmailID;
	
	//Device id of the user
	private String mDeviceID;
	
	//Contains the user's emergency token
	private String mToken;
	
	//Contains the seed informatoin used for generation of login token
	private String mSeed;
	
	//Contains the passphrase information
	private String mPassPhrase;
		
	
	private Status mStatus;
	
	
	
	public User(){
		this.mStatus = Status.Unregistered;
		this.mUserID = "";
	}
	
	
	public Status getStatus(){
		return mStatus;
	}
	
	public String getUserID(){
		return mUserID;
	}
	
	public String getEmailID(){
		return mEmailID;
	}
	
	public String getPassPhrase(){
		return mPassPhrase;
	}
	
	public String getSeed(){
		return mSeed;
	}
	
	public String getDeviceID(){
		return mDeviceID;
	}
	
	public String getToken(){
		return mToken;
	}
	
	
	
	
	public void setUserID(String userID){		
		this.mUserID = userID;
		this.mStatus = Status.Registered;
	}
	
	public void setEmailID(String emailID){
		this.mEmailID = emailID;
	}
	
	public void setDeviceID(String deviceId){
		this.mDeviceID = deviceId;
	}
	
	public void setToken(String token){
		this.mToken = token;
	}
	
	public void setSeed(String seed){
		this.mSeed = seed;
	}
	
	public void setPassPhrase(String passPhrase){
		this.mPassPhrase = passPhrase;
	}

	/**
	 * Retrieves the user data from preferences
	 */
	public void retrieve(Activity activity) {
		PreferencesHandler preferencesHandler = new PreferencesHandler(activity);
		
		//Retrieve the user id
		this.mUserID = preferencesHandler.getPreference("UserID");
		
		//Retrieve the user primary email id
		this.mEmailID = preferencesHandler.getPreference("EmailID");
		
		//Retrieve the device id
		this.mDeviceID = preferencesHandler.getPreference("DeviceID");
		
		//Retrieve the seed
		this.mSeed = preferencesHandler.getPreference("Seed");
		
		//Retrieve the token
		this.mToken = preferencesHandler.getPreference("Token");
		
		//Retrieve the passphrase
		this.mPassPhrase = preferencesHandler.getPreference("PassPhrase");
		
		try {
			//Check whether the user id was retrieved
			if(mUserID.length() > 9)
				this.mStatus = Status.Registered;
		} catch (Exception e) {
			this.mStatus = Status.Unregistered;
		}
						
	}
	
	/**
	 * Saves the user data to preferences
	 * @param activity
	 */
	public void commit(Activity activity){
		//Get the preferences handler
		PreferencesHandler preferencesHandler = new PreferencesHandler(activity);
		
		try{
			preferencesHandler.setPreference("UserID", mUserID);	
			preferencesHandler.setPreference("EmailID", mEmailID);
			preferencesHandler.setPreference("DeviceID", mDeviceID);
			preferencesHandler.setPreference("Seed", mSeed);
			preferencesHandler.setPreference("Token", mToken);
			preferencesHandler.setPreference("PassPhrase", mPassPhrase);
		}catch (Exception ex){
			
		}
	}
	
}
