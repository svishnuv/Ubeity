package edu.iit.ubiety;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProfProfile extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profview);
		ActionBar actionBar = getActionBar();
		 
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
		
		TextView prof = (TextView) findViewById(R.id.details);
		TextView ta = (TextView) findViewById(R.id.tadetails);
		TextView course = (TextView) findViewById(R.id.course);
		
		
		prof.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent adddetails = new Intent(ProfProfile.this, ProfDetails.class);
				 
				Bundle bndlanimation =
			    ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
				startActivity(adddetails, bndlanimation);
 
			}
		});
		
		ta.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent taIntent = new Intent(ProfProfile.this, TADetails.class);
				Bundle bndlanimation =
					    ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
						startActivity(taIntent, bndlanimation);
		 
				
			}
		});
        course.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent courseIntent = new Intent(ProfProfile.this,CourseList.class);
				Bundle bndlanimation =
					    ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
						startActivity(courseIntent , bndlanimation);
		 
			}
		});
           
	}
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.activity_logout_actions, menu);
	 
	        return super.onCreateOptionsMenu(menu);
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        item.setOnMenuItemClickListener(new OnMenuItemClickListener() {
	            public boolean onMenuItemClick(MenuItem item) {
	                Intent in = new Intent(ProfProfile.this, Login.class);
	                startActivity(in);
	                return true;
	            }
	        });
			return false;
	       
	        
	    }
	    
}
