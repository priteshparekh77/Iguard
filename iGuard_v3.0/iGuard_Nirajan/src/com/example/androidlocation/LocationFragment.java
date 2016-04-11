package com.example.androidlocation;

import com.example.design.InternetConnection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



public class LocationFragment extends Fragment {

	private GoogleMap myMap;
	private View view;
	private Button locationButton;
	private TextView currentAddress;
	private LocationTracker gps;
	Marker pointer;
	
	private InternetConnection connection;
	private Boolean internetConnected=false;
	
	
	public LocationFragment(){
		
	}
	
	/**
	 * Inflate the Fragment's view 
	 * add to the Parent Activity's view 	
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		if(container==null)
			return null;
		
		if(view !=null){
			ViewGroup parent= (ViewGroup) view.getParent();
			if(parent!=null)
				parent.removeView(view);
			}
		
		try{
			view= (ViewGroup) inflater.inflate(R.layout.activity_location, null, false);
			//view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				//	ViewGroup.LayoutParams.MATCH_PARENT));
			
			// set up the map in the Fragment's view
			setUpMap();
			
			locationButton= (Button) view.findViewById(R.id.btnShowLocation);
			
			locationButton.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					Log.d("button", "onclick");
					// Check for Internet Connectivity
					connection = new InternetConnection(getActivity());
					internetConnected= connection.isConnectingToInternet();
					
					if (!internetConnected) {
			            // Internet Connection is not present
			            connection.showInternetAlert(getActivity());
			            // stop executing code by return
			            return;
			        }
					addMarker();	// insert Marker with current address on the map
				//	new AsyncQueryDetailTask(){
					//}.execute();
					
					
					
				}
				
			});
			
			
		} catch (Exception e){
			e.printStackTrace();
		}
		        
		return view;
	}
	
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		Log.d("LocFragment onAttach", "Called");
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("LocationFragmentonCreate", "Called");
				
	}

	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("LocFragmentonActivityCreated", "Called");

    }

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume(){
		super.onResume();
		Log.d("LocFragmentApp", "Resumed");
	}
	
	
	/**
	 * To verify map is not already instantiated
	 * if not, set up the Google Map
	 */
	private void setUpMap(){
		// do a null check 
		if(myMap==null){
			Log.d("Map", "Not Instantiated");
			myMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			
			// check if it was successful in obtaining the map
			if(myMap!=null){
				Log.d("Map", "Is Now Instantiated");
				myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			}
		}
	}
	
	
	
	/**
	 * Method to add marker on the Google Map displaying the current address
	 */
	protected void addMarker(){
		gps= new LocationTracker(getActivity());
		if(gps.canGetLocation()){
			// retrieve current latitude and longitude
			double latitude= gps.getLatitude();
			double longitude= gps.getLongitude();
			//Address addressObject= gps.getAddress(latitude, longitude);
			String address= gps.getAddress(latitude, longitude);
	
			currentAddress=(TextView) view.findViewById(R.id.street);
			currentAddress.setText("Current Address: "+address);
			
			
			// location of the user
			final LatLng CIU = new LatLng(latitude,longitude);  
		
			// if a marker already exists, remove it
			if(pointer!=null)
				pointer.remove();
			
			//adding a marker to the current location and setting different properties for the marker
				pointer = myMap.addMarker(new MarkerOptions()                                   
				.position(CIU).title(address).draggable(true));	
			
				// move the camera to the current location
			myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CIU,5));
			
			// zoom in, animating the camera
			//myMap.animateCamera(CameraUpdateFactory.zoomIn());
			
			//myMap.animateCamera(CameraUpdateFactory.zoomTo(10),2000,null);
						
			}
	}
	
}	// end of LocationActivity Class