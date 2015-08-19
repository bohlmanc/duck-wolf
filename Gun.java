
// Created by Theballzman. All rights reserved.

public class Gun {

	private String manufacturer,model,serial,caliber,acquired,notes,type;

	private int ID;

	public Gun(String m, String mo, String s, String c, String a, String n, String gtype,int id) {
		manufacturer = m;
		model = mo;
		serial = s;
		acquired = a;
		caliber = c;
		notes = n;
		type=gtype;
		ID = id;
	}

	public Gun() {
		manufacturer = "";
		model = "";
		serial = "";
		acquired = "";
		caliber = "";
		notes = "";
		ID = -1;
	}

	public String getType() {
		return type;
	}

	public void setType(String t) {
		type = t;
	}

	public String getManufacturer() {
		return manufacturer;
	}
	
	public String getModel() {
		return model;
	}

	public String getSerial() {
		return serial;
	}

	public String getCaliber() {
		return caliber;
	}

	public String getAcquired() {
		return acquired;
	}

	public String getNotes() {
		return notes;
	}

	public int getID() {
		return ID;
	}

	public String getLabel() {
		String outputString = "<html><p>"+type+"<br>"+manufacturer+" : "+model+"<br>"+"Caliber: "+caliber+"<br>"+"Serial #: "+serial+"<br>"+"Date Acquired: " + acquired + "<br>"+"Notes: "+notes;
		return outputString;
	}

	public String toString() {
		String outputString = "";
		outputString += type + " - " + manufacturer + ": " + model + "\nSerial #: " + serial + " Caliber: " + caliber + "\nDate Acquired: " + acquired + "\nNotes: " + notes;
		return outputString;
	}
}