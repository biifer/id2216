package app.example.com;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

public class DataBaseActivity extends Activity {
	DataBaseAdapter db;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        db = new DataBaseAdapter(this);
        System.out.println("bool1");
        insertRoute();
        displayAllRoutes();
        //update();
        //dis(3);
        //del(5);
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
        db.open();
        long id;
        id = db.insertRoute(
        		"ivanabbeaffe",
        		"marathon",
        		"43km", "sdf", "sddf", "dsfdsf", "dsfsdf");
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
