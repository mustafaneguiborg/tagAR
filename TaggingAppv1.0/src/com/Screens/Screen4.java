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
 * @version: 1.0 
 * @description: This activity allows the user to enter registration information. Upon submission of the form the app checks if the
 * internet connection is enabled and if not, then the user is shown the settings screen to enable the net.
 *   
 */


/**
 * Changes in Version 1.0
 * i am passing in the data variable userAccounts and email and password to Screen3.java or com.TagScreen3 because Screen3.java requires 
 * the data to be retrieved. Since the internal file may have data in it and we do not want to loose it so we are passing around
 * the userAccount from Screen3.java to all the classes except Screen5.java (as we do not need the userAccount in it) as we have to 
 * go back to Screen3.java from those classes (activities). 
 * 
 */

package com.Screens;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Screen4 extends Activity implements OnClickListener {

	private static Context context;
	private EditText firstName;
	private EditText lastName;
	private EditText email;
	private EditText password;
	private Button registerButton;
	private Handler handler = new Handler();
	private static String toastMessage;
	private static ProgressDialog progressDialog;
	private static String firstNameText;
	private static String lastNameText;
	private static String emailText;
	private static String passwordText;
	private TextView loginLinkActivity;
	/**
	 * Addition in version 1.0
	 */
	private static ArrayList<String>userAccounts;// i have to make this static because then i get null value in the userAccounts variable when i try to access it in the onClick function. 

	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.screen4);
        
        
        firstName=(EditText)findViewById(R.id.firstNameRegistration);
        lastName=(EditText)findViewById(R.id.lastNameRegistration);
        email=(EditText)findViewById(R.id.emailRegistration);
        password=(EditText)findViewById(R.id.passwordRegistration);
        
        registerButton=(Button)findViewById(R.id.registerButton); 
        registerButton.setOnClickListener(this);
              
        loginLinkActivity=(TextView)findViewById(R.id.loginLinkActivity);
        loginLinkActivity.setOnClickListener(this);
        context=this;
        
        
        /**
         * Addition in version 1.0
         *   
         */
        Bundle bundle=getIntent().getExtras();
        boolean emptyUserAccountsList=bundle.getBoolean("emptyUserAccountsList");
    	userAccounts=bundle.getStringArrayList("userAccounts");
 
        
    }

	
	/**
	 * 
	 * source for showing Toast notification to the ui thread from a thread.
	 * http://www.codeproject.com/Articles/109735/Toast-A-User-Notification
	 * 
	 * I need to pass a handler which can send messages to the ui thread.
	 * 
	 */
	
	  public void showToast() {
		          handler.post(new Runnable() {
		               public void run() {
		                   Toast.makeText(getApplicationContext(),
		                           toastMessage, Toast.LENGTH_SHORT).show();
		               }
		           });
		      }
	  
	
	@Override
	public void onClick(View view) {
		
		int viewId=view.getId();
		firstNameText=firstName.getText().toString();
		lastNameText=lastName.getText().toString();
		emailText=email.getText().toString();
		passwordText=password.getText().toString();
				
	
        
        if(R.id.registerButton==viewId)
        {
        	
        	firstNameText=firstNameText.replace(" ", "");
        	lastNameText=lastNameText.replace(" ","");
			emailText=emailText.replace(" ","");
			passwordText=passwordText.replace(" ","");
        	
        	
			if((firstNameText.equals(" ") || firstNameText.equals("")) || (lastNameText.equals(" ") || lastNameText.equals("")) || (emailText.equals(" ") || emailText.equals("")) || (passwordText.equals(" ") || passwordText.equals("")))
			{//if the first name, last name, email or password  contains just space or no value at all then do not allow to continue.
			
				Toast.makeText(this, "Please do not leave any of the fields blank.", 1000).show();
				
			}//end if
			else
			{
				
				
				/**
				 * 
				 * I am checking for the format of the email address
				 * source: http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
				 * 
				 */
				  Pattern p = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
			      Matcher m = p.matcher(emailText);
			      boolean result = m.matches();
			      
			      if(false==result)
			      {
			    	  Toast.makeText(this,"The format of the email address is not valid. Please enter again.", 1000).show();
			      }//end if
			      else
			      {
				
				
					/**
					 *
					 *check for internet connection. if the internet connection is not enabled then ask the user to enable it.
					 *http://stackoverflow.com/questions/4238921/android-detect-whether-there-is-an-internet-connection-available
					 * 
					 */
					
				    
			        ConnectivityManager connectivityManager =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			        NetworkInfo networkStatus= connectivityManager.getActiveNetworkInfo();
		        
			        
			        if(networkStatus!=null)
			        {
			        	//source for reading and writing to a url. http://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html
			        	//i am posting the data via the POST method
			        	progressDialog = ProgressDialog.show(context, "","Please wait...", true);
			        	progressDialog.setCancelable(true);
			        	
			        	new Thread() {
			        		
							public void run() {
			        	
				        	 try {
								
								/**
								 * 
								 *i am encoding the values into base64 encoding to provide a certain amount of security.
								 *i do not want to send the data as plain text over the airwaves
				        		 *i have chosen base64 instead of other encoding schemes such as md5 has, because i want 
				        		 *a 2 way encryption scheme which can be encoded and also be decoded because on the server side
				        		 *i need to decode the data to be able to perform operations
								 * 
								 */
				        		 
				        		    emailText=Base64.encodeToString(emailText.getBytes(),Base64.DEFAULT);
									passwordText=Base64.encodeToString(passwordText.getBytes(),Base64.DEFAULT);
									firstNameText=Base64.encodeToString(firstNameText.getBytes(),Base64.DEFAULT);
									lastNameText=Base64.encodeToString(lastNameText.getBytes(),Base64.DEFAULT);
														
									 byte [] decodedBytes;
									 String data;
						             String decodedString;
						             StringBuffer stringComplete=new StringBuffer();
						             
						             URL url = new URL("http://worldofpakistan.net/tagar/register.php");
						             URLConnection connection = url.openConnection();
						             connection.setDoOutput(true);
						            
						             OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
						             out.write("email="+emailText+"&password="+passwordText+"&firstName="+firstNameText+"&lastName="+lastNameText);//i am sending data via the post method
						             out.close();
						
						             BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						             		             
						             while ((data = in.readLine()) != null) 
						             {
						            	 stringComplete.append(data);
						            	 
						             }//end while
								        			        	             
						             in.close();
						             
						             decodedBytes=Base64.decode(stringComplete.toString(),Base64.DEFAULT);
						             decodedString=new String(decodedBytes);
						            // Toast.makeText(this, decodedString, 1000).show();
						            
						             int pos=decodedString.indexOf("],",0);
						             int pos1=0;
						             String num=decodedString.substring(2, pos);//i am starting from 2 because [[1 to get 1 i will have to place the pointer just before 1, which lies at 2. at 0 lies the first [, at 1 lies the second [, and at 2 lies the 1(or the first character of the number in the string 
						             Integer numOfTerms=Integer.parseInt(num);
						             
						            // Toast.makeText(this, numOfTerms.toString(), 1000).show();
						             pos=pos+3;
						             String sub="";
						             for(int i=0;i<numOfTerms;i++)
						             {
						            	 pos1=decodedString.indexOf("]", pos);
						            	 	            	 
						            	 
						            	// Log.d("pos", ((Integer)pos).toString());
						            	 //Log.d("pos1", ((Integer)pos1).toString());
						            	 sub=decodedString.substring(pos, pos1);
						            	 pos=pos1+3;
						            	 		            	 
						            	 //Toast.makeText(this, sub, 1000).show();
						            	
						            	// Log.d("app", sub);
						            
						            	  
							             try
							             {
								             Integer term=Integer.parseInt(sub);
								             //Log.d("term", ((Integer)term).toString());
								             //the reason why i am comparing in this fashion where the comparing value is on the left side while the value
								             //to be compared is on the right side, rather than the normal other way round, is because if i miss using two equals (==) 
								             //for equivalent and use just equal as in assignment (=) then upon compilation i will be informed of such an error, as in this
								             //case a literal value can not be assigned some other value.
								             if(1==term)
								             {							            	 
								            	 
								            	 progressDialog.dismiss();
								            	 toastMessage="Account created. Please Login";
								            	 showToast();
   	 
								            	 /**
								            	  * changes in Version 1.0
								            	  * We are now passing data to the next activity so that the new feature
								            	  * remember login details can function properly.								            	  * 
								            	  */
								            	 
								            	 
									          	   Intent intent=new Intent("com.TagScreen3");
									               intent.putExtra("emptyUserAccountsList",false);
									               intent.putStringArrayListExtra("userAccounts",userAccounts);
									               intent.putExtra("email","");
									    	       intent.putExtra("password","");
									               startActivity(intent);
									               finish();
								          	   	 
								          	   	 
								             }//end if
								             else if(-1==term)
								             {
								            	 progressDialog.dismiss();
								            	 toastMessage="Please do not leave any of the fields blank.";
								            	 showToast();//i am calling the public function which will display a Toast notification by using the handler
								            	 
								             }//end else if
								             else if(-2==term)
								             {
								            	 progressDialog.dismiss();
								            	 toastMessage="An account already exists for the given email address. Please login from the login screen. If you have forgotten your password you can reset it from the login screen.";
								            	 showToast();

								            	 
								             }//end else if
								             else
								             {
								            	 progressDialog.dismiss();

								             
								             }//end else
								             
								             
							             }//end try
							             catch(Exception e)
							             {//the string is not a number
							            	 
							            	// e.printStackTrace();
							             
							            	 
							             }//end catch
						             }//end for
				        		 
				        	 
							} catch (UnsupportedEncodingException e){
								
								//e.printStackTrace();
							} catch (MalformedURLException e) {
							
								//e.printStackTrace();
							} catch (IOException e) {
							
								//e.printStackTrace();
							}
			        	 
						}}.start();
			        	
			        }//end if
			        else
			        {
			        	Toast.makeText(this, "No Internet connection detected. Please enable your internet connection.", 1000).show();
			       	 	startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 0);//open up the systems screen which allows me to set the internet settings
			        
			        }//end else
				
			      }//end else
			}//end else
		
        	
	        

        }//end if
        else if(R.id.loginLinkActivity==viewId)
        {
        	
        	 /**
	       	  * changes in Version 1.0
	       	  * We are now passing data to the next activity so that the new feature
	       	  * remember login details can function properly.								            	  * 
	       	  */
        			   	 
       	   	Intent intent=new Intent("com.TagScreen3");
            intent.putExtra("emptyUserAccountsList",false);
            intent.putStringArrayListExtra("userAccounts",userAccounts);
            intent.putExtra("email","");
 	       	intent.putExtra("password","");
            startActivity(intent);
            finish();

			
        }//end else if
	}

	
	  /**********onKeyDown function*********/
			public boolean onKeyDown(int keyCode, KeyEvent event)
			{//this function will work with all the Android versions 
				//i am overriding the back button and the home button



			
				if (keyCode == KeyEvent.KEYCODE_BACK) 
				{
			   	 
					 /**
	            	  * changes in Version 1.0
	            	  * We are now passing data to the next activity so that the new feature
	            	  * remember login details can function properly.								            	  * 
	            	  */
					
	          	   Intent intent=new Intent("com.TagScreen3");
	               intent.putExtra("emptyUserAccountsList",false);
	               intent.putStringArrayListExtra("userAccounts",userAccounts);
	               intent.putExtra("email","");
	    	       intent.putExtra("password","");
	               startActivity(intent);
	               finish();


				}//end if
				else if(keyCode==KeyEvent.KEYCODE_MENU)
				{
					

					
				}//end else if


				return super.onKeyDown(keyCode, event);
			}


}