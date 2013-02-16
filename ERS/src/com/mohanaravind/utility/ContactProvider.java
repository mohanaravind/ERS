/**
 * 
 */
package com.mohanaravind.utility;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * @author Aravind
 *
 */
public class ContactProvider {

	private Activity mActivity;
	
	private ContactProvider(Activity activity){
		this.mActivity = activity;
	}
	
	public static ContactProvider getContactProvider(Activity activity){
		return new ContactProvider(activity);
	}
	
	
	
		
	
	
	/**
	 * Call this method inside  onActivityResult(int requestCode, int resultCode, Intent data) method
	 * @param uri
	 */
	public ContactDetail getContact(Intent data) {
		//Declarations
		ContactDetail contactDetail = null;
		String displayName = null;
		String phoneNumber = null;
		Uri photoUri = null;
		Cursor contactData = null;
		
		try {
			Uri uri = data.getData();
			String id = uri.getLastPathSegment(); 
			
			//Query for the contact
			contactData = mActivity.getContentResolver().query(
			              ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
			              null, 
			              ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
			              new String[]{id}, null);
			
			//Open the first record in the cursor
			if (contactData.moveToFirst())
			{
				//Retrieve the data
				displayName = contactData.getString(contactData.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				phoneNumber = contactData.getString(contactData.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));		  
				photoUri = getPhotoUri(Long.parseLong(id));
			}
						
			//Initiaalize contact with data
			contactDetail = new ContactDetail(displayName, phoneNumber, photoUri);
		} catch (NumberFormatException e) {			
			Log.v("Ara", e.getMessage());
		}
	    finally{
	    	if(contactData != null)
	    		contactData.close();
	    }
		
		return contactDetail;
	}
	
	/**
	 * Gets the uri of the contact photo using contact id
	 * @param contactId
	 * @return
	 */
	private Uri getPhotoUri(long contactId) {
	    ContentResolver contentResolver = mActivity.getContentResolver();
	    Cursor cursor = null;
	    
	    try {
	        cursor = contentResolver
	            .query(ContactsContract.Data.CONTENT_URI,
	                null,
	                ContactsContract.Data.CONTACT_ID
	                    + "="
	                    + contactId
	                    + " AND "

	                    + ContactsContract.Data.MIMETYPE
	                    + "='"
	                    + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
	                    + "'", null, null);

	        if (cursor != null) {
	        if (!cursor.moveToFirst()) {
	            return null; // no photo
	        }
	        } else {
	        	return null; // error in cursor process
	        }

	    } catch (Exception e) {
	    	Log.w("Ara", e.getMessage());
	        return null;
	    }
	    finally{
	    	if(cursor != null)
	    		cursor.close();
	    }

	    Uri person = ContentUris.withAppendedId(
	        ContactsContract.Contacts.CONTENT_URI, contactId);
	    return Uri.withAppendedPath(person,
	        ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
	  }
	
	/**
	 * The class which describes the contact details 
	 * @author Aravind
	 *
	 */
	public class ContactDetail{
		
		private String mDisplayName;
		private String mPhoneNumber;
		private Uri mPhotoUri;
		
		/**
		 * Constructor
		 * @param displayName
		 * @param phoneNumber
		 * @param photoUri
		 */
		public ContactDetail(String displayName, String phoneNumber, Uri photoUri){
			this.mDisplayName = displayName;
			this.mPhoneNumber = phoneNumber;
			this.mPhotoUri = photoUri;
		}
		
		/**
		 * Getter Properties
		 */
		public String getDisplayName(){ return this.mDisplayName; };
		public String getPhoneNumber(){ return this.mPhoneNumber; };
		public Uri getPhotoUri(){ return this.mPhotoUri; };
		
		
	}
	
		

	
	
}
