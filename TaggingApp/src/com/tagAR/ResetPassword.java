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
 * @description: This activity is the forgot password feature, where if the user has forgotten his password, can enter 
 * the email address with which he registered an account on tagAR and we will generate a new password and email it to him.
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

package com.tagAR;


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

import utilties.Utilities;

import com.tagAR.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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


public class ResetPassword extends Activity implements OnClickListener{

	/**
	 * Change in Version 2.1 
	 * I have changed the urls to the server code from http://worldofpakistan.net/tagar/$fileName where $fileName is the file name
	 * to http://worldofpakistan.net/tagar/$fileName2_1 where $fileName2_1 is the new file name so that i can provide backward compatibility to 
	 * the apps which are of version 2.0. From now onwards if there is any change in the server code then i will keep the old code for backward 
	 * compatibility and use new code and will name the file's name to the latest version of the android app. I have made a fix to the code 
	 * which goes with the fix that i have made in Version 2.1 
	 * 
	 */

	private EditText email;
	private Button resetPassword;
	private static Context context;
	private static String emailText;
	private static ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.resetpassword);

		email=(EditText)findViewById(R.id.emailResetPassword);
		resetPassword=(Button)findViewById(R.id.resetButton);
		resetPassword.setOnClickListener(this);

		context=this;


	}


	public void onClick(View view) {

		int viewId=view.getId();


		if(R.id.resetButton==viewId)
		{//the reset button has been pressed


			emailText=email.getText().toString();
			emailText=emailText.replace(" ", "");

			if((emailText.equals(" ") || emailText.equals("")))
			{//if the email contains just space or no value at all then do not allow to continue.

				Toast.makeText(this, "Please do not leave any of the fields blank.", 1000).show();

			}//end if
			else
			{

				boolean result = Utilities.emailFormatChecker(emailText);

				if(false==result)
				{
					Toast.makeText(this,"The format of the email address is not valid. Please enter again.", 1000).show();
				}//end if
				else
				{



					boolean networkStatus=Utilities.networkStatus(context);

					if(networkStatus)
					{

						new ResetPasswordPostToServer().execute();

					}//end if
					else
					{
						Toast.makeText(this, "No Internet connection detected. Please enable your internet connection.", 1000).show();
						startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 0);//open up the systems screen which allows me to set the internet settings

					}//end else



				}//end else

			}//end else


		}//end if
		else
		{

		}//end else


	}


	/**********onKeyDown function*********/
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{//this function will work with all the Android versions 
		//i am overriding the back button and the home button




		if (keyCode == KeyEvent.KEYCODE_BACK) 
		{

			/**
			 * change in version 1.0
			 * i am passing data to the next activity. this is for the new 
			 * remember login details feature.
			 * 
			 */

			Intent intent=new Intent("com.tagAR.PreLoginTabs");
			startActivity(intent);
			finish();


		}//end if
		else if(keyCode==KeyEvent.KEYCODE_MENU)
		{



		}//end else if


		return super.onKeyDown(keyCode, event);
	}


	class ResetPasswordPostToServer extends AsyncTask<String, Void, Boolean> {

		private String toastMessage;

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if(true==result)
			{//the operation is a success
				progressDialog.dismiss();
				Utilities.showToast(context,toastMessage);
			}//end if
			else
			{//the operation is not a success
				progressDialog.dismiss();
				Utilities.showToast(context,toastMessage);

			}//end else

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			/**
			 * source for reading and writing to a url. http://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html
			 * i am posting the data via the POST method
			 */

			progressDialog = ProgressDialog.show(context, "","Please wait...", true);
			progressDialog.setCancelable(true);


		}

		@Override
		protected Boolean doInBackground(String... params) {



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


				byte [] decodedBytes;
				String data;
				String decodedString;
				StringBuffer stringComplete=new StringBuffer();

				URL url = new URL("http://worldofpakistan.net/tagar/forgotpassword3_0.php");
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);

				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write("email="+emailText);//i am sending data via the post method
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

				//  Log.d("app", decodedString);
				int pos=decodedString.indexOf("],",0);

				int pos1=0;
				String num;
				Integer numOfTerms;
				try
				{
					num=decodedString.substring(2, pos);//i am starting from 2 because [[1 to get 1 i will have to place the pointer just before 1, which lies at 2. at 0 lies the first [, at 1 lies the second [, and at 2 lies the 1(or the first character of the number in the string 
					numOfTerms=Integer.parseInt(num);
				}//end if
				catch(Exception e)
				{
					numOfTerms=-3;
				}//end else

				//Log.d("value of numOfTerms", ((Integer)numOfTerms).toString());

				ArrayList<String> userData=new ArrayList<String>();


				// Toast.makeText(this, numOfTerms.toString(), 1000).show();
				pos=pos+3;
				String sub="";

				//   dialog.dismiss();

				for(int i=0;i<numOfTerms;i++)
				{
					pos1=decodedString.indexOf("]", pos);
					sub=decodedString.substring(pos, pos1);
					pos=pos1+3;
					userData.add(sub);
					

					try
					{
						Integer term=Integer.parseInt(sub);

						/**
						 *
						 *the reason why i am comparing in this fashion where the comparing value is on the left side while the value 
						 *to be compared is on the right side, rather than the normal other way round, is because if i miss using two equals (==) 
						 *for equivalent and use just equal as in assignment (=) then upon compilation i will be informed of such an error, as in this 
						 *case a literal value can not be assigned some other value. 
						 *
						 */


						if(-1==term)
						{
							
							toastMessage="Please do not leave any of the fields blank.";
							return false;
						}//end if
						else if(-2==term)
						{
							
							toastMessage="No user exists for the given email address.";
							return false;
						}//end else if
						else
						{							        

							toastMessage="The password has been reset. Please check your email.";
							return true;

						}//end else



					}//end try
					catch(Exception e)
					{//the string is not a number
						return false;
						// e.printStackTrace();

					}//end catch

				}//end for



			} catch (UnsupportedEncodingException e){

				return false;
				//e.printStackTrace();
			} catch (MalformedURLException e) {

				return false;
				//e.printStackTrace();
			} catch (IOException e) {

				return false;
				//e.printStackTrace();
			}
			return false;

		}

	}

}