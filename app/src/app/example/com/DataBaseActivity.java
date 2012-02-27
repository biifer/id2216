package app.example.com;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

public class DataBaseActivity extends Activity {
	DataBaseAdapter db;
    
	public static final String PREFS_NAME = "PrefsFile";
	SharedPreferences settings;
	SharedPreferences.Editor editor;

	private void saveIntToMyPrefs(String key, int value) {
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	private int loadIntFromMyPrefs(String key) {
		int defValue = -1;
		settings = getSharedPreferences(PREFS_NAME, 0);
		int value = settings.getInt(key, defValue);
		return value;
	}

	private void saveStringToMyPrefs(String key, String value) {
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private String loadStringFromMyPrefs(String key) {
		String defValue = "-1";
		settings = getSharedPreferences(PREFS_NAME, 0);
		String value = settings.getString(key, defValue);
		return value;
	}

	private void saveBooleanToMyPrefs(String key, Boolean value) {
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	private boolean loadBooleanFromMyPrefs(String key) {
		boolean defValue = false;
		settings = getSharedPreferences(PREFS_NAME, 0);
		boolean value = settings.getBoolean(key, defValue);
		return value;
	}	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        db = new DataBaseAdapter(this);
        System.out.println("bool1");
        insertRoute();
        displayAllRoutes();
        
    }

    private void updateRoute(){ 

        db.open();
        if (db.updateRoute(1,
        		"arne",
        		"springaheladagen",
        		"15km", "bla", "bla", "bla", "bla"))
            Toast.makeText(this, "Update successful.",
                Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Update failed.",
                Toast.LENGTH_LONG).show();
        //-------------------

        //Hämta samma route för att bekräfta
        Cursor c = db.getRoute(1);
        if (c.moveToFirst())
            displayRouteParam(c);
        else
            Toast.makeText(this, "No route found",
            		Toast.LENGTH_LONG).show();
        //-------------------
        db.close();

    }
    private void insertRoute(){ 
    	//Här ska vi hämta infon från "PrefsFile" och slänga in den i databasen TODOTODOTODO
        
    	String uploader = loadStringFromMyPrefs("uploader");
    	String routeName = loadStringFromMyPrefs("routename");
    	String distance = loadStringFromMyPrefs("distance");
    	String radius = loadStringFromMyPrefs("radius");
    	String flags = loadStringFromMyPrefs("flags");
    	String totalTime = loadStringFromMyPrefs("totaltime");
    	String averageSpeed = loadStringFromMyPrefs("averagespeed");
    	
    	db.open();
        long id;
        id = db.insertRoute(
        		uploader,
        		routeName,
        		distance, radius, flags, totalTime, averageSpeed);
        id = db.insertRoute(
        		"kthkarin",
        		"jogging",
        		"1km", "ewrewr", "ewrwegfd", "sdf", "lol");
        System.out.println("bool211");
        db.close();

    }

    private void displayAllRoutes(){

        db.open();
        /*Cursor c = db.getAllTitles();
        if (c.moveToFirst())
        {
            do {
            	System.out.println("bool2");
                DisplayTitle(c);
            } while (c.moveToNext());
        }
        else
        	System.out.println("boo3l");*/
        try{
        	Cursor c = db.getAllRoutes();
            if (c.moveToFirst())
            {
                do {
                	System.out.println("bool2");
                    displayRouteParam(c);
                } while (c.moveToNext());
            }
        }catch(Exception e){
        	System.out.println(e);
        }
        db.close();
    }

    private void displayRoute(int j){

        db.open();
        Cursor c = db.getRoute(j);
        if (c.moveToFirst())
            displayRouteParam(c);
        else
            Toast.makeText(this, "No route found",
            		Toast.LENGTH_LONG).show();
        db.close();
    }        

     private void deleteRoute(int j){

        db.open();
        if (db.deleteRoute(j))
            Toast.makeText(this, "Delete successful.",
                Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Delete failed.",
                Toast.LENGTH_LONG).show();
        db.close();

    }
     
    public void displayRouteParam(Cursor c)
    {
    	System.out.println("bool");
        Toast.makeText(this,
                "id:            " + c.getString(0) + "\n" +
                "Uploader:      " + c.getString(1) + "\n" +
                "Route_name:    " + c.getString(2) + "\n" +
                "Distance:      " + c.getString(3) + "\n" +
                "Radius:        " + c.getString(4) + "\n" +
                "Flags          " + c.getString(5) + "\n" +
                "Total_time     " + c.getString(6) + "\n" +
                "Average_speed: " + c.getString(7),
                Toast.LENGTH_LONG).show();
    }
}
