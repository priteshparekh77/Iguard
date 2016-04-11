package com.example.design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.androidlocation.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter{

	private List<HashMap<String, String>> mItems= new ArrayList<HashMap<String, String>>();
	private final Context mContext;
	
	
	
	public CustomListAdapter(Context context, List<HashMap<String, String>> items) {
		mContext=context;
		mItems=items;
	}

	// Add a ToDoItem to the adapter
	// Notify observers that the data set has changed
		public void add(HashMap<String, String> item) {
			mItems.add(item);
			notifyDataSetChanged();
		}

	// Clears the list adapter of all items.
		public void clear(){
			mItems.clear();
			notifyDataSetChanged();
		}

		// Returns the total number of Subscribers

		@Override
		public int getCount() {
			return mItems.size();
		}

		// Retrieve the Subscribers at a certain position
		@Override
		public Object getItem(int pos) {
			return mItems.get(pos);
		}

		// Get the ID for the Subscriber Object
		// In this case it's just the position
		@Override
		public long getItemId(int pos) {

			return pos;
		}

		// Update the List 
		public void updateItem(List<HashMap<String, String>> items){
			mItems=items;
			notifyDataSetChanged();
		}
		
		//Create a View to display each Subscriber 
		// at specified position in mItems
			@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.d("adapter view", "called");
			//Get the current ToDoItem
			final HashMap<String, String> item = mItems.get(position);
			
			//Inflate the View for this Subscriber item
			// from list_row.xml.
			
			LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			RelativeLayout itemLayout= (RelativeLayout) inflater.inflate(R.layout.list_item, null);
			
			
			// set the name of the subscriber 
			final TextView subName= (TextView) itemLayout.findViewById(R.id.name);
			subName.setText(item.get("name"));
			Log.d("name", item.get("name"));
			// set the address of the subscriber
			final TextView subAddress= (TextView) itemLayout.findViewById(R.id.address);
			subAddress.setText(item.get("address"));
			Log.d("address", item.get("address"));
			// Return the View created
			return itemLayout;
			
			
		}
}
