package com.example.mycloud;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.net.ParseException;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

public class signup extends Activity {
	
	int isProf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		// Initial the connections to the parse cloud.   
		Parse.initialize(this, "tfdyAUkvGhTOUwF1jSQjU264pTe87wzwDAflPBOd", "cGoNqfBYj9ZYxSC230FU2ekh5WL6wEQqok2SKmQe");	
		ParseAnalytics.trackAppOpened(getIntent());		

		//Set Spinner Adapter
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.college_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}
	
	public void checkProf(){
		
		isProf = 1;
	}
	
	public void checkNotProf(){
		
		isProf = 0;
	}
	
	@SuppressLint("NewApi")
	public void clicksignup(View v){
		
		
				
		EditText fname = (EditText) findViewById(R.id.fname);
		EditText lname = (EditText) findViewById(R.id.lname);
		EditText uemail = (EditText) findViewById(R.id.uemail);
		EditText uphone = (EditText) findViewById(R.id.uphone);
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		EditText uname = (EditText) findViewById(R.id.uname);
		EditText pwd = (EditText) findViewById(R.id.pwd);
		
		String fname1 = fname.getText().toString();
		String lname1 = lname.getText().toString();
		String uemail1 = uemail.getText().toString();
		String uphone1 = uphone.getText().toString();
		String spinner1 = spinner.getSelectedItem().toString();
		String uname1 = uname.getText().toString();
		String pwd1 = pwd.getText().toString();

		
		ParseUser user = new ParseUser();
		user.setUsername(uname1);
		user.setPassword(pwd1);
		user.setEmail(uemail1);
		user.put("FirstName",fname1);
		user.put("LastName",lname1);
		user.put("phone", uphone1);
		user.put("college",spinner1);
		
		ToggleButton toggle = (ToggleButton) findViewById(R.id.toggle);
		toggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			
							if(isChecked){
								
								checkProf();
							}
							
							else{
								
								checkNotProf();
							}
		            }
		        });
		
		user.put("IsProfessor",isProf);
		
		user.signUpInBackground(new SignUpCallback() {
			@Override
			public void done(com.parse.ParseException e) {
				// TODO Auto-generated method stub
				
			    if (e == null) {
				      // Hooray! Let them use the app now.
			    		Log.i("Sign up Successful: ","data");
			    		Toast.makeText(getApplicationContext(),"Sign Up Successfull", Toast.LENGTH_LONG).show();

			    		Intent myIntent  = new Intent(getApplicationContext(), MainActivity.class);
			    		
						Bundle bndlanimation =
								ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
						startActivity(myIntent, bndlanimation);
				    } 
			    
			    
			    else {
			    		Log.e("Signup Failed: ",e.getMessage());
			    		Toast.makeText(getApplicationContext(),"Sign Up Failed", Toast.LENGTH_LONG).show();
			    		

				    }
				
			}
				});
		
	}
	
	
	
	

}
