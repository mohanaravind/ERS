/**
 * 
 */
package com.mohanaravind.ui;

import java.util.HashMap;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mohanaravind.entity.EmergencyContactInfo;
import com.mohanaravind.main.R;
import com.mohanaravind.utility.ContactProvider;

/**
 * @author Aravind
 *
 */
public class HistorySection {
	LinearLayout mLinearLayout;
	Activity mActivity;
	OnClickListener mOnClickListener;
    
    HashMap<String, ContactDisplay> mContactDisplays;
    
    public final static String FIRST_CONTACT = "1";
    public final static String SECOND_CONTACT = "2";
    
    /**
     * Defines the contact image height and width
     */
    final Integer CONTACT_HEIGHT = 150;
    final Integer CONTACT_WIDTH = 150;
    final Integer CONTACT_BORDER = 2;
	
    
    public LinearLayout getView(){
    	return mLinearLayout;
    }
    
		
	public HistorySection(Activity activity, OnClickListener onClickListener){		
		
		this.mLinearLayout = new LinearLayout(activity);
		
		this.mLinearLayout.setOrientation(LinearLayout.VERTICAL);
		
		this.mActivity = activity;
		this.mOnClickListener = onClickListener;
		
		this.mContactDisplays = new HashMap<String, HistorySection.ContactDisplay>();
		
		construct();
	}
	
	/**
	 * Displays the emergency contact information details to the ui
	 * @param emergencyContactInfo
	 */
	public void displayData(EmergencyContactInfo emergencyContactInfo){
		try {
			String contact;
						
			contact = FIRST_CONTACT;			
			displayContactInfo(mContactDisplays.get(contact), emergencyContactInfo.getContactDetails().get(contact));
			
			contact = SECOND_CONTACT;	
			displayContactInfo(mContactDisplays.get(contact), emergencyContactInfo.getContactDetails().get(contact));
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	
	/**
	 * Displays the Contact information to UI
	 * @param textView
	 * @param imageView
	 * @param contactDetail
	 */
	private void displayContactInfo(ContactDisplay contactDisplay, ContactProvider.ContactDetail contactDetail){
		//If emergency contact detail 1 has been set
		if(contactDetail != null){
			StringBuilder displayText = new StringBuilder();
			
			
			contactDisplay.imageView.setImageURI(contactDetail.getPhotoUri());
			
			//Set the layout parameters
			LayoutParams params = new LinearLayout.LayoutParams(CONTACT_HEIGHT, CONTACT_WIDTH);			
			contactDisplay.imageView.setLayoutParams(params);			
			contactDisplay.imageView.setBackgroundColor(Color.GRAY);
			contactDisplay.imageView.setPadding(CONTACT_BORDER, CONTACT_BORDER, CONTACT_BORDER, CONTACT_BORDER);
					
			
			//Construct the display text
			displayText.append("   ");
			displayText.append(contactDetail.getDisplayName());
			displayText.append("\n");
			displayText.append("   ");
			displayText.append(contactDetail.getPhoneNumber());
			
			contactDisplay.textView.setText(displayText);			
		}
	}
	
	
	//@Override
	private void construct(){
		String contact;
						
		//Create the first emergency contact
		contact = FIRST_CONTACT;
		mContactDisplays.put(contact, addEmergencyContact(" Emergency contact", contact));
	
		//Create the second emergency contact
		contact = SECOND_CONTACT;
		mContactDisplays.put(contact, addEmergencyContact(" Emergency contact", contact));

	}


	/**
	 * Adds the emergency contact to the display
	 * 
	 */
	private ContactDisplay addEmergencyContact(String message, String tag) {
		ContactDisplay contactDisplay;
		TextView tvMessage;
		ImageView imgContact;
		LinearLayout lvMessage;
		LinearLayout.LayoutParams lvMessageParams;
		//Create the message in the content
		lvMessage = new LinearLayout(mActivity);


		lvMessage.setBackgroundResource(R.drawable.backgroundtransparency);
		lvMessage.setPadding(5,5,5,5);
		
		lvMessage.setGravity(Gravity.CENTER_VERTICAL);

		//Create the layout parameters
		lvMessageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lvMessageParams.setMargins(0,0,0,4);

		//Set the layout params
		lvMessage.setLayoutParams(lvMessageParams);
				
		//Create the contact image
		imgContact = new ImageView(mActivity);
		imgContact.setImageResource(R.drawable.addcontact);
		imgContact.setPadding(6, 6, 6, 6);
		imgContact.setTag(tag);
		
		//Set the layout parameters
		LayoutParams params = new LinearLayout.LayoutParams(CONTACT_HEIGHT, CONTACT_WIDTH);			
		imgContact.setLayoutParams(params);
		imgContact.setBackgroundColor(Color.GRAY);
		imgContact.setPadding(CONTACT_BORDER, CONTACT_BORDER, CONTACT_BORDER, CONTACT_BORDER);
		
		
		// Set up the user interaction to manually show or hide the system UI.
		imgContact.setOnClickListener(mOnClickListener);
		
		lvMessage.addView(imgContact);
				 

		//Create the message's textview
		tvMessage = new TextView(mActivity);
		tvMessage.setMaxEms(100);
		tvMessage.setPadding(5, 0, 0, 0);
		tvMessage.setTextColor(Color.GRAY);

		//Set the text
		tvMessage.setText(message);
		lvMessage.addView(tvMessage);
						
		
		//Add the message view
		mLinearLayout.addView(lvMessage);
		
		contactDisplay = new ContactDisplay(tvMessage, imgContact);
		
		return contactDisplay;
	}
	
	
	private class ContactDisplay{
		public TextView textView;
		public ImageView imageView;		
		
		public ContactDisplay(TextView textView, ImageView imageView){
			this.textView = textView;
			this.imageView = imageView;
		}
	}
}
