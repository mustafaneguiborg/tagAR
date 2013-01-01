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
 * @description: This class is a helper class which is being used as a wrapper for the
 * Facebook SDK class.
 */
package facebookConnector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.content.Context;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

/**
 * 
 * Addition in Version 3.0
 * I am added this new helper class to be used in the project.
 * This class will be used as a wrapper class to the Facebook class.
 */

public class FacebookConnector {
	
	/**
	 * Addition in Version 3.0
	 * I have made this class implement serializable because i
	 * will be saving the object of FacebookConnector in an intent object
	 * and will be passing it to the next activity so that it can be used there.
	 */
	private String facebookApiKey="ENTER-YOUR-FACEBOOK-APP-ID";
	private Facebook facebook;
    private AsyncFacebookRunner mAsyncRunner;

    private String userId;
	private String email;
	private String firstName;
	private String lastName;
	
	
	private ArrayList<String> userData=new ArrayList<String>();
	private ProgressDialog progressDialog;

	
	public FacebookConnector() {
		super();
		
		 this.facebook= new Facebook(facebookApiKey);
		 this.mAsyncRunner= new AsyncFacebookRunner(facebook);
		
	}
	
	
	
	public void getUserData()
	{
		
            
		 	// get information about the currently logged in user
    		mAsyncRunner.request("me", new RequestListener(){

			public void onComplete(String response, Object state) {
			

				//Toast.makeText(context, response, 2000).show();
				Log.d("app",response);
				
				//http://stackoverflow.com/questions/2255220/how-to-parse-a-json-and-turn-its-values-into-an-array
				 
				JSONObject object;
				try {
					object = new JSONObject(response);
					JSONArray nameArray=object.names();
		            JSONArray valArray = object.toJSONArray(nameArray);
		           
					 for(int i=0;i<valArray.length();i++)
		             {
		                String p = nameArray.getString(i);
		                
		                if(p.equalsIgnoreCase("email"))
		                {
		                	email=valArray.getString(i);
		                
		                }//end if
		                else if(p.equalsIgnoreCase("id"))
		                {
		                	
		                	userId=valArray.getString(i);
		                	Log.d("userId",userId);
		                }//end else if
		                else if(p.equalsIgnoreCase("first_name"))
		                {
		                	firstName=valArray.getString(i);
		                }//end else if
		                else if(p.equalsIgnoreCase("last_name"))
		                {
		                	lastName=valArray.getString(i);
		                }//end else if
		                
		                
		            }//end for
					 
					 /**
					  * Addition in Version 3.0
					  * I am creating an intent object here and starting the
					  * next activity because i need to make sure that the userData
					  * has been retrieved completely. This is the only function which
					  * will ensure me that since the onComplete function is run when the
					  * operation has been completed. Calling in the activites caused problem
					  * because the facebook commands are called asynchronously.
					  * 
					  */
					 
					 	userData.add(userId);
			    		userData.add(email);
			    		userData.add(firstName);
			    		userData.add(lastName);
			    		
			    		
					
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				}
			
				
			}

			public void onIOException(IOException e, Object state) {
				// TODO Auto-generated method stub
				
			}

			public void onFileNotFoundException(
					FileNotFoundException e, Object state) {
				// TODO Auto-generated method stub
				
			}

			public void onMalformedURLException(
					MalformedURLException e, Object state) {
				// TODO Auto-generated method stub
				
			}

			public void onFacebookError(FacebookError e, Object state) {
				// TODO Auto-generated method stub
				
			}
    		
    		
    		
    		
    	});
		
    		
    		
    		
	}
	
	
	public Boolean getStatus()
	{
		return facebook.isSessionValid();
	}
	
	
	public void login(final Context context)
	{
		
    	

		facebook.authorize((Activity) context, new String[] { "email", "publish_checkins","user_status","user_website","user_education_history","user_birthday","user_likes","user_photos" }, new DialogListener() {
            
            public void onComplete(Bundle values) {
            	
            	//Toast.makeText(context, "logged in", 1000).show();
            	
            	/**
            	 * Addition in Version 3.0
            	 * I am showing a progress dialog because after the user logs in, 
            	 * getting the data of the user takes some time, so i want to
            	 * inform the user not to worry and that the application is 
            	 * doing some processing.
            	 */
            	progressDialog = ProgressDialog.show(context, "","Please wait...", true);
				progressDialog.setCancelable(true);
            	
				getLoginUserData(context);

        
            	 
            }

            
            public void onFacebookError(FacebookError error) {
            	

            }

            
            public void onError(DialogError e) {
            	
            }

            public void onCancel() {
            	
            }
        });

		
	}
	
