package com.example.androidlocation;

import java.util.ArrayList;
import java.util.HashMap;
import com.example.androidlocation.R;
import com.example.design.CustomListAdapter;
import com.example.design.InternetConnection;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class HospitalFragment extends Fragment{
	
	private View view;
	private Button getHospitalButton;
	private TextView hospitals;
	private InternetConnection connection;
	private Boolean internetConnected=false;
	
	private ListView myList;
	private CustomListAdapter myAdapter;
	private ArrayList<HashMap<String, String>> hospitalList;
	
	public HospitalFragment(){
		
		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
				
		if(container==null)
			return null;
		
		if(view !=null){
			ViewGroup parent= (ViewGroup) view.getParent();
			if(parent!=null)
				parent.removeView(view);
			}
		
			try {
				view= (ViewGroup) inflater.inflate(R.layout.activity_hospital_fragment, null, false);
				
		       // myList= (ListView) getActivity().findViewById(R.id.list_view);
		        //myAdapter= new CustomListAdapter(getActivity(), hospitalList);
				//myList.setAdapter(myAdapter);
		        
				hospitals= (TextView) view.findViewById(R.id.texts);
				hospitals.setMovementMethod(new ScrollingMovementMethod());
				
				
				getHospitalButton= (Button) view.findViewById(R.id.btnShowHospitals);
				//hospitalList= new ArrayList<HashMap<String, String>>();
				

				getHospitalButton.setOnClickListener(new View.OnClickListener(){
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Log.d("Hospital Button", "Clicked");
						//hospitalList= new ArrayList<HashMap<String, String>>();
						// Check for Internet Connectivity
						connection = new InternetConnection(getActivity());
						internetConnected= connection.isConnectingToInternet();
						
						if (!internetConnected) {
				            // Internet Connection is not present
				            connection.showInternetAlert(getActivity());
				            // stop executing code by return
				            return;
				        }
						
						/** Asynchronous Task to download the list of hospitals **/
						new AsyncQueryTask(){
							
							@Override
							public void onPostExecute(ArrayList<HashMap<String, String>> mList){
								StringBuilder result=new StringBuilder();
								for(int i=0; i<mList.size(); i++){
									HashMap<String, String> a= mList.get(i);
									//hospitalList.add(a);
									
									Log.d("Checking", a.toString());
									result.append(createHospitalItem(a));
									
								}
								hospitals.setText(result.toString());
								
							}
							
						}.execute();
						
						
					}
					
				});
				
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
			
				Log.d("Hospital View", "Error");
			}
	
			
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("HospitalFragment", "onCreate");
		
	}
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		Log.d("HospitalFragmentonAttach", "Called");
		
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("HospitalFragmentonActivityCreated", "Called");
        
     
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
		Log.d("HospitalFragment", "Resumed");
	}
	
	/**
	 * create a Hospital object and return it
	 */
	
	private static String createHospitalItem(HashMap<String, String> list){
		String name="";
		String icon="";
		String vicinity="";
		String lat="";
		String lng="";
		String address="";
		
		
		name= list.get("name");
		Log.d("name", name);
		icon= list.get("icon");
		Log.d("icon", icon);
		vicinity= list.get("vicinity");
		lat= list.get("lat");
		Log.d("lat", lat);
		lng= list.get("lng");
		Log.d("lng", lng);
		address= list.get("address");
		
		
		Hospital item= new Hospital(icon, name, vicinity, address); 
		return item.toString();
	}
	
}
