/**
 * 
 */
package com.mohanaravind.ui;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mohanaravind.main.R;


/**
 * @author Aravind
 *
 */
public class AuthenticationSection {
	LinearLayout mInformationLayout;
	Activity mActivity;
	OnClickListener mOnClickListener;
    
	/**
	 * UI Elements
	 */
	TextView mToken;
	Button mGenerate;
	
	/**
	 * Composite reference of UI elements
	 */
	public AuthenticationDisplay mAuthenticationSectionDisplay;
        
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
     * Gets the autentication display
     * @return
     */
    public AuthenticationDisplay getAuthenticationDisplay(){
    	return mAuthenticationSectionDisplay;
    }
	
    /**
     * Constructor
     * @param activity
     * @param onClickListener
     */
	public AuthenticationSection(Activity activity, OnClickListener onClickListener){		
		
		this.mInformationLayout = new LinearLayout(activity);
		
		this.mInformationLayout.setOrientation(LinearLayout.VERTICAL);
		
		this.mActivity = activity;
		this.mOnClickListener = onClickListener;
		
		constructTitleBar("Token Generation");		
		
		construct();
	}
	

	
	
	


	
	/**
	 * Constructs the critial information interface
	 */
	private void construct(){
		LinearLayout.LayoutParams lvMessageParams;
		LinearLayout linearLayout = new LinearLayout(mActivity);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		//mInformationLayout.setPadding(10,10,10,10);
		

		

				
		//mInformationLayout.setGravity(Gravity.CENTER_VERTICAL);

		//Create the layout parameters
		lvMessageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 
				LinearLayout.LayoutParams.MATCH_PARENT);
		//lvMessageParams.setMargins(0,250,0,4);

		//Set the layout params
		linearLayout.setLayoutParams(lvMessageParams);
						 

		//Create the message's textview
		mToken = new TextView(mActivity);
		mToken.setMaxEms(100);
		mToken.setPadding(5, 5, 5, 5);
		mToken.setTextColor(Color.BLACK);
		mToken.setTextSize(26);
		mToken.setGravity(Gravity.CENTER);
		mToken.setBackgroundResource(R.drawable.backgroundtransparency);
				int maxLength = 50;
		InputFilter[] FilterArray = new InputFilter[1];
		FilterArray[0] = new InputFilter.LengthFilter(maxLength);
		mToken.setFilters(FilterArray);
		
		//Set the text
		mToken.setText("Generate your token");
												
		//Add the token textview to the layout
		linearLayout.addView(mToken);		
		
		
	    /*   <Button
        android:id="@+id/button1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="7dp"
        android:gravity="center_vertical|center_horizontal|fill"
        android:onClick="btnContinue_clicked"
        android:paddingBottom="10dp"
        android:paddingLeft="30dp"
        android:paddingRight="30dp"
        android:paddingTop="10dp"
        android:text="@string/button_continue"
        android:typeface="serif" />*/
		
		LinearLayout.LayoutParams buttonlayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT);
	    buttonlayoutParams.setMargins(0,50,0,4);
		
	    //RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	
	    //relParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	    
	    
		//Start rendering the button
		mGenerate = new Button(mActivity);
		mGenerate.setText("Generate");
		mGenerate.setTypeface(Typeface.SERIF);
		mGenerate.setLayoutParams(buttonlayoutParams);
		
		//mGenerate.setPadding(0, 0, 0, 0);
		

	
		//Wire up the listener
		mGenerate.setOnClickListener(mOnClickListener);		
		
		//Add the button
		linearLayout.addView(mGenerate);
		linearLayout.setGravity(Gravity.CENTER);
		
		
		mInformationLayout.addView(linearLayout);
		
		//Set the display UI elements
		mAuthenticationSectionDisplay = new AuthenticationDisplay(mToken, mGenerate);
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
		
		mInformationLayout.setBackgroundResource(R.drawable.frame_background);
		mInformationLayout.addView(tvTitle);
		
	}
	
	public class AuthenticationDisplay{
		private TextView mTokenView;
		private Button mGenerateButton;		
	
		public TextView getTokenView(){
			return this.mTokenView;
		}
		
		public Button getGenerateButton(){
			return this.mGenerateButton;
		}
		
		public AuthenticationDisplay(TextView tokenView, Button generateButton){
			this.mTokenView = tokenView;
			this.mGenerateButton = generateButton;
		}
	}
}
