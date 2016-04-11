package com.example.iguard;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DatabaseManager {
     public static final String DB_NAME = "ContactData";
     public static final String DB_TABLE = "Contacts";
     public static final int DB_VERSION = 1;
     public static final String ID = "id";
     public static final String NAME = "name";
     public static final String NUMBER = "number";
     private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, number TEXT);";
     private Cursor recordSet;
     private SQLHelper helper;
     private SQLiteDatabase db;
     private Context context;

     public DatabaseManager(Context c){
         this.context = c;
         helper=new SQLHelper(c);
         this.db = helper.getWritableDatabase();
    }

    public DatabaseManager openReadable() throws android.database.SQLException {
        helper=new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this; 
    }
    public void close(){
        helper.close();
    }
    
  // ADDING CONTACT INTO THE THE CONTCT TABLE
    public boolean addRow(String n, String p){
        ContentValues newContact = new ContentValues();
        
        newContact.put("name", n); 
        newContact.put("number", p);
        try{db.insertOrThrow(DB_TABLE, null, newContact);}
        catch(Exception e) {
            Log.e("Error in inserting rows ", e.toString());
            e.printStackTrace();
            return false;           
        }
        db.close();
        return true;
    }
    
  // DELETING THE CONTACT FROM THE TABLE
    
    public boolean deleteRow(int c)
    {
    	try{
    	db.delete(DB_TABLE, "number"+ "=" + c, null);
    	}
    	catch (Exception e)
    	{
    		Log.e("Error in deleting the data", e.toString());
    		e.printStackTrace();
    		return false;
    		
    		
    	}
    	return true;
    	
    }
    //GETTING THE NUMBER COLUMN FOR SENDING SMS
    public ArrayList<String> numberList(){
        ArrayList<String> resultRows=new ArrayList<String>();
        String[] columns = new String[]{ "number"};
       
        try{
       	
       Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);

        //Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            resultRows.add(Integer.toString(cursor.getInt(0)));
            cursor.moveToNext();
        }  
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        }
        catch(Exception e)
        {
    		Log.e("Error in Selecting Data in row: ", e.toString());
    		e.printStackTrace();
    		
        }
        return resultRows;
    }

    public Cursor retrieveContacts(){
       
        String[] columns = new String[]{"id", "name", "number"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);

        return cursor;
    }
 
    

    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper(Context c){
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Products table","Upgrading database i.e. dropping table and recreating it");
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);  
        }
    }
}
