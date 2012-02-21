package app.example.com;

import java.nio.charset.Charset;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PreviewActivity extends MapActivity {

	
	private LocationManager lm;
	private LocationListener locationListener;
	LinearLayout linerarLayout;
	MapView mapView;
	
	public static final String PREFS_NAME = "PrefsFile"; //I filen sparar vi: time, averageSpeed, name, nrOfFLags, 

	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		Context myContext = this; 
	    switch (item.getItemId()) {
	        case R.id.about:
	        	 final Dialog dialog = new Dialog(myContext);
	                dialog.setContentView(R.layout.about);
	                dialog.setTitle("About");
	                dialog.setCancelable(true);

	                TextView text = (TextView) dialog.findViewById(R.id.Text);
	                text.setText("I belive I can fly v1.02");

	              
	                dialog.show();
				break;
	        case R.id.help:
	        	 final Dialog dialog2 = new Dialog(myContext);
	                dialog2.setContentView(R.layout.help);
	                dialog2.setTitle("Help");
	                dialog2.setCancelable(true);

	                text = (TextView) dialog2.findViewById(R.id.Text);
	                text.setText("Help? Look at the map! That's you...");

	                Button button = (Button) dialog2.findViewById(R.id.cancel);
	                button.setOnClickListener(new OnClickListener() {
	                	
	                public void onClick(View v) {
	                        dialog2.dismiss();
	                    }
	                });
	                dialog2.show();
	        default:
	        	break;
	    }
	    return false;
	}
	private void saveToMyPrefs() {
		//TODO lägga in alla parametrar som ska sparas till summary, koordinater av flaggor: hur lösa det?
		
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.preview);

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		Button start = (Button)findViewById(R.id.start_button);
        start.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), MainActivity.class);
				v.getContext().startActivity(myIntent);
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
