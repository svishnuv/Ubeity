/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.maptesting;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Locale;


public class MainActivity extends FragmentActivity implements LocationListener,
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	// A request to connect to Location Services
	private LocationRequest mLocationRequest;

	// Stores the current instantiation of the location client in this object
	private LocationClient mLocationClient;

	// Handles to UI widgets
	private TextView mLatLng;
	private TextView mAddress;
	private ProgressBar mActivityIndicator;
	private TextView mConnectionState;
	private TextView mConnectionStatus;

	// Handle to SharedPreferences for this app
	SharedPreferences mPrefs;

	// Handle to a SharedPreferences editor
	SharedPreferences.Editor mEditor;

	
	boolean mUpdatesRequested = false;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button showMapButton = (Button) findViewById(R.id.show_map);
		showMapButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ShowMap.class);
				startActivity(intent);
			}
		});

		// Get handles to the UI view objects
		mLatLng = (TextView) findViewById(R.id.lat_lng);
		// mAddress = (TextView) findViewById(R.id.address);
		// mActivityIndicator = (ProgressBar)
		// findViewById(R.id.address_progress);
		mConnectionState = (TextView) findViewById(R.id.text_connection_state);
		mConnectionStatus = (TextView) findViewById(R.id.text_connection_status);

		// Create a new global location parameters object
		mLocationRequest = LocationRequest.create();

		/*
		 * Set the update interval
		 */
		mLocationRequest
				.setInterval(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);

		// Use high accuracy
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

		// Set the interval ceiling to one minute
		mLocationRequest
				.setFastestInterval(LocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);

		// Note that location updates are off until the user turns them on
		mUpdatesRequested = false;

		// Open Shared Preferences
		mPrefs = getSharedPreferences(LocationUtils.SHARED_PREFERENCES,
				Context.MODE_PRIVATE);

		// Get an editor
		mEditor = mPrefs.edit();

		/*
		 * Create a new location client, using the enclosing class to handle
		 * callbacks.
		 */
		mLocationClient = new LocationClient(this, this, this);

	}

	
	@Override
	public void onStop() {

		// If the client is connected
		if (mLocationClient.isConnected()) {
			stopPeriodicUpdates();
		}

		// After disconnect() is called, the client is considered "dead".
		mLocationClient.disconnect();

		super.onStop();
	}

	
	@Override
	public void onPause() {

		// Save the current setting for updates
		mEditor.putBoolean(LocationUtils.KEY_UPDATES_REQUESTED,
				mUpdatesRequested);
		mEditor.commit();

		super.onPause();
	}

	/*
	 * Called when the Activity is restarted, even before it becomes visible.
	 */
	@Override
	public void onStart() {

		super.onStart();

		mLocationClient.connect();

	}


	@Override
	public void onResume() {
		super.onResume();

		// If the app already has a setting for getting location updates, get it
		if (mPrefs.contains(LocationUtils.KEY_UPDATES_REQUESTED)) {
			mUpdatesRequested = mPrefs.getBoolean(
					LocationUtils.KEY_UPDATES_REQUESTED, false);

			// Otherwise, turn off location updates until requested
		} else {
			mEditor.putBoolean(LocationUtils.KEY_UPDATES_REQUESTED, false);
			mEditor.commit();
		}

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		// Choose what to do based on the request code
		switch (requestCode) {

		// If the request code matches the code sent in onConnectionFailed
		case LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST:

			switch (resultCode) {
			// If Google Play services resolved the problem
			case Activity.RESULT_OK:

				// Log the result
				Log.d(LocationUtils.APPTAG, getString(R.string.resolved));

				// Display the result
				mConnectionState.setText(R.string.connected);
				mConnectionStatus.setText(R.string.resolved);
				break;

			// If any other result was returned by Google Play services
			default:
				// Log the result
				Log.d(LocationUtils.APPTAG, getString(R.string.no_resolution));

				// Display the result
				mConnectionState.setText(R.string.disconnected);
				mConnectionStatus.setText(R.string.no_resolution);

				break;
			}

			// If any other request code was received
		default:
			// Report that this Activity received an unknown requestCode
			Log.d(LocationUtils.APPTAG,
					getString(R.string.unknown_activity_request_code,
							requestCode));

			break;
		}
	}

	
	private boolean servicesConnected() {

		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			Log.d(LocationUtils.APPTAG,
					getString(R.string.play_services_available));

			// Continue
			return true;
			// Google Play services was not available for some reason
		} else {
			// Display an error dialog
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode,
					this, 0);
			if (dialog != null) {
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				errorFragment.setDialog(dialog);
				errorFragment.show(getSupportFragmentManager(),
						LocationUtils.APPTAG);
			}
			return false;
		}
	}

	
	public void getLocation(View v) {

		// If Google Play Services is available
		if (servicesConnected()) {

			// Get the current location
			Location currentLocation = mLocationClient.getLastLocation();
			// 41.83834780, -87.63710470
			String curLoc = LocationUtils.getLatLng(this, currentLocation);
			String[] latl = curLoc.split(",");

			Double lat = 41.83834780;
			Double lon = -87.63710470;
			BigDecimal lat1 = new BigDecimal(latl[0].trim());
			BigDecimal log1 = new BigDecimal(latl[1].trim());
			BigDecimal lat2 = new BigDecimal(lat);
			BigDecimal log2 = new BigDecimal(lon);
			lat1 = lat1.round(new MathContext(2)).setScale(2,
					java.math.RoundingMode.CEILING);
			log1 = log1.round(new MathContext(2)).setScale(2,
					java.math.RoundingMode.CEILING);
			lat2 = lat2.round(new MathContext(2)).setScale(2,
					java.math.RoundingMode.CEILING);
			log2 = log2.round(new MathContext(2)).setScale(2,
					java.math.RoundingMode.CEILING);

			if (lat1.equals(lat2) && log1.equals(log2)) {
				System.out.println(lat1 + " Location verified" + log1);
			}

			// Display the current location in the UI
			mLatLng.setText(LocationUtils.getLatLng(this, currentLocation));
		}
	}


	// For Eclipse with ADT, suppress warnings about Geocoder.isPresent()
	@SuppressLint("NewApi")
	public void getAddress(View v) {

		// In Gingerbread and later, use Geocoder.isPresent() to see if a
		// geocoder is available.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
				&& !Geocoder.isPresent()) {
			// No geocoder is present. Issue an error message
			Toast.makeText(this, R.string.no_geocoder_available,
					Toast.LENGTH_LONG).show();
			return;
		}

		if (servicesConnected()) {

			// Get the current location
			Location currentLocation = mLocationClient.getLastLocation();

			// Turn the indefinite activity indicator on
			mActivityIndicator.setVisibility(View.VISIBLE);

			// Start the background task
			(new MainActivity.GetAddressTask(this)).execute(currentLocation);
		}
	}

	public void startUpdates(View v) {
		mUpdatesRequested = true;

		if (servicesConnected()) {
			startPeriodicUpdates();
		}
	}

	
	public void stopUpdates(View v) {
		mUpdatesRequested = false;

		if (servicesConnected()) {
			stopPeriodicUpdates();
		}
	}

	
	@Override
	public void onConnected(Bundle bundle) {
		mConnectionStatus.setText(R.string.connected);

		if (mUpdatesRequested) {
			startPeriodicUpdates();
		}
	}

	
	@Override
	public void onDisconnected() {
		mConnectionStatus.setText(R.string.disconnected);
	}

	
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

		if (connectionResult.hasResolution()) {
			try {

				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(this,
						LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */

			} catch (IntentSender.SendIntentException e) {

				// Log the error
				e.printStackTrace();
			}
		} else {

			// If no resolution is available, display a dialog to the user with
			// the error.
			showErrorDialog(connectionResult.getErrorCode());
		}
	}

	
	@Override
	public void onLocationChanged(Location location) {

		// Report to the UI that the location was updated
		mConnectionStatus.setText(R.string.location_updated);

		// In the UI, set the latitude and longitude to the value received
		mLatLng.setText(LocationUtils.getLatLng(this, location));
	}

	
	private void startPeriodicUpdates() {

		mLocationClient.requestLocationUpdates(mLocationRequest, this);
		mConnectionState.setText(R.string.location_requested);
	}

	
	private void stopPeriodicUpdates() {
		mLocationClient.removeLocationUpdates(this);
		mConnectionState.setText(R.string.location_updates_stopped);
	}

	
	protected class GetAddressTask extends AsyncTask<Location, Void, String> {

		// Store the context passed to the AsyncTask when the system
		// instantiates it.
		Context localContext;

		// Constructor called by the system to instantiate the task
		public GetAddressTask(Context context) {

			// Required by the semantics of AsyncTask
			super();

			// Set a Context for the background task
			localContext = context;
		}

		
		@Override
		protected String doInBackground(Location... params) {
			
			Geocoder geocoder = new Geocoder(localContext, Locale.getDefault());

			// Get the current location from the input parameter list
			Location location = params[0];

			// Create a list to contain the result address
			List<Address> addresses = null;

			// Try to get an address for the current location. Catch IO or
			// network problems.
			try {

				
				addresses = geocoder.getFromLocation(location.getLatitude(),
						location.getLongitude(), 1);

				// Catch network or other I/O problems.
			} catch (IOException exception1) {

				// Log an error and return an error message
				Log.e(LocationUtils.APPTAG,
						getString(R.string.IO_Exception_getFromLocation));

				// print the stack trace
				exception1.printStackTrace();

				// Return an error message
				return (getString(R.string.IO_Exception_getFromLocation));

				// Catch incorrect latitude or longitude values
			} catch (IllegalArgumentException exception2) {

				// Construct a message containing the invalid arguments
				String errorString = getString(
						R.string.illegal_argument_exception,
						location.getLatitude(), location.getLongitude());
				// Log the error and print the stack trace
				Log.e(LocationUtils.APPTAG, errorString);
				exception2.printStackTrace();

				//
				return errorString;
			}
			// If the reverse geocode returned an address
			if (addresses != null && addresses.size() > 0) {

				// Get the first address
				Address address = addresses.get(0);

				// Format the first line of address
				String addressText = getString(
						R.string.address_output_string,

						// If there's a street address, add it
						address.getMaxAddressLineIndex() > 0 ? address
								.getAddressLine(0) : "",

						// Locality is usually a city
						address.getLocality(),

						// The country of the address
						address.getCountryName());

				// Return the text
				return addressText;

				// If there aren't any addresses, post a message
			} else {
				return getString(R.string.no_address_found);
			}
		}

		
		@Override
		protected void onPostExecute(String address) {

			// Turn off the progress bar
			mActivityIndicator.setVisibility(View.GONE);

			// Set the address in the UI
			mAddress.setText(address);
		}
	}

	
	private void showErrorDialog(int errorCode) {

		// Get the error dialog from Google Play services
		Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode,
				this, LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

		// If Google Play services can provide an error dialog
		if (errorDialog != null) {

			// Create a new DialogFragment in which to show the error dialog
			ErrorDialogFragment errorFragment = new ErrorDialogFragment();

			// Set the dialog in the DialogFragment
			errorFragment.setDialog(errorDialog);

			// Show the error dialog in the DialogFragment
			errorFragment.show(getSupportFragmentManager(),
					LocationUtils.APPTAG);
		}
	}

	
	public static class ErrorDialogFragment extends DialogFragment {

		// Global field to contain the error dialog
		private Dialog mDialog;

	
		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}

		
		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}

		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}
	}
}
