package com.example.design;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
	
	/**
	 * final class to check for Internet Connectivity
	 */
	  
	public final class InternetConnection {
	  
	    private Context _context;
	  
	    public InternetConnection(Context context){
	        this._context = context;
	    }
	  
	    /**
	     * Checking for all possible Internet providers
	     * **/
	    public boolean isConnectingToInternet(){
	        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
	          if (connectivity != null)
	          {
	              NetworkInfo[] info = connectivity.getAllNetworkInfo();
	              if (info != null)
	                  for (int i = 0; i < info.length; i++)
	                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
	                      {
	                          return true;
	                      }
	  
	          }
	          return false;
	    }
	    
	    /**
	     * Function to show settings alert dialog if Location Providers are disabled
	     * On pressing Settings button will launch Settings Options
	     * */    
	    public void showInternetAlert(Context context){
	    	AlertDialog.Builder alertDialog= new AlertDialog.Builder(context);
	    	// Setting Dialog Title
	    	alertDialog.setTitle("Internet Services Disabled");
	    	
	    	// Setting Dialog message
	    	alertDialog.setMessage("Please Check Your Internet Service Connection");
	    	
	    	// On pressing Settings Button
	    	alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){
	    		public void onClick(DialogInterface dialog, int which){
	    			
	    		}
	    	});
	    	alertDialog.show();
	    }
	    
	}
