/**
 * 
 */
package com.mohanaravind.utility;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;

/**
 * @author Aravind
 *
 *<uses-permission android:name="android.permission.SEND_SMS"/>
 */
public class SMSProvider {
	
	
	/**
	 * Default Constructor
	 */
	private SMSProvider(){
		
	}

	
	/**
	 * Returns an instance of SMS Provider
	 * @return
	 */
	public static SMSProvider getSMSProvider(){
		return new SMSProvider();
	}
	
	
	/**
	 * Sends an SMS to another device
	 * @author Aravind
	 * @param phoneNumber
	 * @param message
	 */
	public void sendSMS(String phoneNumber, String message)
	{
		
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, null, null);	
	}
}
