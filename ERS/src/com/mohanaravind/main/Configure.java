package com.mohanaravind.main;

import com.mohanaravind.dbutility.DataStore;
import com.mohanaravind.entity.EmergencyContactInfo;
import com.mohanaravind.entity.User.Status;
import com.mohanaravind.ui.ContactsSection;
import com.mohanaravind.ui.HistorySection;
import com.mohanaravind.ui.InformationSection;
import com.mohanaravind.utility.ContactProvider;
import com.mohanaravind.utility.ContactProvider.ContactDetail;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class Configure extends FragmentActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
				
		super.onCreate(savedInstanceState);
		
		//If the user is an unregistered user
		if(DataStore.getUser(this).getStatus() == Status.Unregistered)
			displayWelcomeScreen();
		else		
			displayConfigurationScreen();
	}

	/**
	 * Displays the welcome screen for unregistered users
	 */
	private void displayWelcomeScreen(){
		
		Intent intent = new Intent();
		intent.setClassName(getApplicationContext(), Welcome.class.getName());
		
		//Start the welcome screen
		startActivity(intent);
		
		this.finish();
	}
	
	
	/**
	 * Displays the configuration layout for the user
	 */
	private void displayConfigurationScreen() {
		setContentView(R.layout.activity_configure);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_configure, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment =   new SectionFragment();  
			Bundle args = new Bundle();
			args.putInt(SectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			
			
			return fragment;
			
		}

		
		/**
		 * Returns the number of required sections/tabs
		 */		
		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		
		/**
		 * Sets the title of each section/tab
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase();
			case 1:
				return getString(R.string.title_section2).toUpperCase();
			case 2:
				return getString(R.string.title_section3).toUpperCase();
			}
			return null;
		}
	

		

	
	}

	

	/**
	 * Tab/Section builder class
	 * @author Aravind
	 *
	 */
	public static class SectionFragment extends Fragment {
		
		private final int CONTACTS = 1;
		private final int INFORMATION = 2;
		private final int HISTORY = 3;
		
		
		
		
		
		public static final String ARG_SECTION_NUMBER = "section_number";

		public SectionFragment() {
		}

				
				
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			//Declarations				
			View view = null;

			try {
				//Get tab which is getting constructed
				int tabIndex = getArguments().getInt(ARG_SECTION_NUMBER);
				
				//Get the respective tab view
				switch (tabIndex) {
					case CONTACTS:
						mContactsSection = new ContactsSection(getActivity(), new ContactListener());
						view = mContactsSection.getView();
						displayData();
						break;
					case INFORMATION:		
						mInformationSection = new InformationSection(getActivity(), new ContactListener());
						view = mInformationSection.getView();				
						break;
					case HISTORY:								
						mHistorySection = new HistorySection(getActivity(), new ContactListener());
						view = mHistorySection.getView();
						break;			
					default:					
						view = null;
						break;
				}
			} catch (Exception e) {
				Log.d("Ara", e.getMessage());
			}
			

			
			return view;
		}
		
		
		private static final int CONTACT_PICKER_RESULT = 1001;
		
		private ContactsSection mContactsSection;
		private InformationSection mInformationSection;
		private HistorySection mHistorySection;
		
		//private EmergencyContactInfo emergencyContactInfo;
		
		private String mContactTag;
		
		/**
		 * Gets triggered after user selects the emergency contact
		 */
		@Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (resultCode == RESULT_OK) {
	            switch (requestCode) {
	            case CONTACT_PICKER_RESULT:
	                // handle contact results
	                	            	
	            	EmergencyContactInfo emergencyContactInfo = persistEmergencyContactInfo(data);
	            	mContactsSection.displayData(emergencyContactInfo);
	            	
	            	
	            	break;
	            }
	        } else {
	            // gracefully handle failure
	            Log.w("Ara", "Warning: activity result not ok");
	        }
	    }


		/**
		 * Displays the persisted/user data onto the UI
		 */
		private void displayData(){
			//Get the emergency contact info
			EmergencyContactInfo emergencyContactInfo = DataStore.getEmergencyContactInfo(getActivity());
								
			if(emergencyContactInfo != null)
				mContactsSection.displayData(emergencyContactInfo);
			
		}
		
		//Persist emergency contact data
		private EmergencyContactInfo persistEmergencyContactInfo(Intent data){
	        //Declarations
			EmergencyContactInfo emergencyContactInfo;
			
			//Get the contact details
        	ContactProvider contactProvider = ContactProvider.getContactProvider(getActivity());
        	ContactDetail contactDetail = contactProvider.getContact(data);			      	        	        	
        	
        	//Check its availability in data store
        	if(DataStore.getEmergencyContactInfo(getActivity()) == null)
        		emergencyContactInfo = new EmergencyContactInfo();	            	
        	else
        		emergencyContactInfo = DataStore.getEmergencyContactInfo(getActivity());
        	
        	emergencyContactInfo.getContactDetails().put(mContactTag, contactDetail);	            	
        	DataStore.setEmergencyContactInfo(emergencyContactInfo);
        	DataStore.commitEmergencyContactInfo(getActivity());
        	
        	
        	return emergencyContactInfo;
		}


		

		/**
		 * Listens for click events on contact
		 * @author Aravind
		 *
		 */
		private class ContactListener implements View.OnClickListener {

			@Override
			public void onClick(View view) {
				doLaunchContactPicker(view);
				
			}
			
			public void doLaunchContactPicker(View view) {
			    Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
			            Contacts.CONTENT_URI);
			    			    
			    //Set the contact tag
			    mContactTag = view.getTag().toString();
			    			    
			    startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
			}
			

			
		}
		
		
	}

	
}
