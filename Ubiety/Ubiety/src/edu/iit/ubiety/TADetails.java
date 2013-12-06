package edu.iit.ubiety;



import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Button;

public class TADetails extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tadetails);
		ActionBar actionBar = getActionBar();
		 
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
		
		Button save = (Button) findViewById(R.id.save);
		
	   save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent logIntent = new Intent(TADetails.this, ProfProfile.class);
				startActivity(logIntent);
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
	                Intent in = new Intent(TADetails.this, Login.class);
	                startActivity(in);
	                return true;
	            }
	        });
			return false;
	       
	        
	    }
	    
}