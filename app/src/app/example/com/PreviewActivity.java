package app.example.com;

import java.nio.charset.Charset;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PreviewActivity extends MapActivity {

	int averageSpeed;
	int time;
	private LocationManager lm;
	private LocationListener locationListener;
	LinearLayout linerarLayout;
	MapView mapView;
	
	public static final String PREFS_NAME = "PrefsFile";
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.preview);
		
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		Button start = (Button)findViewById(R.id.start_button);
        start.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View buttons = findViewById(R.id.relativeLayout2);
				buttons.setVisibility(RelativeLayout.GONE);
				View text = findViewById(R.id.linearLayout1);
				text.setVisibility(LinearLayout.VISIBLE);
				//Intent myIntent = new Intent(v.getContext(), PreviewActivity.class);
				//v.getContext().startActivity(myIntent);
			}
		});
		
        Button newRoute = (Button)findViewById(R.id.new_route_button);
        newRoute.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), AppActivity.class);
				v.getContext().startActivity(myIntent);
			}
		});
        
        Button checkPoint = (Button) findViewById(R.id.checkpoint);
        checkPoint.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				Context context = getApplicationContext();
				CharSequence text = "Checkpoint";
				int duration = Toast.LENGTH_LONG;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();		
			}
		});
        
        Button summary = (Button) findViewById(R.id.summary);
        summary.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putInt("time", time);
				editor.putInt("averageSpeed", averageSpeed);
				
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), SummaryActivity.class);
				v.getContext().startActivity(myIntent);
			}
		});
        
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		locationListener = new LocationListener() {

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub

			}

			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub

			}

			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub

			}
		};

		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				locationListener);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return true;
	}
}
