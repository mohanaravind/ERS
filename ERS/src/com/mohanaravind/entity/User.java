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
	
	public void setUserID(String userID){		
		this.mUserID = userID;
		this.mStatus = Status.Registered;
	}

	/**
	 * Retrieves the user data from preferences
	 */
	public void retrieve(Activity activity) {
		PreferencesHandler preferencesHandler = new PreferencesHandler(activity);
		
		//Retrieve the user id
		this.mUserID = preferencesHandler.getPreference("UserID");
		
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
		}catch (Exception ex){
			
		}
	}
	
}
