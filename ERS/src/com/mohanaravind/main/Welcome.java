package com.mohanaravind.main;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.content.Intent;

public class Welcome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Sets the application to full screen mode
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



		
		setContentView(R.layout.activity_welcome);
		

		
		

	}
	
	// Gets triggered when continue button is clicked
	public void btnContinue_clicked(View view) {
		
		Intent intent = new Intent();
		intent.setClassName(getApplicationContext(), Verify.class.getName());
		
		//Start the welcome screen
		startActivity(intent);
		
		this.finish();
	}

}
