package com.example.mycloud;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SignUpCallback;

import android.net.ParseException;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Initial the connections to the parse cloud.   
		Parse.initialize(this, "tfdyAUkvGhTOUwF1jSQjU264pTe87wzwDAflPBOd", "cGoNqfBYj9ZYxSC230FU2ekh5WL6wEQqok2SKmQe");	
		ParseAnalytics.trackAppOpened(getIntent());		
		
        //Security of data        
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        
        //Enable to receive push
        PushService.setDefaultPushCallback(this, MainActivity.class);
        ParseInstallation pi = ParseInstallation.getCurrentInstallation();
        
        
        //Register a channel to test push channels
        Context ctx = this.getApplicationContext();
        PushService.subscribe(ctx, "Presence", MainActivity.class);       
        pi.saveEventually();
        
        
        
	}
	
public void getObjects(View v){


    ParseQuery<ParseObject> query = ParseQuery.getQuery("NewClass");  // Class of the object
    query.findInBackground(new FindCallback<ParseObject>() {
		@Override
		public void done(List<ParseObject> myList, com.parse.ParseException e) {       // Retrieve all the objects of the class and store as a list of objects
			// TODO Auto-generated method stub
	           if (e == null) {
	        	   
                   Intent myIntent  = new Intent(getApplicationContext(), ListViewClass.class);
                   Bundle bundle = new Bundle();
                   ArrayList<String> myArrayList = new ArrayList<String>();
                   
	                for(int i=0;i<myList.size();i++)  {
	                     Log.d("data","Retrieved Object is " + myList.get(i).getString("YO"));  
	                    // mFuncDate.add( scoreList.get(i).getString("Date"));
	                    myArrayList.add(myList.get(i).getString("USA"));
	                }  
	                
	               bundle.putStringArrayList("myArrayList", myArrayList);
	               myIntent.putExtras(bundle);
	               startActivity(myIntent);

	                
	            } else {
	                Log.d("Data", "Error: " + e.getMessage());
	            }
		}
    });
}
	 
		 

	 
public void mysignup(View v){  
			
    			Intent myIntent  = new Intent(getApplicationContext(), signup.class);	
				Bundle bndlanimation =
						ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
				startActivity(myIntent, bndlanimation);


			 			
			// The below code is bundle the values.
			/*
			Bundle bundle = new Bundle();
			bundle.putString("key",key1);
			bundle.putString("value1", value1);
			Log.e("Key value: ",key1);
			Log.e("Value value : ", value1);
			ParseObject testObject = new ParseObject("TestObject");
			
			
			testObject.put(key1, value1);
			
			testObject.saveInBackground();
			
			*/
		
		}
			
	public void mylogin(View v){
		
		EditText key = (EditText) findViewById(R.id.keyg);
		EditText value = (EditText) findViewById(R.id.valueg);
		String key1 = key.getText().toString();
		String value1 = value.getText().toString();
		
		ParseUser.logInInBackground(key1, value1, new LogInCallback() {
			@Override
			public void done(ParseUser user, com.parse.ParseException e) {
				// TODO Auto-generated method stub
			    if (user != null) {
				      Log.i("User Logged in","data");
				      
		    			Intent myIntent  = new Intent(getApplicationContext(), screen1.class);	
						Bundle bndlanimation =
								ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
						startActivity(myIntent, bndlanimation);
				    } else {
				    	
				    	Log.e("Error in Login",e.getMessage());
				    }
			}
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	 }
