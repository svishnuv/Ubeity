package com.example.maptesting;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ShowMap extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_map);
		
		GoogleMap googlemap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		googlemap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		googlemap.setMyLocationEnabled(true);  		
		LatLng currentLocation = new LatLng(41.8383,-87.6371);
		googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 25));
		googlemap.setBuildingsEnabled(true);
		googlemap.addMarker(new MarkerOptions()
        .title("SB ")
        .snippet("CS 445 Class room")
        .position(currentLocation));    
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_map, menu);
		return true;
	}

}
