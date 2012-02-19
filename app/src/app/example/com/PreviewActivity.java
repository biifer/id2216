package app.example.com;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PreviewActivity extends Activity {

	private LocationManager lm;
	private LocationListener locationListener;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.preview);

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
}
