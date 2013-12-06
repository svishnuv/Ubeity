package com.example.mycloud;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class tempActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String key1 = bundle.getString("key");
		String value1 = bundle.getString("value");

		Parse.initialize(this, "tfdyAUkvGhTOUwF1jSQjU264pTe87wzwDAflPBOd", "cGoNqfBYj9ZYxSC230FU2ekh5WL6wEQqok2SKmQe");	
		ParseAnalytics.trackAppOpened(getIntent());
		ParseObject testObject = new ParseObject("TestObject");
		
		
		testObject.put(key1, value1);
		
		testObject.saveInBackground();
		
		Intent main_screen = new Intent(this, MainActivity.class);
		startActivity(main_screen);
		
	}
	
}	
		
