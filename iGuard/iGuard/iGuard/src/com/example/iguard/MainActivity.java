package com.example.iguard;

import java.util.ArrayList;

//import com.example.iguard.ContactsMainActivity.MyAdapter;



import java.util.HashMap;

//import com.example.volumechange.MainActivity;
//import com.example.volumechange.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;



public class MainActivity extends Activity {
	private ImageButton emgButton,carButton, callMeBackButton, accidentButton;
	private Button contactsButton;
	private String textForToast;
	private String emgMessage = "I am in need of immediate help, please contact police and come for my aid.";
	private String carMessage = "My car broke down come to get help me.";
	private String cmbMessage = "Cought in a bad situation call be now to avoid this situation.";
	private String accidentMessage = "I met an accident, come and get me.";
	private int[] numbers;
	private AlertDialog.Builder build;
	private LocationManager currentLocation; 
	private static final int REQUEST_CODE = 0;
//-----------------------------------------------------------
	private ArrayList<String> conNames;
	private ArrayList<String> conNumbers;
	private Cursor crContacts;
	private ListView v;
	private ArrayList<String> numberList = new ArrayList<String>();
	private DatabaseManager dbManager;
//-------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        
        emgButton = (ImageButton) findViewById(R.id.emgButton);
		emgButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				build = new AlertDialog.Builder(MainActivity.this);
				build.setTitle("Emergency Message"); 
				build.setMessage("Do you want to Send this Message?");
				build.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
					//Send Emergency Message Method Call
								sendSMSEmergency();
								Toast.makeText(
										getApplicationContext(),
										"Your message has been Sent", 3000).show();
							      
								dialog.cancel();
							}
						});

				build.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
				AlertDialog alert = build.create();
				alert.show();
				
			}
		});
		
	       carButton = (ImageButton) findViewById(R.id.carButton);
			carButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					build = new AlertDialog.Builder(MainActivity.this);
					build.setTitle("Emergency Message"); 
					build.setMessage("Do you want to Send this Message?");
					build.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
					// Sending Car broke doen message
									sendSMSCar();

									Toast.makeText(
											getApplicationContext(),
											"Your message has been Sent", 3000).show();

								
									dialog.cancel();
								}
							});

					build.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							});
					AlertDialog alert = build.create();
					alert.show();
					
				}
			});
		       callMeBackButton = (ImageButton) findViewById(R.id.callMeBackButton);
				callMeBackButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						build = new AlertDialog.Builder(MainActivity.this);
						build.setTitle("Emergency Message"); 
						build.setMessage("Do you want to Send this Message?");
						build.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
						//Send Message Call me Back 
										sendSMSCallMeBack();

										Toast.makeText(
												getApplicationContext(),
												"Your message has been Sent", 3000).show();

									
										dialog.cancel();
									}
								});

						build.setNegativeButton("No",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
									}
								});
						AlertDialog alert = build.create();
						alert.show();
						
					}
				});

				       accidentButton = (ImageButton) findViewById(R.id.accidentButton);
						accidentButton.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								build = new AlertDialog.Builder(MainActivity.this);
								build.setTitle("Emergency Message"); 
								build.setMessage("Do you want to Send this Message?");
								build.setPositiveButton("Yes",
										new DialogInterface.OnClickListener() {

											public void onClick(DialogInterface dialog,
													int which) {
								// Send SMS Accident 
												sendSMSAcccident();
												

												Toast.makeText(
														getApplicationContext(),
														"Your message has been Sent", 3000).show();

											
												dialog.cancel();
											}
										});

								build.setNegativeButton("No",
										new DialogInterface.OnClickListener() {

											public void onClick(DialogInterface dialog,
													int which) {
												dialog.cancel();
											}
										});
								AlertDialog alert = build.create();
								alert.show();
								
							}
						});
		
 
    }
    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		MenuInflater imf = getMenuInflater();
		imf.inflate(R.menu.main, menu);
		return true;
	}
	  // Method for sending sms by volume key
	// Modify this method to send sms
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)){
	            //Do something
	        	//Toast.makeText(MainActivity.this, "Hello World", Toast.LENGTH_SHORT).show();
	        	 /*
	        	  * This method is used to send sms with volume up buttom
	        	  */
	        	        Log.i("Sending SMS", "");
	        	        dbManager = new DatabaseManager(MainActivity.this);
	        	        numberList = dbManager.numberList();       
	        	        String numbers[] = new String[numberList.size()];
	        	        numbers = numberList.toArray(numbers);
	        	        try {        	
	        	           SmsManager smsManager = SmsManager.getDefault();
	        	           for(String number : numbers) {
	        	        	   smsManager.sendTextMessage(number, null, emgMessage, null, null);
	        	        	 }
	        	           Toast.makeText(getApplicationContext(), "SMS sent.",
	        	           Toast.LENGTH_LONG).show();
	        	        } catch (Exception e) {
	        	           Toast.makeText(getApplicationContext(),
	        	           "SMS faild, please try again.",
	        	           Toast.LENGTH_LONG).show();
	        	           e.printStackTrace();
	        	        }
	        	     }
	        
	        return true;
	  
	   }
