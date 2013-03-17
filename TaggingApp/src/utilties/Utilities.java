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
 * @description: This class has been designed as a utility class. We will be using this class to call
 * function which provide a utility.
 */
package utilties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.widget.Toast;

public class  Utilities {
    
	public static final String PREFS_NAME = "tagARPrefs";//This will point to the shared preferences of tagAR

    
	public static boolean networkStatus(Context context)
	{
		
		/**
		 *
		 *check for internet connection. if the internet connection is not enabled then ask the user to enable it.
		 *http://stackoverflow.com/questions/4238921/android-detect-whether-there-is-an-internet-connection-available
		 * 
		 */
		
		ConnectivityManager connectivityManager =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkStatus= connectivityManager.getActiveNetworkInfo();
        
        if(null!=networkStatus)
        {
        	return true;	
        }//end if
        else
        {
        	return false;
        }//end else
		
		
	}
	
	
	public static boolean emailFormatChecker(String email)
	{
		/**
		 * 
		 * I am checking for the format of the email address
		 * source: http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
		 * 
		 */
		 Pattern p = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	     Matcher m = p.matcher(email);
	     return  m.matches();
	}
	
		
	
	
	public static void setSharedPreferencesString(Context context,String key, String value)
	{ 

		 SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
	     SharedPreferences.Editor editor = settings.edit();
	  	 editor.putString(key, value).commit();

	}
	
	public static void setSharedPreferencesInteger(Context context,String key, Integer value)
	{ 

		 SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
	     SharedPreferences.Editor editor = settings.edit();
	  	 editor.putInt(key, value).commit();

	}
	
	
	public static void setSharedPreferencesFloat(Context context,String key, Float value)
	{ 

		 SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
	     SharedPreferences.Editor editor = settings.edit();
	  	 editor.putFloat(key, value).commit();

	}
	
	public static void setSharedPreferencesBoolean(Context context,String key, Boolean value)
	{ 

		 SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
	     SharedPreferences.Editor editor = settings.edit();
	  	 editor.putBoolean(key, value).commit();

	}
	
	public static void setSharedPreferencesLong(Context context,String key, Long value)
	{ 

		 SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
	     SharedPreferences.Editor editor = settings.edit();
	  	 editor.putLong(key, value).commit();

	}
	
	
	
	public static Object getSharedPreferences(Context context,String key,char type)
	{
		/**
		 * s: String
		 * i: Integer
		 * b: Boolean
		 * l: Long
		 * f: Float 
		 */
		
		 SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
	     
		 if('s'==type)
		 {
			return (Object)settings.getString(key, ""); 
		 }//end if
		 else if('i'==type)
		 {
				return (Object)settings.getInt(key,0); 

		 }//end else if
		 else if('b'==type)
		 {
			 return (Object)settings.getBoolean(key, false);
		 }//end else if
		 else if('l'==type)
		 {
			 return (Object)settings.getLong(key, 0);

		 }//end else if
		 else if('f'==type)
		 {
			 return (Object)settings.getFloat(key,0.0f);

		 }//end else if
		 else
		 {
			 return null;
		 }//end else
		 
		

	}
	
	
	

	public static void showToast(final Context context, final String message) {
		
	 Handler handler = new Handler();
	 
		/**
		 * 
		 * source for showing Toast notification to the ui thread from a thread.
		 * http://www.codeproject.com/Articles/109735/Toast-A-User-Notification
		 * 
		 * I need to pass a handler which can send messages to the ui thread.
		 * 
		 */

		handler.post(new Runnable() {
			public void run() {
				Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
			}
		});
	}
	

}
