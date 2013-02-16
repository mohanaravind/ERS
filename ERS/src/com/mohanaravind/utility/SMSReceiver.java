/**
 * 
 */
package com.mohanaravind.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
/**
 * @author Aravind
 *
 *
 *	  
      <uses-permission android:name="android.permission.RECEIVE_SMS" />
  
  		The priority defines how important is this broadcast for this listener
        <receiver android:name="com.mohanaravind.utility.SMSReceiver">
            <intent-filter android:priority="100">
                <action android:name="android.provider.Telephony.SMS_RECEIVED" />
            </intent-filter>
        </receiver>
 *
 */
public class SMSReceiver extends BroadcastReceiver
{

	public enum Status{
		Awaiting, Received, Failed
	}
	
	
	private static String mSender;
	private static String mMessage;
	
	private static Status mStatus = Status.Awaiting;; 

	private static boolean mAbortBroadcast = false;
	
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		try {
			Bundle extras = intent.getExtras();

			Object[] pdus = (Object[])extras.get("pdus");
			for (Object pdu: pdus)
			{
				SmsMessage msg = SmsMessage.createFromPdu((byte[])pdu);

				mSender = msg.getOriginatingAddress();
				mMessage = msg.getMessageBody();	
				
				//Set the success flag
				mStatus = Status.Received;
								
				//If it was opted that notification and other SMS application should not receive this 
				if(mAbortBroadcast)
					abortBroadcast();				
										
			}
		} catch (Exception e) {			
			mStatus = Status.Failed;
		}
	}


	/**
	 * Pass true to stop the default SMS application to display the message
	 * and stop the notification to be popped up
	 * @param abortBroadcast
	 */
	public static void setAbortBroadcast(boolean abortBroadcast){
		mAbortBroadcast = abortBroadcast;
	}
	
	
	/**
	 * @return the mSender
	 */
	public static String getSender() {
		return mSender;
	}


	/**
	 * @return the mMessage
	 */
	public static String getMessage() {
		return mMessage;
	}
	
	/**
	 * @return the mStatus
	 */
	public static Status getStatus() {
		return mStatus;
	}



	/**
	 * Clears up the received message details
	 */
	public static void clearData(){
		mSender = null;
		mMessage = null;
		mStatus = Status.Awaiting;
	}
	
}
