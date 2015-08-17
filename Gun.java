

public class Gun {

	private String manufacturer,model,serial,caliber,acquired,notes;

	private int ID;

	public Gun(String m, String mo, String s, String c, String a, String n, int id) {
		manufacturer = m;
		model = mo;
		serial = s;
		acquired = a;
		caliber = c;
		notes = n;
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

	public String toString() {
		String outputString = "";
		outputString += manufacturer + ": " + model + "\nSerial #: " + serial + " Caliber: " + caliber + "\nDate Acquired: " + acquired + "\nNotes: " + notes;
		return outputString;
	}
}