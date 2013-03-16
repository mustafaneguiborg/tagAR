package com.tagAR;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class PreLoginTabs extends Activity{
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prelogintab);

		/*Resources ressources = getResources(); 
		TabHost tabHost = (TabHost)findViewById(R.id.preLoginTab); 
		
		// Android tab
		Intent intentScreen3 = new Intent().setClass(this, Login.class);
		TabSpec tabSpecScreen3 = tabHost
			.newTabSpec("Login")
			.setIndicator("Login",null)
			.setContent(intentScreen3);

		// Apple tab
		Intent intentScreen4 = new Intent().setClass(this, Registration.class);
		TabSpec tabSpecScreen4 = tabHost
			.newTabSpec("Registration")
			.setIndicator("Registration",null)
			.setContent(intentScreen4);
		
				
				
		// add all tabs 
		tabHost.addTab(tabSpecScreen3);
		tabHost.addTab(tabSpecScreen4);
		
		
		//set Windows tab as default (zero based)
		tabHost.setCurrentTab(0);
		*/
	}

}
