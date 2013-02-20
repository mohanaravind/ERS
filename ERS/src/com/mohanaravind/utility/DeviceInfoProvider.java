/**
 * 
 */
package com.mohanaravind.utility;

import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Patterns;

/**
 * @author Aravind
 *
 *    This is for getting the device id
 *    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
 *    
 *    This is for getting primary email id
 *    <uses-permission android:name="android.permission.GET_ACCOUNTS" />
 *
 */   
public class DeviceInfoProvider {
	
	/**
	 * Fields
	 */
	private Activity _Activity;
	
	
	/**
	 * Constructor
	 * @param activity
	 */
	public DeviceInfoProvider(Activity activity){
		this._Activity = activity;
	}
	
	/**
	 * Returns the device id
	 * @param context
	 * @return
	 */
	public String getDeviceID(Context context) {
		TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId;
      
        if (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
        	//Tablet
        	deviceId = Secure.getString(this._Activity.getContentResolver(), Secure.ANDROID_ID);

        } else {
        	//Mobile
        	deviceId = manager.getDeviceId();

        }
        
        return deviceId;
	}
	
	public String getEmailID(Context context){
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(context).getAccounts();
		String emailID = "";
		
		
		for (Account account : accounts) {
		    if (emailPattern.matcher(account.name).matches()) {
		    	//Possible email id
		    	emailID = account.name;
		        break;
		    }
		}
		
		return emailID;
	}
	
}
