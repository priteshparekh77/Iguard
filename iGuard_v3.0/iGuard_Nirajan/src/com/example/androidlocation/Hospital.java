package com.example.androidlocation;

/**
 * Implementing Serializable, so that we can pass Hospital object
 * to another using Intent
 */
public class Hospital {
	
	private String name, icon, vicinity, reference, address, phone;
	
	
	public Hospital(String icon, String name, String vicinity, String address) {
		this.name=name; this.icon=icon; this.vicinity=vicinity; this.address=address;
		
	}

	public String toString(){
		return "Name: " + name +"\n"+ "Address: "+ address + "\n\n";
	}
	

}
