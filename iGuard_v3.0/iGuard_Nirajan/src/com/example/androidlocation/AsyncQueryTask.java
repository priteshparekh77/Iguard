package com.example.androidlocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

	/**
	 * Asynchronous class to download the places through Google Places API
	 * and parse the Result in JSON format
	 */
	
public class AsyncQueryTask extends AsyncTask<String, Integer, ArrayList<HashMap<String, String>>>{

	// Google Places API Key
		private static final String API_KEY = "AIzaSyBZmNuPQUohZ8_oTiBC7VeR0N_lsPh2Y5Q";
		private static final String SEARCH_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?";
		private static final String SEARCH_QUERY = "hospitals";
		
		private double longitude, latitude, radius;
		
		// URL for the places query
		String placesQuery= null;
		
		/**
		 * Method to set the URL to download the JSON data
		 */
		private String downloadUrl(String url) throws IOException{
			String resultData="";
			InputStream inputStream= null;
			HttpClient httpClient= new DefaultHttpClient();
			HttpResponse httpResponse;
			
			try {
				httpResponse= httpClient.execute(new HttpGet(url));
				Log.i("Praeda", httpResponse.getStatusLine().toString());
				
				HttpEntity entity = httpResponse.getEntity();
				if(entity !=null){
					inputStream= entity.getContent();
					
				if(inputStream!=null)
					resultData=convertInputStreamToString(inputStream);
				else
					resultData="HTTP Request Error";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("downloadUrl1", "error");
			} finally{
				inputStream.close();	// closing the InputStream
				Log.d("downloadUrl2", "error");
			}
			Log.d("result download", resultData.toString());
			return resultData;
		}
		
		/**
		 * Method to convert the JSON feed to String
		 */
		private static String convertInputStreamToString(InputStream inputStream) throws IOException{
			BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
			String line="";
			StringBuilder sb= new StringBuilder();
			try{
				while((line=bufferedReader.readLine())!=null){
					sb.append(line + "\n");
					System.out.println("Line " + line);	
					}
			} catch (IOException e){
				e.printStackTrace();
			} finally{
				try{
				inputStream.close();
				} catch (IOException e){
					e.printStackTrace();
				}
			}
			
			
			Log.d("result", sb.toString());
			return sb.toString();
		}		

		
		
		/**
		 *  Invoked by execute() method of Async object
		 *  
		 */
		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(String... jsonData) {
			// TODO Auto-generated method stub
		
			ArrayList<HashMap<String, String>> myPlaces=null;
			JSONObject jsonObj;
			// Instance of JSON Parser
			PlaceParser plParser= new PlaceParser();
			
			try{
				placesQuery=downloadUrl("https://maps.googleapis.com/maps/api/place/textsearch/json?location=40.0,-74.2&radius=500&query=hospitals&sensor=false&key=AIzaSyBZmNuPQUohZ8_oTiBC7VeR0N_lsPh2Y5Q");
				// parse the string to a JSON Object
				jsonObj= new JSONObject(placesQuery);
				myPlaces= plParser.parse(jsonObj);
				Log.d("myparser", "OK");
			} catch(Exception e){
				Log.d("Exception", "MyParser Background");
				}
		
			Log.d("parserlist", myPlaces.toString());
			return myPlaces;	// final return of an ArrayList of results
	}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			Log.d("Progress", Integer.toString(values[0]));
		}

		@Override
		public void onPostExecute(ArrayList<HashMap<String, String>> mList) {
			// TODO Auto-generated method stub
			Log.d("BackgroundWork", "Complete");
		}
		
		

}// end of AsyncQueryTask class