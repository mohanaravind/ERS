/**
 * 
 */
package com.mohanaravind.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONException;

import com.mohanaravind.utility.TokenFactory;
import com.mohanaravind.dbutility.DataStore;
import com.mohanaravind.utility.HTTPUtility;
import com.mohanaravind.utility.HTTPUtility.HTTPResult;
import com.mohanaravind.utility.HTTPUtility.RequestType;
import com.mohanaravind.utility.DeviceInfoProvider;
import com.mohanaravind.utility.SMSProvider;
import com.mohanaravind.utility.SMSReceiver;
import com.mohanaravind.utility.HTTPUtility.IHTTPUtilityListener;
import com.mohanaravind.utility.SMSReceiver.Status;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * @author Aravind
 *
 */
public class WidgetService extends Service {

	public static final String UPDATEMOOD = "UpdateMood";
	public static final String CURRENTMOOD = "CurrentMood";
	
	private String currentMood;
	//private LinkedList<String> moods;
	
	public WidgetService(){
		//this.moods = new LinkedList<String>();
		fillMoodsList();
	}

    private void fillMoodsList() {
    	/*this.moods.add(":)");
    	this.moods.add(":(");
    	this.moods.add(":D");
    	this.moods.add(":X");
    	this.moods.add(":S");
    	this.moods.add(";)");
    	
    	this.currentMood = ";)";*/
    }


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStart(intent, startId);
		
        //Log.i(CurrentMoodWidgetProvider.WIDGETTAG, "onStartCommand");

        updateMood(intent);
			
		stopSelf(startId);
		

		return START_STICKY;
	}

	private String getRandomMood() {
		/*Random r = new Random(Calendar.getInstance().getTimeInMillis());
		int pos = r.nextInt(moods.size());
		return moods.get(pos);*/
		
		return "ara";
	}

	private void updateMood(Intent intent) {
        //Log.i(CurrentMoodWidgetProvider.WIDGETTAG, "This is the intent " + intent);
        if (intent != null){
    		String requestedAction = intent.getAction();
            //Log.i(CurrentMoodWidgetProvider.WIDGETTAG, "This is the action " + requestedAction);
    		if (requestedAction != null && requestedAction.equals(UPDATEMOOD)){
	            this.currentMood = getRandomMood();
	            	            	 
	            int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);

	            //Log.i(CurrentMoodWidgetProvider.WIDGETTAG, "This is the currentMood " + currentMood + " to widget " + widgetId);

	            AppWidgetManager appWidgetMan = AppWidgetManager.getInstance(this);
	            RemoteViews views = new RemoteViews(this.getPackageName(),R.layout.widget_sos);
	            //views.setTextViewText(R.id.widgetMood, currentMood);
	            appWidgetMan.updateAppWidget(widgetId, views);
	            
	            
	            try {
	            	/*
					//Token demo
					TokenFactory tokenFactory = new TokenFactory("17163935750", "6546548849sdf", "swordfish", 1484986);
					
					Integer token = tokenFactory.generateToken();
					
					String strToken = token.toString();
					
					*/
	            	
	            	//HTTP data demo
	            	String response = "Sending...";
	            	
	            	MyListener myListener = new MyListener();
	            	
	            	String URI = "http://emergencymedicalrecords.appspot.com/registeruserservlet?apiKey=23a2A!0aSdsaAFUpqmvA49()564@3Sv18sA413g&deviceId=6546548849sdf&phoneNumber=17163935750";
	            	
	            	HTTPUtility httpUtility = new HTTPUtility(URI, myListener, RequestType.GET);
	            	
	            	Thread thread = new Thread(httpUtility);
	            	
	            	thread.start();
					
	            	Context context = this.getApplicationContext();
	            	TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	            	response = manager.getDeviceId();
	            	
	            	
					//Display a toast asking the user to touch to get the pixel color
					Toast tstMessage = Toast.makeText(this,
							response,
							Toast.LENGTH_LONG);
					
					tstMessage.show();
				} catch (Exception e) {
					Log.v("Ara", e.getMessage());
				}

				

				
				
	            //Log.i(CurrentMoodWidgetProvider.WIDGETTAG, "CurrentMood updated!");
    		}
        }
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	
	private class MyListener implements IHTTPUtilityListener{

		@Override
		public void responseReceived(HTTPResult httpResult) {

			try {
				Log.v("Ara", httpResult.getResponseAsJSONObject().getString("Seed"));
			} catch (JSONException e) {
				Log.v("Ara", httpResult.getRawResponse());
			}
		}

		@Override
		public void responseFailed(HTTPResult httpResult) {
			// TODO Auto-generated method stub
			Log.v("Ara", httpResult.getRawResponse());
		}
		
	}


	
	
}
