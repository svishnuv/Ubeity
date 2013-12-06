package com.example.mycloud;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.R;
import android.net.ParseException;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewClass extends Activity {
	    private ListView lv;
		@Override
	    		public void onCreate(Bundle saveInstanceState) {
			super.onCreate(saveInstanceState);
			//setContentView(R.layout.mylistviewxml);
			
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
	         lv = (ListView) findViewById(R.id.list);
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
        
	}
	
}	