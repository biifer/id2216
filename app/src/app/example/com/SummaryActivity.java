package app.example.com;

import com.google.android.maps.MapActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SummaryActivity extends MapActivity {

	public static final String PREFS_NAME = "PrefsFile"; //I filen sparar vi: time, averageSpeed, name, nrOfFLags, 
	
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	
	String name;
	int totalTime;
	int totalDistance;
	int averageSpeed;
	int numberOfFlags;
	
	
	public void saveToMyPrefs() {
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
	}
	
	public String loadStringFromMyPrefs() {
		String defValue = "fel";
		settings = getSharedPreferences(PREFS_NAME, 0);
		String hej = settings.getString("radius", defValue);
	return hej;
	}
	
	public int loadIntFromMyPrefs() {
		int defValue = -1;
		settings = getSharedPreferences(PREFS_NAME, 0);
		int hej = settings.getInt("radius", defValue);
	return hej;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.summary);
		Button menu = (Button)findViewById(R.id.menu_button);
        menu.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), AppActivity.class);
				v.getContext().startActivity(myIntent);
			}
		});
        
    	Button share = (Button)findViewById(R.id.share_button);
        share.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Context context = getApplicationContext();
				
				
				String i = loadStringFromMyPrefs();
				CharSequence text = i;
				int duration = Toast.LENGTH_LONG;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		});
	}
	
	public void setTotalTime (int t) {
		
	}
	
	public void setTotalDistance (int d) {
		
	}
	
	public void setAverageSpeed (int s) {
		
	}
	
	public void setNumberOfFlags (int n) {
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
