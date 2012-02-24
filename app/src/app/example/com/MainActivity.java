package app.example.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends MapActivity {

	private LocationManager lManager;
	private LocationListener locationListener;
	int averageSpeed;
	int time;
	public static final String PREFS_NAME = "PrefsFile"; 
	
	LinearLayout linerarLayout;
	MapView mapView;
	MapController mController;
	MyLocationOverlay myLocationOverlay;
	GeoPoint p;
	List<Overlay> mapOverlays;
	ArrayList<GeoPoint> points = new ArrayList<GeoPoint>();

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu2, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Context myContext = this;
		switch (item.getItemId()) {
		case R.id.about:
			final Dialog aboutDialog = new Dialog(myContext);
			aboutDialog.setContentView(R.layout.about);
			aboutDialog.setTitle("About");
			aboutDialog.setCancelable(true);

			TextView text = (TextView) aboutDialog.findViewById(R.id.aboutText);
			text.setText("I belive I can fly v1.02");
			aboutDialog.show();
			break;
		case R.id.help:
			final Dialog helpDialog = new Dialog(myContext);
			helpDialog.setContentView(R.layout.help);
			helpDialog.setTitle("Help");
			helpDialog.setCancelable(true);

			text = (TextView) helpDialog.findViewById(R.id.Text);
			/*
			 * "Run faggot run!" kanske borde sparas i filen strins istället?
			 */
			text.setText("Run faggot run!");

			Button button = (Button) helpDialog.findViewById(R.id.cancel);
			button.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					helpDialog.dismiss();
				}
			});
			helpDialog.show();
			break;
		case R.id.newG:
			Intent myIntent = new Intent(this, AppActivity.class);
			this.startActivity(myIntent);
			break;
		default:
			break;
		}
		return false;
	}

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.map_pin_24);
		MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(
				drawable);
		mapOverlays.add(itemizedoverlay);
		//Ivans kod
		ArrayList<ParcelableGeoPoint> pointsExtra = getIntent()
				.getParcelableArrayListExtra("geoPoints");

		for (ParcelableGeoPoint p : pointsExtra) {
			points.add(p.getGeoPoint());
		}
		//Slut Ivans kod
		
		final Chronometer cm = (Chronometer) findViewById(R.id.chronometer);
		cm.setBase(SystemClock.elapsedRealtime());
		cm.start();

		mapView = (MapView) findViewById(R.id.mapviewMain);
		mapView.setBuiltInZoomControls(false);
		mController = mapView.getController();
		mapOverlays = mapView.getOverlays();
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		mController.setZoom(5);
		onFix();

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
				Intent myIntent = new Intent(v.getContext(),
						SummaryActivity.class);
				myIntent.putExtra("time", cm.getBase());
				v.getContext().startActivity(myIntent);
			}
		});

		lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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
				double lat = location.getLatitude();
				double lon = location.getLongitude();
				GeoPoint point = new GeoPoint((int)(lat * 1e6),
                        (int)(lon * 1e6));
				mController.animateTo(point);				
								
			}
		};

		lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				this.locationListener);
	}

	protected void markers() {
		if (p != null) {
			Drawable drawable = this.getResources().getDrawable(
					R.drawable.map_pin_24);
			MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(
					drawable);
			int numOfPoints = points.size();
			for (int i = 0; i < numOfPoints; i++) {
				GeoPoint point = points.get(i);
				OverlayItem overlayitem = new OverlayItem(point, null, null);
				itemizedoverlay.addOverlay(overlayitem);
			}
			;
			mapOverlays.clear();
			mapOverlays.add(myLocationOverlay);
			mapOverlays.add(itemizedoverlay);
			mapView.postInvalidate();
		}
	}

	protected void onFix() {
		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				p = myLocationOverlay.getMyLocation();
				mController.animateTo(p);
				mController.setZoom(17);
				markers();
			}
		});

	}

	protected void onResume() {
		super.onResume();
		myLocationOverlay.enableMyLocation();

	}

	@Override
	protected void onPause() {
		super.onPause();
		myLocationOverlay.disableMyLocation();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub "lol"
		return false;
	}
}
