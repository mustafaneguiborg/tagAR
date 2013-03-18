/**
 *  
 *tagAR is a tagging application developed for the Android Platform
 *Copyright (C) 2012  Mustafa Neguib, MN Tech Solutions
 *  
 *This file is part of tagAR.
 *
 *tagAR is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *the Free Software Foundation, either version 3 of the License, or
 *(at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 *You can contact the developer/company at the following:
 *
 *Phone: 00923224138957
 *Website: www.mntechsolutions.net
 *Email: support@mntechsolutions.net , mustafaneguib@mntechsolutions.net
 *
 *
 */

/*
 * @author: Mustafa Neguib
 * @company: MN Tech Solutions
 * @applicationName: tagAR
 * @appType: This app is an augmented reality app which allows the user to tag locations
 * @version: 3.0 
 * @description: This activity is a simple About page which gives some information to the user about the app and the company.
 * 
 */

package com.tagAR;




import java.util.ArrayList;

import com.tagAR.R;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class About extends ListActivity{

	
    private  ArrayList<String>userData;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        //setContentView(R.layout.about);
        
        /**
         * 
         * source for setting up a basic list view
         * http://www.mkyong.com/android/android-listview-example/
         * 
         */
        
        ArrayList<String> listItems=new ArrayList<String>();
        listItems.add(this.getString(R.string.description));
        listItems.add(this.getString(R.string.company));
        listItems.add(this.getString(R.string.designerDeveloper));
        listItems.add(this.getString(R.string.license));
        listItems.add(this.getString(R.string.website));
        listItems.add(this.getString(R.string.website1));
        listItems.add(this.getString(R.string.appVersion));
        listItems.add(this.getString(R.string.copyright1));
        listItems.add(this.getString(R.string.copyright2));

        ArrayAdapter<String> adapter;
        
        adapter=new ArrayAdapter<String>(this,R.layout.about,listItems);
        setListAdapter(adapter);

        ListView listView = getListView();
		listView.setTextFilterEnabled(true);
 
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    // When clicked, show a toast with the TextView text
			    //Toast.makeText(getApplicationContext(),((TextView) view).getText(), Toast.LENGTH_SHORT).show();
				
				/**
				 * 
				 * source for opening a web link using intent
				 * http://stackoverflow.com/questions/3004515/android-sending-an-intent-to-browser-to-open-specific-url
				 * 
				 */
				
			    if(4==id)
			    {
			    	
			    	Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://tagar.mntechsolutions.net"));
			    	startActivity(intent);
			    	
			    }//end if
			    else if(5==id)
			    {
			    	Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://github.com/mustafaneguib/tagAR"));
			    	startActivity(intent);
			    	
			    }//end else if
			    else
			    {
			    	
			    }//end else
			    	
			}
		});
        
    }

	
	
}