package com.example.androidlocation;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;



public class LocationTracker extends Service implements LocationListener{

	private final Context mContext;
	
	// flag for GPS status
	private static boolean isGPSEnabled=false;
	
	// flag for network status
	private static boolean isNetworkEnabled=false;
	
	// flag for ability to retrieve location
	boolean canGetLocation=false;
	
	// current location
	private Location location;
	private double latitude, longitude;
		
	// minimum distance in meters to request location update
	private static final long MIN_DISTANCE= 10;
	// minimum time in milliseconds to request location update
	private static final long MIN_TIME=100*60*1;
	
	// Declaring a Location Manager
	protected LocationManager locationManager;
	
	
	
	/**
	 * Default Constructor for GPSTracker
	 * @param takes a Context as a parameter
	 */
	public LocationTracker(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext=context;
		getLocation();
		}

	 /**
     * Function to check GPS/Network service enabled
     * @return boolean
     * */
	 public boolean canGetLocation() {
				return this.canGetLocation;
				}
    
    
	/**
	 * method to retrieve the current location
	 * uses Network service or GPS
	 */
		private void getLocation() {
			// TODO Auto-generated method stub
			try{
				// obtain reference to the LocationManager using the SystemService
			locationManager= (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
			
			// retrieve GPS status
			isGPSEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			// log message for GPS status
			if(isGPSEnabled)
				Log.d("GPS Status", "Enabled");
			
			// retrieve network status
			isNetworkEnabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			// Log message for network status
			if(isNetworkEnabled)
				Log.d("Network Status", "Enabled");
			
			// both GPS and Network unavailable, set canGetLocation value as false and prompt user to turn on Location Services
			if(!isGPSEnabled && !isNetworkEnabled){
				this.canGetLocation=false;
				showSettingsAlert();
				Log.d("GPS & Network", "UNAVAILABLE");
			
			}
			// either GPS or Network available, get current location
			else{
				this.canGetLocation = true;
				
				/**
				 *  Retrieve location from Network Provider if enabled ***
				 */
				if(isNetworkEnabled){
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_DISTANCE,MIN_TIME, this);
					Log.d("NETWORK", "USED");
				
					if(locationManager!=null){
						location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if(location!=null){
							latitude=location.getLatitude();
							longitude=location.getLongitude();
							}
						}
					}// end of isNetworkEnabled
				
				/**
				 *  Retrieve location from GPS if enabled ***
				 */
				if(isGPSEnabled){
					// if no previous location
						locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
						Log.d("GPS", "USED");
						
						// retrieve previous location
						if(locationManager!=null){
							location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							
							if(location!=null){
								latitude=location.getLatitude();
								longitude=location.getLongitude();
								}
							}
						
					}// end of isGPSEnabled
			
				}// end of !isGPSEnabled & !isNetworkEnabled 
			
			} catch(Exception e){
				Log.d("getLocation() Exception", "Exception for getLocation() method ");
				e.printStackTrace();
		}
		
	//	return location;		// return the current location
	}

	

	/**
	 * Unimplemented methods of LocationListener
	 */
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Log.d("Location: ", "Changed");
		// provide updated location parameters
		latitude= location.getLatitude();
		longitude= location.getLongitude();
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Log.d("Provider: ", "Disabled");
		/** Throwing NullPointerException
		 * for content.ContextWrapper.getApplicationContext
		 *	Toast.makeText(getApplicationContext(), "GPS Disabled", Toast.LENGTH_LONG).show();	
		*/
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Log.d("Provider: ", "Enabled");
		/** Throwing NullPointerException
		 * for content.ContextWrapper.getApplicationContext
		Toast.makeText(getApplicationContext(), "GPS Enabled", Toast.LENGTH_LONG).show();
		*/
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		Log.d("Provider", "Turned OFF");
			
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
	/**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(LocationTracker.this);
        }       
    }
    
   
    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
         
        // return latitude
        return latitude;
    }
     
    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
         
        // return longitude
        return longitude;
    }
    
    /**
     * Get a list of possible addresses and return the first address
     * @return 
     */
    protected Address getAdd(double lat, double lng){
    
    Address result = null;
    Geocoder geo= new Geocoder(mContext, Locale.ENGLISH);
    List<Address> addresses=null;
    try{
		// returns an array of known addresses surrounding the given latitude & longitude
		// last parameter defines how many results to expect
		// returns 1 possible street address
		
		addresses= geo.getFromLocation(lat, lng, 1);
		/* adding try and catch for Locale in Address NullPointerException */
		if(addresses != null && addresses.size()>0){
			result= addresses.get(0);
		}
		
    } catch (IOException e1){
			e1.printStackTrace();
			Log.d("GeoCoder", "IOException");
		} catch (IllegalArgumentException e2){
			e2.printStackTrace();
			Log.d("GeoCoder", "IllegalArgumentException");
			}
    return result;
    }
    
