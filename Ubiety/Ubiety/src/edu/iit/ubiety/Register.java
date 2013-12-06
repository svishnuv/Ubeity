package edu.iit.ubiety;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Register extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profview);
		
		Button register = (Button) findViewById(R.id.register);
		
		
		register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent logIntent = new Intent(Register.this, ProfProfile.class);
				startActivity(logIntent);
			}
		});
		
	}
}