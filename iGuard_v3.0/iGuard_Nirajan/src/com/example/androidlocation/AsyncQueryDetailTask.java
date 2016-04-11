package com.example.androidlocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

public class AsyncQueryDetailTask extends AsyncTask<String, Integer, HashMap<String, String>> {

	// Google Places API Key
			private static final String API_KEY = "AIzaSyBZmNuPQUohZ8_oTiBC7VeR0N_lsPh2Y5Q";
			private static final String SEARCH_URL = "https://maps.googleapis.com/maps/api/place/details/json?";
			private static final String SEARCH_QUERY = "hospitals";
	
			private String detailQuery=null;
			WebView mWvPlaceDetails;
			
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
			
	
			
	@Override
	protected HashMap<String, String> doInBackground(String... ref) {
		HashMap<String, String> detailPlaces=null;
		JSONObject jsonObj;
		String reference= ref[0];
		// Instance of Place Detail JSON Parser
		PlaceDetailParser plParser= new PlaceDetailParser();
		
		
		try{
		
			StringBuilder url= new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?sensor=false&key=AIzaSyBZmNuPQUohZ8_oTiBC7VeR0N_lsPh2Y5Q");
			url.append("reference="+reference);
			// query to get the detail of one place
			String query= "https://maps.googleapis.com/maps/api/place/details/json?reference=CqQBlgAAAHIYEQ_BrzN7INQ6dClQqmAEUiiaBW8Gu7BafJgzWpXvhiu2FObdaynRJSg6saja0oDjb8h360prXRlC28QkgrAue3XmLM9bnRsFJVno5SkBbqWY96sp7afu0dMQjWDiWSjFrSbnL9c3i6F7sKA11goy4CWSPKbqcOq9Y2FMhjgO9sA1G9J3YXG7BzYwt9j6_dwRpu-MX0W-l2mVZlLkmzkSEJZZDN73uVlwztq9Kb8t5LYaFMh0ooRJlbVgxQaECJGx0i78XMhn&sensor=false&key=AIzaSyBZmNuPQUohZ8_oTiBC7VeR0N_lsPh2Y5Q";
			detailQuery=downloadUrl(query);
			
			// parse the string to a JSON Object
			jsonObj= new JSONObject(detailQuery);
			detailPlaces= plParser.parse(jsonObj);
			Log.d("myparser", "OK");
		} catch(Exception e){
			Log.d("Exception", "MyParser Background");
			}
	
		Log.d("parserlist", detailPlaces.toString());
		return detailPlaces;	// final detail return of the place 
			
		
	}
	
	/** Executed after the complete execution of doInBackground() method **/
    @Override
    protected void onPostExecute(HashMap<String,String> hPlaceDetails){

        String name = hPlaceDetails.get("name");
        String icon = hPlaceDetails.get("icon");
        String vicinity = hPlaceDetails.get("vicinity");
        String lat = hPlaceDetails.get("lat");
        String lng = hPlaceDetails.get("lng");
        String formatted_address = hPlaceDetails.get("formatted_address");
        String formatted_phone = hPlaceDetails.get("formatted_phone");
        String website = hPlaceDetails.get("website");
        String rating = hPlaceDetails.get("rating");
        String international_phone_number = hPlaceDetails.get("international_phone_number");
        String url = hPlaceDetails.get("url");

        String mimeType = "text/html";
        String encoding = "utf-8";

        String data = "<html>"+
                      "<body><img style='float:left' src="+icon+" /><h1><center>"+name+"</center></h1>" +
                      "<br style='clear:both' />" +
                      "<hr />"+
                      "<p>Vicinity : " + vicinity + "</p>" +
                      "<p>Location : " + lat + "," + lng + "</p>" +
                      "<p>Address : " + formatted_address + "</p>" +
                      "<p>Phone : " + formatted_phone + "</p>" +
                      "<p>Website : " + website + "</p>" +
                      "<p>Rating : " + rating + "</p>" +
                      "<p>International Phone : " + international_phone_number + "</p>" +
                      "<p>URL : <a href='" + url + "'>" + url + "</p>" +
                      "</body></html>";

        // Setting the data in WebView
        Log.d("Detail", data);
      //  mWvPlaceDetails.loadDataWithBaseURL("", data, mimeType, encoding, "");
    }

	

}