//Method for sending Emergency message.
    public void sendSMSEmergency() {
        Log.i("Sending SMS", "");
        dbManager = new DatabaseManager(MainActivity.this);
        numberList = dbManager.numberList();       
        String numbers[] = new String[numberList.size()];
        numbers = numberList.toArray(numbers);
        try {        	
           SmsManager smsManager = SmsManager.getDefault();
           for(String number : numbers) {
        	   smsManager.sendTextMessage(number, null, emgMessage, null, null);
        	 }
           Toast.makeText(getApplicationContext(), "SMS sent.",
           Toast.LENGTH_LONG).show();
        } catch (Exception e) {
           Toast.makeText(getApplicationContext(),
           "SMS faild, please try again.",
           Toast.LENGTH_LONG).show();
           e.printStackTrace();
        }
     }
    public void sendSMSCar() {
        Log.i("Sending SMS", "");
        dbManager = new DatabaseManager(MainActivity.this);
        numberList = dbManager.numberList();       
        String numbers[] = new String[numberList.size()];
        numbers = numberList.toArray(numbers);
        try {        	
           SmsManager smsManager = SmsManager.getDefault();
           for(String number : numbers) {
        	   smsManager.sendTextMessage(number, null, carMessage, null, null);
        	 }
           Toast.makeText(getApplicationContext(), "SMS sent.",
           Toast.LENGTH_LONG).show();
        } catch (Exception e) {
           Toast.makeText(getApplicationContext(),
           "SMS faild, please try again.",
           Toast.LENGTH_LONG).show();
           e.printStackTrace();
        }
     }
    public void sendSMSAcccident() {
        Log.i("Sending SMS", "");
        dbManager = new DatabaseManager(MainActivity.this);
        numberList = dbManager.numberList();        
        String numbers[] = new String[numberList.size()];
        numbers = numberList.toArray(numbers);
        try {        	
           SmsManager smsManager = SmsManager.getDefault();
           for(String number : numbers) {
        	   smsManager.sendTextMessage(number, null, accidentMessage, null, null);
        	 }
           Toast.makeText(getApplicationContext(), "SMS sent.",
           Toast.LENGTH_LONG).show();
        } catch (Exception e) {
           Toast.makeText(getApplicationContext(),
           "SMS faild, please try again.",
           Toast.LENGTH_LONG).show();
           e.printStackTrace();
        }
     }
    public void sendSMSCallMeBack() {
        Log.i("Sending SMS", "");
        dbManager = new DatabaseManager(MainActivity.this);
        numberList = dbManager.numberList();        
        String numbers[] = new String[numberList.size()];
        numbers = numberList.toArray(numbers);
        try {        	
           SmsManager smsManager = SmsManager.getDefault();
           for(String number : numbers) {
        	   smsManager.sendTextMessage(number, null, cmbMessage, null, null);
        	 }
           Toast.makeText(getApplicationContext(), "SMS sent.",
           Toast.LENGTH_LONG).show();
        } catch (Exception e) {
           Toast.makeText(getApplicationContext(),
           "SMS faild, please try again.",
           Toast.LENGTH_LONG).show();
           e.printStackTrace();
        }
     }
    public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.item1) {
			showContactList();
		
		} else if (item.getItemId() == R.id.item2) {
			MainActivity main = MainActivity.this;
			
			Intent intent = new Intent(MainActivity.this, DeleteContacts.class);
			startActivity(intent);
		}
		 else if (item.getItemId() == R.id.item3) {

			Intent intent = new Intent(MainActivity.this, AddContact.class);
			startActivity(intent);
			}
		return super.onOptionsItemSelected(item);
	}
//---------------------------------------------------------------------------------------------------------
    //Inner Adapter Class for List view of contacts
    
    
    private class MyAdapter extends ArrayAdapter<String> {

		public MyAdapter(Context context, int resource, int textViewResourceId,
				ArrayList<String> conNames) {
			super(context, resource, textViewResourceId, conNames);

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View row = setList(position, parent);
			return row;
		}

		private View setList(int position, ViewGroup parent) {
			LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View row = inf.inflate(R.layout.liststyle, parent, false);

			TextView tvName = (TextView) row.findViewById(R.id.tvNameMain);
			TextView tvNumber = (TextView) row.findViewById(R.id.tvNumberMain);

			tvName.setText(conNames.get(position));
			tvNumber.setText("No: " + conNumbers.get(position));

			return row;
		}
	}
   //----------------------------------------------------------
    //METHOD FOR DISPLAYING LIST OF CONTACTS
   public void showContactList()
   {
    
    setContentView(R.layout.main);
    v = (ListView)findViewById(R.id.list);

	conNames = new ArrayList<String>();
	conNumbers = new ArrayList<String>();
	
//CALL TO CLASS "DatabaseManager"
	dbManager = new DatabaseManager(MainActivity.this);

	crContacts = dbManager.retrieveContacts();
	crContacts.moveToFirst();

	while (!crContacts.isAfterLast()) {
		conNames.add(crContacts.getString(1));
		conNumbers.add(crContacts.getString(2));
		crContacts.moveToNext();
	}
//	setListAdapter(new MyAdapter(this, android.R.layout.simple_list_item_1,
//			R.id.tvNameMain, conNames));
	MyAdapter myADAPTER = new MyAdapter(this, android.R.layout.simple_list_item_1, R.id.tvNameMain, conNames);
	v.setAdapter(myADAPTER);
	
   }  

}
