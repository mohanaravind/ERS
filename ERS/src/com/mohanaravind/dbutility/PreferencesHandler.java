/**
 * 
 */
package com.mohanaravind.dbutility;


import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * @author Aravind
 *
 */
public class PreferencesHandler {

	public static final String PREFS_NAME = "ECSPreferencesFile";
	
	public static final String DEBUG_FLAG = "Ara";
	
	Activity mActivity;
	
	
	public PreferencesHandler(Activity activity){
		this.mActivity = activity;
	}
		
	/**
	 * Set the preference 
	 * @param key
	 * @param value
	 */
	public void setPreference(String key, String value){
		try{
			  //We need an Editor object to make preference changes.
			  // All objects are from android.context.Context
			  SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
			  SharedPreferences.Editor editor = settings.edit();
			  editor.putString(key, value);

			  // Commit the edits!
		      editor.commit();
		} catch (Exception e) {
			Log.d(DEBUG_FLAG, e.getMessage());
		}
	}
	
	/**
	 * Get the stored preference value using the key
	 * Returns null if not present
	 * @param key
	 * @return
	 */
	public String getPreference(String key){
		String value = null;
		
		try{
			// Restore preferences
		       SharedPreferences settings = mActivity.getSharedPreferences(PREFS_NAME, 0);
		       value = settings.getString(key, null);
		}catch (Exception e) {
			Log.d(DEBUG_FLAG, e.getMessage());
		}
		
		return value;
	}
	
}
