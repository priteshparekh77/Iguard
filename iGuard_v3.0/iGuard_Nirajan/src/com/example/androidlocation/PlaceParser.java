package com.example.androidlocation;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class PlaceParser{
	
	private static final String TAG_RESULTS="results";
		
	/**
	 * Method receives a JSONObject and returns a List
	 */
	public ArrayList<HashMap<String, String>> parse(JSONObject jObj){
		JSONArray places= null;
		try{
			places= jObj.getJSONArray(TAG_RESULTS);
			
		}catch (JSONException e){
			e.printStackTrace();
			Log.d("PlaceParser", "Error");
		}
		
		return getPlaces(places);
	
	}	// end of parse(JSONObject jObj) method
	
	
	/**
	 * Method to parse a JSONArray and return a List 
	 */
	private ArrayList<HashMap<String, String>> getPlaces(JSONArray input){
		int count= input.length();
		ArrayList<HashMap<String, String>> placesList= new ArrayList<HashMap<String, String>>();
		/** final ArrayList result of the Search Result **/ 
		HashMap<String, String> place=null;
		
		// Iterating through each place/item in the JSON array, and adding to the List
		for(int i=0; i<count; i++){
			try{
				place= getPlace((JSONObject)input.get(i));
				placesList.add(place);
			} catch(JSONException e){
				e.printStackTrace();
				Log.d("PlaceParser", "getPlaces");
			}
		}
		return placesList;
		
	}	// end of getPlaces(JSONArray input) method
	
	
	/**
	 *  Method to parse a JSON Object and returns a HashMap
	 */
	private HashMap<String, String> getPlace(JSONObject jsonFeed){
		
		String name= "N/A";
		String vicinity="N/A";
		String address="N/A";
		String icon="";
		String lat="";
		String lng="";
		String reference="";
		
		HashMap<String, String> places= new HashMap<String, String>();
		
		try{
			
			// getting the name of the place
			if(!jsonFeed.isNull("name"))
				name=jsonFeed.getString("name");
			// getting the icon
			if(!jsonFeed.isNull("icon"))
				icon=jsonFeed.getString("icon");
			// getting the address
			if(!jsonFeed.isNull("formatted_address"))
				address=jsonFeed.getString("formatted_address");
			// getting the vicinity if available
			if(!jsonFeed.isNull("vicinity"))
				vicinity= jsonFeed.getString("vicinity");
			
			
			
			// location is given as another JSON object
			lat= jsonFeed.getJSONObject("geometry").getJSONObject("location").getString("lat");
			lng= jsonFeed.getJSONObject("geometry").getJSONObject("location").getString("lng");
			reference= jsonFeed.getString("reference");
			
			places.put("name", name);
			places.put("icon", icon);
			places.put("address", address);
			places.put("vicinity", vicinity);
			places.put("lat", lat);
			places.put("lng", lng);
			places.put("reference", reference);
					
		} catch (JSONException e){
			e.printStackTrace();
			Log.d("PlaceParser", "getPlace()");
		}
		
		return places;
		
	}	// end of getPlace(JSONObject jsonFeed) method
	
	
	
	
}