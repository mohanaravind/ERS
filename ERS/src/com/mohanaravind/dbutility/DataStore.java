/**
 * 
 */
package com.mohanaravind.dbutility;

import android.app.Activity;

import com.mohanaravind.entity.EmergencyContactInfo;
import com.mohanaravind.entity.History;
import com.mohanaravind.entity.User;

/**
 * A singleton class which provides all the required data for the application
 * @author Aravind
 *
 */
public class DataStore {

	
	private static User mUser;
	
	private static EmergencyContactInfo mEmergencyContactInfo;
	private static History mHistory;
	
	private static boolean mTriedRetrieving = false;
	
	private static Activity mActivity;
	


	
	/**
	 * Constructor
	 */
	private DataStore(){
		
	}
	
	


	/**
	 * @return the emergencyContactInfo
	 */
	public static EmergencyContactInfo getEmergencyContactInfo(Activity activity) {
		mActivity = activity; 
		
		retrieveEmergencyContactInfo();
		
		return mEmergencyContactInfo;
	}


	/**
	 * @param emergencyContactInfo the emergencyContactInfo to set
	 */
	public static void setEmergencyContactInfo(EmergencyContactInfo emergencyContactInfo) {
		mEmergencyContactInfo = emergencyContactInfo;				
	}


	/**
	 * @return the history
	 */
	public static History getHistory(Activity activity) {
		mActivity = activity;
		
		return mHistory;
	}


	/**
	 * @param history the history to set
	 */
	public static void setHistory(History history) {
		mHistory = history;
	}
	
	
	/**
	 * Committed methods
	 * @param activity
	 */
	public static void commitEmergencyContactInfo(Activity activity){
		mEmergencyContactInfo.commit(activity);
	}
	
	
	/**
	 * Retrieve the data from preferences
	 */
	private static void retrieveEmergencyContactInfo(){
		
		if(mTriedRetrieving)
			return;
		
		//Try retrieving data
		mEmergencyContactInfo = new EmergencyContactInfo();
		mEmergencyContactInfo.retrieve(mActivity);		
				
		//Set the retrieval flag to true
		mTriedRetrieving = true;
	
	}



	
	/**
	 * Retrieves the user data from preferences
	 * @return
	 */
	public static User getUser(Activity activity){
	
		//If the user data is not retrieved yet 
		if(mUser == null){
			mUser = new User();
			mUser.retrieve(activity);
		}
		
		return mUser;
	}
	
	/***
	 * Commits the user data to preferences
	 * @param activity
	 */
	public static void commitUser(Activity activity){
		mUser.commit(activity);
	}
	
}
