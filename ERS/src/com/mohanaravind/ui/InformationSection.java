/**
 * 
 */
package com.mohanaravind.ui;


import android.app.Activity;
import android.graphics.Color;
import android.text.InputFilter;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mohanaravind.entity.EmergencyContactInfo;
import com.mohanaravind.main.R;

/**
 * @author Aravind
 *
 */
public class InformationSection {

	LinearLayout mInformationLayout;
	Activity mActivity;
	OnClickListener mOnClickListener;
    
	/**
	 * UI Elements
	 */
	EditText mInformationText;
	Button mSaveButton;
	
        
    public final static String FIRST_CONTACT = "1";
    public final static String SECOND_CONTACT = "2";
    
    /**
     * Defines the contact image height and width
     */
    final Integer CONTACT_HEIGHT = 150;
    final Integer CONTACT_WIDTH = 150;
    final Integer CONTACT_BORDER = 2;
	
    
    public LinearLayout getView(){
    	return mInformationLayout;
    }
    
	
    /**
     * Constructor
     * @param activity
     * @param onClickListener
     */
	public InformationSection(Activity activity, OnClickListener onClickListener){		
		
		this.mInformationLayout = new LinearLayout(activity);
		
		this.mInformationLayout.setOrientation(LinearLayout.VERTICAL);
		
		this.mActivity = activity;
		this.mOnClickListener = onClickListener;
		
		//Construct the title bar
		constructTitleBar("Personal Information");
		
		//construct();
	}
	

	
	
	


	
	/**
	 * Constructs the critial information interface
	 */
	private void construct(){
		LinearLayout.LayoutParams lvMessageParams;
		
		
		//Start rendering the button
		mSaveButton = new Button(mActivity);
		mSaveButton.setText("Save");
		
		//Add the button
		mInformationLayout.addView(mSaveButton);
		
				
		//mInformationLayout.setGravity(Gravity.CENTER_VERTICAL);

		//Create the layout parameters
		lvMessageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lvMessageParams.setMargins(0,0,0,4);

		//Set the layout params
		mInformationLayout.setLayoutParams(lvMessageParams);
				
		//Wire up the listener
		mSaveButton.setOnClickListener(mOnClickListener);						 

		//Create the message's textview
		mInformationText = new EditText(mActivity);
		mInformationText.setMaxEms(100);
		mInformationText.setPadding(5, 5, 5, 5);
		mInformationText.setTextColor(Color.GRAY);
		mInformationText.setBackgroundResource(R.drawable.backgroundtransparency);
				int maxLength = 50;
		InputFilter[] FilterArray = new InputFilter[1];
		FilterArray[0] = new InputFilter.LengthFilter(maxLength);
		mInformationText.setFilters(FilterArray);
		
		//Set the text
		mInformationText.setText("Type your critical information here");
												
		//Add the button and information text

		mInformationLayout.addView(mInformationText);		
	}

	/**
	 * Constructs the title bar
	 */
	private void constructTitleBar(String title){
		TextView tvTitle = new TextView(mActivity);
		tvTitle.setText(title);
		tvTitle.setTextAppearance(mActivity,R.style.TitleBar);

		
		LinearLayout.LayoutParams titleBarLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 
		LinearLayout.LayoutParams.WRAP_CONTENT);
		titleBarLayoutParams.setMargins(18,25,0,25);
		
		tvTitle.setLayoutParams(titleBarLayoutParams);
		
		mInformationLayout.addView(tvTitle);
		mInformationLayout.setBackgroundResource(R.drawable.frame_background);
		
	}

}