    /**
	 * Method to convert the current location to a Street Address
	 * @param longitude and latitude of the location
	 */
	protected String getAddress(double lat, double lng){
		String resultAddress;
		Geocoder geocoder= new Geocoder(mContext, Locale.ENGLISH);
		List<Address> addresses=null;
		
		try{
	// returns an array of known addresses surrounding the given latitude & longitude
	// last parameter defines how many results to expect
	// returns 1 possible street address
	
			addresses= geocoder.getFromLocation(lat, lng, 1);
			/* adding try and catch for Locale in Address NullPointerException */
			if(addresses != null && addresses.size()>0){
			// retrieve the first address
				Address returnedAddress= addresses.get(0);
				
			// Format the address, add city, country and postal code if available
				
			resultAddress= String.format("%s, %s, %s, %s",
			returnedAddress.getMaxAddressLineIndex()>0? returnedAddress.getAddressLine(0) :"",
							returnedAddress.getLocality(),
							returnedAddress.getCountryName(),
							returnedAddress.getPostalCode());
		
		} else {
			resultAddress= "No Address Match!";
		}
	
		} catch (IOException e1){
			e1.printStackTrace();
			resultAddress="No Address Match!";
			Log.d("GeoCoder", "IOException");
		} catch (IllegalArgumentException e2){
			e2.printStackTrace();
			resultAddress="No Address Match!";
			Log.d("GeoCoder", "IllegalArgumentException");
		}
		Log.d("address", resultAddress);
		return resultAddress;	
	
	} // end of the getAddress() method		
		
	
	/**
	 * Method to get the street address only
	 */
	protected String getStreetAddress(double lat, double lng){
		Address a= getAdd(lat, lng);
		return a.getAddressLine(0);
	}
	
	/**
	 * Method to get the City of the current location
	 */
	protected String getCity(Address address){
		return address.getLocality();
	}
	
	/**
	 * Method to get the Country
	 */
	protected String getCountry(Address address){
		return address.getCountryName();
	}
	
	/**
	 * Method to get the Zip Code
	 */
	protected String getZip(Address address){
		return address.getPostalCode();
	}
	
	
    /**
     * Function to show settings alert dialog if Location Providers are disabled
     * On pressing Settings button will launch Settings Options
     * */    
    public void showSettingsAlert(){
    	AlertDialog.Builder alertDialog= new AlertDialog.Builder(mContext);
    	// Setting Dialog Title
    	alertDialog.setTitle("Location Services Disabled");
    	
    	// Setting Dialog message
    	alertDialog.setMessage("Turn On Your Location Service Providers");
    	
    	// On pressing Settings Button
    	alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener(){
    		public void onClick(DialogInterface dialog, int which){
    			// start an implicit intent
    			Intent intent= new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    			mContext.startActivity(intent);
    			
    		}
    	});
    	
    	// On pressing the cancel button
    	alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
    	
    	// Showing Alert message
    	alertDialog.show();
    	
    }		// end of showSettingsAlert()
    
    
}	// end of LocationTracker

