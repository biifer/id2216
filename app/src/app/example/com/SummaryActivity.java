package app.example.com;

import com.google.android.maps.MapActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SummaryActivity extends MapActivity {

	public static final String PREFS_NAME = "PrefsFile"; //I filen sparar vi: time, averageSpeed, name, nrOfFLags, distance, radius, nameOfUploader 
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	
	String name;
	int totalTime;
	int totalDistance;
	int averageSpeed;
	int numberOfFlags;
	
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
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.summary);	
		Bundle extras = getIntent().getExtras();
		long time = extras.getLong("time");
		long timer = SystemClock.elapsedRealtime() -time;
		long hours = (timer/3600000);
		long minutes=(timer/1000)/60;
		long seconds=(timer/1000)%60;
		TextView total = (TextView) findViewById(R.id.total_time);
		String m = null,s = null,h=null;

		if(minutes < 10 ) m = "0" + minutes;
		else  m = minutes + "";
		if(seconds < 10 ) s = "0" + seconds;
		else s = seconds + "";
		if(hours < 10) h = "0" + hours;
		else h = hours + "";
		if(hours == 0) total.setText("Total Time: " + m +":" + s);
		else total.setText("Total Time: " +h+ ":" + m +":" + s);
		
		String flags = loadStringFromMyPrefs("flags");
		TextView flag_text = (TextView) findViewById(R.id.No_of_flags);
		String buff[] = flags.split(" ");
		flag_text.setText("No of flags: " + buff[1]);
		
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
				
				String distance = loadStringFromMyPrefs("distance");
				String flags = loadStringFromMyPrefs("flags");
				String radius = loadStringFromMyPrefs("radius");
				String toastText = distance + "\n" + radius + "\n" + flags;
				CharSequence text = toastText ;
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
