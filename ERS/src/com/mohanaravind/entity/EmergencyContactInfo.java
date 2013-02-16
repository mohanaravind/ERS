/**
 * 
 */
package com.mohanaravind.entity;


import java.util.HashMap;
import java.util.Map.Entry;

import android.app.Activity;
import android.net.Uri;

import com.mohanaravind.dbutility.PreferencesHandler;
import com.mohanaravind.utility.ContactProvider;
import com.mohanaravind.utility.ContactProvider.ContactDetail;

/**
 * @author Aravind
 *
 */
public class EmergencyContactInfo {

	
	public EmergencyContactInfo(){
		this.mContactDetails = new HashMap<String, ContactDetail>();
	}
	
	private HashMap<String, ContactDetail> mContactDetails;
	
	public HashMap<String, ContactDetail> getContactDetails(){
		return mContactDetails;
	}
	

	/**
	 * Saves the data to preferences
	 */
	public void commit(Activity activity){
		PreferencesHandler preferencesHandler = new PreferencesHandler(activity);
		StringBuilder contacts = new StringBuilder();
		String contactList;		
		
		for (Entry<String, ContactDetail> contact : mContactDetails.entrySet()) {
			preferencesHandler.setPreference(contact.getKey() + "DisplayName", contact.getValue().getDisplayName());
			preferencesHandler.setPreference(contact.getKey() + "PhoneNumber", contact.getValue().getPhoneNumber());
			preferencesHandler.setPreference(contact.getKey() + "PhotoUri", contact.getValue().getPhotoUri().toString());
			
			contacts.append(",");
			contacts.append(contact.getKey());			
		}
		
		//Persist the list of contacts which were added as emergency contacts
		contactList = contacts.toString().replaceFirst("," , "");		
		preferencesHandler.setPreference("Contacts", contactList);		
				
	}
	
	/**
	 * Retrieves the data from preferences
	 * @param activity
	 */
	public void retrieve(Activity activity){
		PreferencesHandler preferencesHandler = new PreferencesHandler(activity);
		String contactList = null;
		String[] contacts;
		
		//Get the list of emergency contacts key's
		contactList = preferencesHandler.getPreference("Contacts");
		
		//If there are no contacts prefeernces
		if(contactList == null)
			return;
		
		contacts = contactList.split(",");
		
		mContactDetails = new HashMap<String, ContactDetail>();
		
		for (String contact : contacts) {
			String displayName;
			String phoneNumber;
			Uri photoUri;
			
			//Retrieve the contact information from preferences
			displayName = preferencesHandler.getPreference(contact + "DisplayName");
			phoneNumber = preferencesHandler.getPreference(contact + "PhoneNumber");
			photoUri = Uri.parse(preferencesHandler.getPreference(contact + "PhotoUri"));
			
			ContactProvider contactProvider = ContactProvider.getContactProvider(activity);			
			ContactDetail contactDetail = contactProvider.new ContactDetail(displayName, phoneNumber, photoUri);
			
			//Store 
			mContactDetails.put(contact, contactDetail);
		}
			
		
		
	}
	
}
