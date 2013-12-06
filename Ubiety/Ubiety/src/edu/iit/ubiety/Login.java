package edu.iit.ubiety;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		Button login = (Button) findViewById(R.id.login);
		Button signup= (Button) findViewById(R.id.signup);
		
		login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent logIntent = new Intent(Login.this, Register.class);
				startActivity(logIntent);
			}
		});
		
		signup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent signIntent = new Intent(Login.this,Register.class);
				startActivity(signIntent);
				
			}
		});
}
}
	