	public void getLoginUserData(final Context context)
	{
			
		 	// get information about the currently logged in user
    		mAsyncRunner.request("me", new RequestListener(){

			public void onComplete(String response, Object state) {
			

				//Toast.makeText(context, response, 2000).show();
				Log.d("app",response);
				
				//http://stackoverflow.com/questions/2255220/how-to-parse-a-json-and-turn-its-values-into-an-array
				 
				JSONObject object;
				try {
					object = new JSONObject(response);
					JSONArray nameArray=object.names();
		            JSONArray valArray = object.toJSONArray(nameArray);
		           
					 for(int i=0;i<valArray.length();i++)
		             {
		                String p = nameArray.getString(i);
		                
		                if(p.equalsIgnoreCase("email"))
		                {
		                	email=valArray.getString(i);
		                
		                }//end if
		                else if(p.equalsIgnoreCase("id"))
		                {
		                	
		                	userId=valArray.getString(i);
		                	Log.d("userId",userId);
		                }//end else if
		                else if(p.equalsIgnoreCase("first_name"))
		                {
		                	firstName=valArray.getString(i);
		                }//end else if
		                else if(p.equalsIgnoreCase("last_name"))
		                {
		                	lastName=valArray.getString(i);
		                }//end else if
		                
		                
		            }//end for
					 
					 /**
					  * Addition in Version 3.0
					  * I am creating an intent object here and starting the
					  * next activity because i need to make sure that the userData
					  * has been retrieved completely. This is the only function which
					  * will ensure me that since the onComplete function is run when the
					  * operation has been completed. Calling in the activites caused problem
					  * because the facebook commands are called asynchronously.
					  * 
					  */
					 
					 	userData.add(userId);
			    		userData.add(email);
			    		userData.add(firstName);
			    		userData.add(lastName);
			    		

			    		Intent intent=new Intent("com.TagScreen5");
			    		Bundle b=new Bundle();
			            intent.putStringArrayListExtra("userData",userData);
			            /**
			             * Addition in Version 3.0
			             * I am sending the facebook access token via a bundle to the next activity
			             * so that it can be used. Developers usually store the access token
			             * in shared preference, but i do not need to do so, because i am
			             * getting the latest value of the access token when ever the user logs in.
			             * I am also saving a flag which will tell whether the user has logged in
			             * via facebook or not. 
			             */
			            
			            intent.putExtra("accessToken",facebook.getAccessToken());
			            intent.putExtra("facebookLogin",true);
			            /**
			             * Addition in Version 3.0
			             * Dismiss the progress dialog that was started
			             * in the login function.
			             */
			    		
			           
				          progressDialog.dismiss();
	
				          context.startActivity(intent);
				          ((Activity) context).finish();
			             
				}//end try
				catch(Exception e)
				{
					e.printStackTrace();
				}//end catch
				
				
			
				
			}

			public void onIOException(IOException e, Object state) {
				// TODO Auto-generated method stub
				
			}

			public void onFileNotFoundException(
					FileNotFoundException e, Object state) {
				// TODO Auto-generated method stub
				
			}

			public void onMalformedURLException(
					MalformedURLException e, Object state) {
				// TODO Auto-generated method stub
				
			}

			public void onFacebookError(FacebookError e, Object state) {
				// TODO Auto-generated method stub
				
			}
    		
    		
    		
    		
    	});
		
    		
    		
    		
	}
	
	public void setAccessToken(String token)
	{
		facebook.setAccessToken(token);
	}
	
	public String getAccessToken()
	{
		return facebook.getAccessToken();
	}
	
    public void authorizeCallback(int requestCode, int resultCode, Intent data)
    {
        facebook.authorizeCallback(requestCode, resultCode, data);

    }

	
	
}
