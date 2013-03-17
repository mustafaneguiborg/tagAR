package com.tagAR;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class PreLoginTabs extends TabActivity{


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prelogintab);


		TabHost tabHost = getTabHost(); 


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


		// add all tabs 
		tabHost.addTab(tabSpecLogin);
		tabHost.addTab(tabSpecRegistration);
		tabHost.addTab(tabSpecResetPassword);


		//set Windows tab as default (zero based)
		tabHost.setCurrentTab(0);

	}

}
