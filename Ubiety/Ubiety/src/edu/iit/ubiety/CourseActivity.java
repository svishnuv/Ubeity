package edu.iit.ubiety;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.TextView;
 
public class CourseActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.proftools);
         
        TextView txtProduct = (TextView) findViewById(R.id.coursename);
         
        Intent i = getIntent();
        // getting attached intent data
        String course = i.getStringExtra("course");
        // displaying selected product name
        txtProduct.setText(course);
         
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
                Intent in = new Intent(CourseActivity.this, Login.class);
                startActivity(in);
                return true;
            }
        });
		return false;
       
        
    }
    
}
