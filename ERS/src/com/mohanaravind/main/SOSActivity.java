package com.mohanaravind.main;

import java.util.HashMap;
import java.util.Map.Entry;

import com.mohanaravind.dbutility.DataStore;
import com.mohanaravind.entity.EmergencyContactInfo;
import com.mohanaravind.entity.User;
import com.mohanaravind.utility.ContactProvider.ContactDetail;

import com.mohanaravind.utility.SMSProvider;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class SOSActivity extends Activity {

	
	ProgressDialog mProgress;
	StringBuilder _details;
	Activity _activity;
	Location _location;
	Boolean _hasSent;
	TextView _tvDetails; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Sets the application to full screen mode
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
				
		setContentView(R.layout.activity_sos);
		
		_activity = this;
		_hasSent = false;
		
		//Display the result
		_tvDetails = (TextView)findViewById(R.id.tvDetails);
		
		
		showProgress();
		getUserLocation();
	}

	
	// Gets triggered when continue button is clicked
	public void btnOk_clicked(View view) {
		
		this.finish();
	}
	/**
	 * Event handler class
	 * @author Aravind
	 *
	 */
	private class LocationProviderListener implements LocationListener{
		
		@Override
		public void onLocationChanged(Location location) {
			//Set the location
			_location = location;
			
			//Send SOS message
			startSOS();
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		
	}
	
	
	private String getUserLocation(){
		String userLocation = "Unknown";
		
		try {
			//Create the location manager
			LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

			//Create the location listener			
			LocationListener locationListener = new LocationProviderListener();
					
			// Register the listener with the Location Manager to receive location updates
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
			
		} catch (Exception e) {
			Log.v("Ara", "getUserLocation: Error while getting user location." + e.getMessage());
		}
		
		return userLocation;
	}
	
	
	/**
	 * To display the progress
	 * @param phoneNumber
	 */
	private void showProgress(){
	    try {
			String message = "Sending SMS..."; 
			mProgress = new ProgressDialog(this);		
			mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);		
			mProgress.setTitle("SOS");
			mProgress.setMessage(message);	
			mProgress.setCancelable(false);
			
			mProgress.setOnDismissListener(new DialogInterface.OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface dialog) {
					Log.v("Ara", _details.toString());
					_tvDetails.setText(_details.toString());
				}
			});

			
			mProgress.show();
		} catch (Exception e) {
			Log.v("Ara", "showProgress: Error while trying to display progress dialog\n" + e.getMessage());
		}
	}

	/**
	 * Sends the SOS messages
	 */
	private void startSOS(){
		try {
			User user = DataStore.getUser(this);
			EmergencyContactInfo emergencyContactInfo = DataStore.getEmergencyContactInfo(this);
			
			SOSMessenger sosMessenger = new SOSMessenger(user, emergencyContactInfo);
			
			_activity = this;
			
			if(!_hasSent){
				//Start the thread to send the SOS messages
				Thread messenger = new Thread(sosMessenger);
				messenger.start();
			}
			
			
		} catch (Exception e) {
			Log.v("Ara", "startSOS: Error while trying to start the thread");
		}
	}
	

	private class SOSMessenger implements Runnable{

		User _user;
		EmergencyContactInfo _emergencyContactInfo;

		
		
		/**
		 * Constructor
		 * @param user
		 */
		public SOSMessenger(User user, EmergencyContactInfo emergencyContactInfo){
			this._user = user;
			this._emergencyContactInfo = emergencyContactInfo;
		}
		
		@Override
		public void run() {		
			
			
			
			try {

				sendSOSMessages();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.v("Ara", "run" +  e.getMessage());
			}
			
			
		}
		
		private void sendSOSMessages(){
			try {
				
				//If the message has already been sent
				if(_hasSent)
					return;
				
				_details = new StringBuilder();
				
				HashMap<String, ContactDetail> contacts = _emergencyContactInfo.getContactDetails();
				SMSProvider smsProvider = SMSProvider.getSMSProvider();
				
				String message = buildSOSMessage();
				
				for (Entry<String, ContactDetail> contact : contacts.entrySet()) {
					ContactDetail detail = contact.getValue();
					
					
					String contactNumber = detail.getPhoneNumber();
					contactNumber = getValidPhoneNumber(contactNumber);
					
					_details.append("Message was sent to:\n");
					_details.append(detail.getDisplayName());
					_details.append("\n");
					_details.append("Message Content:");
					_details.append(message);
					_details.append("\n\n");
									
					Log.v("Ara", _details.toString());
					
					
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						Log.v("Ara", "run" +  e.getMessage());
					}
					
					Log.v("Ara", _details.toString());
					smsProvider.sendSMS(contactNumber, message);					
				}
			} catch (Exception e) {
				Log.v("Ara", "sendSOSMessages: error in sending SMS" +  e.getMessage());
			} finally {
				_hasSent = true;
				
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					Log.v("Ara", "run" +  e.getMessage());
				}
				
				//Dismiss the dialog
				mProgress.dismiss();
				
			}
			
			
		}
		

		
		
		private String buildSOSMessage(){
			StringBuilder message = new StringBuilder();
			
			try{
				message.append("SOS");
				message.append("|");
				message.append(_location.getLatitude());
				message.append("|");
				message.append(_location.getLongitude());
				message.append("|");
				message.append("O+");
				message.append("|");
				message.append("27M");
				message.append("|");
				message.append(_user.getToken());
				message.append("|");
				message.append(getResources().getString(R.string.sos_url));
				message.append("?");
				message.append(getResources().getString(R.string.json_name_token));
				message.append("=");
				message.append(_user.getToken());
				
				
			}catch(Exception ex){
				Log.v("Ara", ex.getMessage());								 
			}
			
			return message.toString();
		}
		
		
		/**
		 * Makes the phone number in the simpler string format
		 * @param phoneNumber
		 * @return
		 */
		private String getValidPhoneNumber(String phoneNumber){

			phoneNumber = phoneNumber.replace("(", "");
			phoneNumber = phoneNumber.replace(")", "");
			phoneNumber = phoneNumber.replace("-", "");
			phoneNumber = phoneNumber.replace(" ", "");
			
			phoneNumber = phoneNumber.trim();
			
			try {
				Integer length = phoneNumber.length();
				
				phoneNumber = phoneNumber.substring(length - 10, length);
			} catch (Exception e) {
				Log.v("Ara", "getValidPhoneNumber: Error while trying to format phone number");
			}
			
			return phoneNumber;
		}
		
		

		
		
		
		
		

			
		
	}
}
