package com.tagAR;

import utilties.Utilities;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabLayout extends TabActivity{


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablayout);

		boolean loggedIn=(Boolean)Utilities.getSharedPreferences(this,"loggedIn", 'b');

		TabHost tabHost = getTabHost(); 

		/**
		 * TabLayout will be the main TabLayout where all the action will be.
		 * I will be adding the required activities in the tabs as i work
		 * on the project.
		 * 
		 * This tab activity will provide logged in until logging out feature.
		 * User's data will be stored in the shared preferences, while the shared
		 * preferences will be private.
		 */


		if(loggedIn)
		{//the user is logged in
			Intent intentAboutTagAR = new Intent().setClass(this, About.class);
			TabSpec tabSpecAboutTagAR = tabHost
					.newTabSpec("About tagAR")
					.setIndicator("About tagAR",null)
					.setContent(intentAboutTagAR);

			tabHost.addTab(tabSpecAboutTagAR);
			//set About tagAR tab as default (zero based)
			tabHost.setCurrentTab(0);
		}
		else
		{//the user is not logged in


			Intent intentLogin = new Intent().setClass(this, Login.class);
			TabSpec tabSpecLogin = tabHost
					.newTabSpec("Login")
					.setIndicator("Login",null)
					.setContent(intentLogin);


			Intent intentRegistration = new Intent().setClass(this, Registration.class);
			TabSpec tabSpecRegistration = tabHost
					.newTabSpec("Registration")
					.setIndicator("Registration",null)
					.setContent(intentRegistration);


			Intent intentResetPassword = new Intent().setClass(this, ResetPassword.class);
			TabSpec tabSpecResetPassword = tabHost
					.newTabSpec("Reset Password")
					.setIndicator("Reset Password",null)
					.setContent(intentResetPassword);

			Intent intentAboutTagAR = new Intent().setClass(this, About.class);
			TabSpec tabSpecAboutTagAR = tabHost
					.newTabSpec("About tagAR")
					.setIndicator("About tagAR",null)
					.setContent(intentAboutTagAR);



			// add all tabs 
			tabHost.addTab(tabSpecLogin);
			tabHost.addTab(tabSpecRegistration);
			tabHost.addTab(tabSpecResetPassword);
			tabHost.addTab(tabSpecAboutTagAR);



			//set Login tab as default (zero based)
			tabHost.setCurrentTab(0);
		}//end if

	}


	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {


		switch (item.getItemId()) {
		case R.id.logOut:
			break;
		case R.id.tutorial:
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
