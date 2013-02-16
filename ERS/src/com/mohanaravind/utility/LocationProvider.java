/**
 * 
 */
package com.mohanaravind.utility;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


/**
 * Use this utility to retrieve latitude / longitude of the device. 
 * With/without the availability of GPS
 * Note: ACCESS_COARSE_LOCATION & ACCESS_FINE_LOCATION permissions has to be set in manifest
 * @author Aravind
 *
 */
public class LocationProvider {

	private Activity activity;
	private LocationProviderListener locationProviderListener;
	
	/**
	 * Constructor
	 */
	private LocationProvider(Activity activity) throws Exception{
		
		try {
			//Set the activity 
			this.activity = activity;
			
			//Create the location manager
			LocationManager locationManager = createLocationManager();

			//Create the location listener
			locationProviderListener = new LocationProviderListener();
			LocationListener locationListener = locationProviderListener;
					
			// Register the listener with the Location Manager to receive location updates
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		} catch (Exception e) {
			//TODO: Do something sensible !!!
			//Throw back the exception
			throw e;
		}
	}
	
	/**
	 * Creates the location manager for this current activity
	 * @return
	 */
	private LocationManager createLocationManager() {		
		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		return locationManager;
	}
		
	/**
	 * Event handler class
	 * @author Aravind
	 *
	 */
	private class LocationProviderListener implements LocationListener{

		private Location location;
		
		@Override
		public void onLocationChanged(Location location) {
			this.location = location;			
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

		/**
		 * @return the location
		 */
		protected Location getLocation() {
			return location;
		}

		
	}
		
	/**
	 * Creates an instance of location provider and returns it
	 * @return
	 * @throws Exception 
	 */
	public static LocationProvider getLocationProvider(Activity activity) throws Exception{
		return new LocationProvider(activity);
	}
	
	/**
	 * This method returns the last available location of the user
	 * @return Location
	 * @author Aravind
	 */
	public Location getLocation(){		
		return locationProviderListener.getLocation();
	}

}
