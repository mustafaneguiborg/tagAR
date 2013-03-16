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
 * @description: This activity shows the users if any email addresses are in the local storage of the phone. This feature is a new
 * addition in version 1.0 and is meant to provide ease of use when logging into the app. If no details exist then the user is directly 
 * taken to the login form, else the user can select form the list of emails saved, and the data is automatically added to the login form.
 */

package com.tagAR;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.tagAR.R;

import data.UserAccounts;
import facebookConnector.FacebookConnector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class LoginAccountsList extends Activity implements OnClickListener, OnLongClickListener{
    
	private ArrayList<String> list;
	private AdapterContextMenuInfo lastMenuInfo = null;
	private TextView addUserAccounts;
	/**
	 * Addition in Version 3.0
	 * I have added new login via facebook button
	 * Now users will be able to login via their facebook accounts
	 * and be able to use new features just for the facebook users.
	 * 
	 */
	
	private String facebookAPIKey;
	private FacebookConnector facebook=new FacebookConnector();
    private AsyncFacebookRunner mAsyncRunner;
	String facebookAccessToken;
	private ImageView facebookLogin;
	private Context context;
	
	@SuppressWarnings("unchecked")
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
              
        
        //context=getApplicationContext();
        /**
         * Changed in Version 3.0
         * I have changed the statement context=getApplicationContext(); to 
         * context=this;
         * There is a difference between the this and getApplicationContext.
         * The this keyword is used for the current activity while the getApplicationContext
         * is used for the whole application.
         * source: http://stackoverflow.com/questions/10641144/difference-between-getcontext-getapplicationcontext-getbasecontext-g
         * Understanding this means that Facebook requires the context of the current Activity where the function
         * authorize is being called. 
         */
        
        context=this;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.loginaccountslist);
        
        addUserAccounts=(TextView)findViewById(R.id.addUserAccounts);
        addUserAccounts.setOnClickListener(this);
        
        facebookLogin=(ImageView)findViewById(R.id.facebookLogin);
        facebookLogin.setOnClickListener(this);
        
        try
		{
        	
        	/**
        	 *Addition in version 1.0
        	 *check if the internal file exists. this file is to contain the list of user accounts that have
        	 *been signed in to the app. I am keeping a list so that that i can show it to the user so that his
        	 *sign in (login) process is as smooth and easy as possible. Once the user enters the login details
        	 *for the first time, the data will be saved in an internal file in the form of objects.
        	 * 
        	 */
		
			
        	int lengthBuffer=0;
        	
        	openFileInput("tagARUserAccountsLength");//i am doing this to check if the file exists or not. if an exception is thrown at this line then i will know that the file does not exist, else it will exist
        	
        	lengthBuffer=getLengthOfDataInBytes("tagARUserAccountsLength");
			
			byte [] buffer1=new byte[lengthBuffer];

			FileInputStream fis;

			fis =openFileInput( "tagARUserAccounts");

			fis.read(buffer1);

			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer1)); 
			Object object = in.readObject(); 
			in.close(); 

			list=new ArrayList<String>();

			list=(ArrayList<String>)object;
			
			byte [] decodedBytes;
            String decodedString;
		
			int i=0;
			LinearLayout l1=(LinearLayout)findViewById(R.id.LinearLayout02);
			TextView tv;
			LayoutParams p = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	        int j=0;
			while(i<list.size())
			{
			
				tv=new TextView(this);
				decodedBytes=Base64.decode(list.get(i).toString(),Base64.DEFAULT);
	            decodedString=new String(decodedBytes);
				//Log.d("app email",decodedString);
				
				tv.setText((j+1)+". "+decodedString);
				tv.setId(i);
				tv.setLayoutParams(p);
				tv.setTextSize(20);
				tv.setOnClickListener(this);
				tv.setOnLongClickListener(this);
				l1.addView(tv);
				
				
				decodedBytes=Base64.decode(list.get(i+1).toString(),Base64.DEFAULT);
	            decodedString=new String(decodedBytes);
				//Log.d("app password",decodedString);
				i=i+2;
				j++;
				
				tv=null;
				
			}//end while
			
			
			
			
			
			
		}//end try
		catch(Exception e)
		{//the file does not exist.
			
			//e.printStackTrace();
			Intent intent=new Intent("com.tagAR.Login");
            intent.putExtra("emptyUserAccountsList",true);
            startActivity(intent);
            finish();
			

		}//end catch	
        
       
    }
	
	public int getLengthOfDataInBytes(String fileName)
	{
		/**
		 * Addition in version 1.0
		 * this function gets the integer of the value stored in the file. this particular function
		 * is being used to get the value of length in bytes of the data stored in some other file.
		 * 
		 */
		
		try {
		StringBuffer buffer= new StringBuffer();
		FileInputStream fis = this.openFileInput(fileName);
    	InputStreamReader isr = new InputStreamReader(fis);
    	Reader in = new BufferedReader(isr);
    	
    	int ch;
    	
    	while ((ch = in.read()) > -1) {
    		buffer.append((char)ch);
    	}
    	
			in.close();
			
			return Integer.parseInt(buffer.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

		
	}

	
	public void onClick(View view) {

		
		Integer id=view.getId();
		
		if(R.id.addUserAccounts==id)
		{
			Intent intent=new Intent("com.tagAR.Login");
		    intent.putExtra("emptyUserAccountsList",false);
            intent.putStringArrayListExtra("userAccounts",list);
	        intent.putExtra("email","");
	        intent.putExtra("password","");
			startActivity(intent);
	        finish();
			
			
		}//end if
		else if(R.id.facebookLogin==id)
		{
			/**
			 * Addition in Version 3.0
			 * This condition activates the
			 * Facebook authentication code.
			 * I am checking if the Internet connection is on.
			 */
			
			   
	        ConnectivityManager connectivityManager =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo networkStatus= connectivityManager.getActiveNetworkInfo();
	        ArrayList<String> userData=new ArrayList<String>();
	        
	        if(networkStatus!=null)
	        {
	        	facebook.login(context);
	        	
				
	        }//end if
	        else
	        {
	        	

	        	Toast.makeText(this, "No Internet connection detected. Please enable your internet connection.", 1000).show();
	       	 	startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 0);//open up the systems screen which allows me to set the internet settings
	        
	        	
	        }//end else
			
			
		}//end else if
		else
		{
		
			//Toast.makeText(this, id.toString(), 1000).show();
			
			byte [] decodedBytes=Base64.decode(list.get(id).toString(),Base64.DEFAULT);
	        String decodedString=new String(decodedBytes);
	        String email=decodedString;
	        decodedBytes=Base64.decode(list.get(id+1).toString(),Base64.DEFAULT);
	        decodedString=new String(decodedBytes);
	        String password=decodedString;
	        
			Intent intent=new Intent("com.tagAR.Login");
		    intent.putExtra("emptyUserAccountsList",false);
            intent.putStringArrayListExtra("userAccounts",list);
	        intent.putExtra("email",email);
	        intent.putExtra("password", password);
	        
	        /**
	         * Addition in Version 3.0
	         * I am aving a flag which will tell whether the 
	         * user has logged in via facebook or not. 
	         *
	         */
	        
	        intent.putExtra("facebookLogin",false);
			startActivity(intent);
	        finish();
        
		}//end else
          
	}

	
	public boolean onLongClick(View view) {
		
		int id=view.getId();
	
		Integer size=list.size();
		
		Log.d("app size",size.toString());

		
		/**
		 * I am executing the same instruction twice, because when i 
		 * remove one element, then the remaining elements are moved back up one place 
		 * 
		 */
		list.remove(id);//remove the email
		list.remove(id);//remove the password
		
		    				    			
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
		byte[] buffer;
		ObjectOutput out1;
		FileOutputStream fos;
		try {
			fos = openFileOutput("tagARUserAccounts",Context.MODE_PRIVATE);

			out1 = new ObjectOutputStream(bos);
			out1.writeObject(list); 
			out1.close(); 

			buffer  = bos.toByteArray(); 
			
			fos.write(buffer);	


			int lengthBuffer=buffer.length;
			Log.v("buffer length", ((Integer)lengthBuffer).toString());
			fos.close();

			fos = openFileOutput("tagARUserAccountsLength",Context.MODE_PRIVATE);
			String val=((Integer)lengthBuffer).toString();
			fos.write(val.getBytes());
			fos.close();

			fos.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		Intent intent=new Intent("com.tagAR.LoginAccountsList");
	    startActivity(intent);
        finish();
		
		
		return false;
	}

	
	/**
	 * Addition in Version 3.0
	 * 
	 * This is part of the Facebook authentication code
	 */
	
	 @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);

	        facebook.authorizeCallback(requestCode, resultCode, data);
	    }
	 
}