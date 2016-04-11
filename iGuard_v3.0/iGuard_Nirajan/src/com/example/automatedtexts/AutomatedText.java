package com.example.automatedtexts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidlocation.R;

public class AutomatedText extends Fragment{

	View view;
	TextView header;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(container==null)
			return null;
		
		if(view !=null){
			ViewGroup parent= (ViewGroup) view.getParent();
			if(parent!=null)
				parent.removeView(view);
			}
		view= inflater.inflate(R.layout.activity_automated_text, container, false);
		header=(TextView) view.findViewById(R.id.header);
		header.setText("Send Automatic Messages!");
		
		
		return view;
	}
	
}
