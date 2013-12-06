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
import com.parse.ParsePush;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class screen1 extends Activity {
    private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen1);
		
		/*
		 ArrayList<String> my_array_list = new ArrayList<String>();
			Bundle bundle = getIntent().getExtras();		
			my_array_list = bundle.getStringArrayList("myArrayList");	    
	        ArrayList<String> my_array_list1 = new ArrayList<String>();
			
			
         for(int i=0;i<my_array_list.size();i++)  {
             Log.d("data","Retrieved Object are " + my_array_list.get(i));  
             if(my_array_list.get(i) != null){
             	
             		my_array_list1.add(my_array_list.get(i));
             }
            // mFuncDate.add( scoreList.get(i).getString("Date"));
        } 

         for(int i=0;i<my_array_list1.size();i++)  {
             Log.d("data","After Removing null " + my_array_list1.get(i));  
             
         }
			try{
	         lv = (ListView) findViewById(R.id.screen1_lv);
	         ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, my_array_list1);
	         lv.setAdapter(arrayAdapter); 
	         arrayAdapter.notifyDataSetChanged();
			}
			catch(Exception e){
				
				Log.e("The exception was: ",e.getMessage());
			}
			
			finally
			{
				Log.e("Some Error occured","");
			}

			*/
	}
	
	public void myPush(View v){
		
		ParsePush push = new ParsePush();
		push.setChannel("Presence");
		push.setMessage("Yo This works..!!!");
		push.sendInBackground();
		Toast.makeText(getApplicationContext(),"Pushed Successfully..!!!", Toast.LENGTH_LONG).show();
		
		Intent myIntent  = new Intent(getApplicationContext(), MainActivity.class);	
		Bundle bndlanimation =
				ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3,R.anim.animation4).toBundle();
		startActivity(myIntent, bndlanimation);

	}
	
	public void myQr(View v){
		
		//Intent myIntent  = new Intent(getApplicationContext(), QrHandler.class);
		//startActivity(myIntent);
	}
	
	public boolean onOptionsItemsSelected(MenuItem item){
		
		switch(item.getItemId()){
		
		case R.id.action_forward:
			Toast.makeText(getApplicationContext(),"Forward Button Clicked", Toast.LENGTH_LONG).show();
			
		
		}
		
		return true;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}
}
