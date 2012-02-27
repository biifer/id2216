package app.example.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
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
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
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
	GeoPoint p, prevPoint = null;
	List<Overlay> mapOverlays;
	ArrayList<GeoPoint> points = new ArrayList<GeoPoint>();
	ArrayList<GeoPoint> notYetReachedPoints = new ArrayList<GeoPoint>();
	ArrayList<ParcelableGeoPoint> visitedPoints = new ArrayList<ParcelableGeoPoint>();
	MyLocationOverlay myLocationOverlay;
	Location destLocation = new Location("destLoaction");
	Location prevLocation = new Location("prevLocation");
	float distanceToNearestPoint = 9000;
	float speed, distance, totalDistance = -1;
	Drawable icon; 
	ParcelableGeoPoint mainPoint;
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
			 * "Run faggot run!" kanske borde sparas i filen strings istället?
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
		final MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(drawable);
		mapView = (MapView) findViewById(R.id.mapviewMain);
		mapView.setBuiltInZoomControls(true);
		mController = mapView.getController();
		mapOverlays = mapView.getOverlays();
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		mapView.getOverlays().add(myLocationOverlay);
		visitedPoints.clear();
		/*
		 * not sure if final should be there but it's required by another method
		 */
		final ArrayList<ParcelableGeoPoint> arrayOfParcebleGeoPoints = getIntent()
				.getParcelableArrayListExtra("geoPoints");
		Bundle extras = getIntent().getExtras();
		mainPoint= extras.getParcelable("mainPoint");
		for (ParcelableGeoPoint p : arrayOfParcebleGeoPoints) {
			points.add(p.getGeoPoint());
			notYetReachedPoints.add(p.getGeoPoint());
		}

		int numOfPoints = points.size();
		for (int i = 0; i < numOfPoints; i++) {
			GeoPoint point = points.get(i);
			OverlayItem overlayitem = new OverlayItem(point, null, null);
			itemizedoverlay.addOverlay(overlayitem);
		};

		mapOverlays.add(itemizedoverlay);
		mapView.postInvalidate();

		final Chronometer cm = (Chronometer) findViewById(R.id.chronometer);
		cm.setBase(SystemClock.elapsedRealtime());
		cm.start();

		

		icon = this.getResources().getDrawable(R.drawable.map_pin_24_green);
		icon.setBounds(
		    0 - icon.getIntrinsicWidth() / 2, 0 - icon.getIntrinsicHeight(), 
		    icon.getIntrinsicWidth() / 2, 0);
		/*Button checkPoint = (Button) findViewById(R.id.checkpoint);
		checkPoint.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});*/

	/*	Button summary = (Button) findViewById(R.id.summary);
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
				myIntent.putExtra("geoPoints", arrayOfParcebleGeoPoints);
				myIntent.putExtra("visitedPoints", visitedPoints);
				myIntent.putExtra("mainPoint",mainPoint);
				v.getContext().startActivity(myIntent);
				finish();
			}
		});*/

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
				GeoPoint point = new GeoPoint((int) (lat * 1e6),
						(int) (lon * 1e6));

				visitedPoints.add(new ParcelableGeoPoint(point));

				if (prevPoint != null) {
					mapOverlays
							.add(new RouteOverlay(prevPoint, point, 0xFF0000));
				}

				if (totalDistance == -1) {
					totalDistance = 0;
				} else {
					totalDistance = totalDistance
							+ location.distanceTo(prevLocation);
				}
				prevLocation = location;
				prevPoint = point;

				mController.animateTo(point);

				/*
				 * This for-loop checks at every GPS update if any flag is
				 * within reach.
				 */
				for (int i = 0; i < notYetReachedPoints.size(); i++) {
					destLocation.reset();
					destLocation.setLatitude((float) (notYetReachedPoints
							.get(i).getLatitudeE6() / 1E6));
					destLocation.setLongitude((float) (notYetReachedPoints.get(
							i).getLongitudeE6() / 1E6));
					distance = location.distanceTo(destLocation);
					if (distanceToNearestPoint > distance) {
						distanceToNearestPoint = distance;
					}

					/*
					 * user catches a flag if current position is within 50m of
					 * a flag
					 */
					if (distance < 50) {
						/*
						 * checkpoint reached
						 */
						Context context = getApplicationContext();
						CharSequence text = "Checkpoint reached!";
						int duration = Toast.LENGTH_SHORT;
						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
						/*
						 * remove the reached point from the list
						 */
						for(int a=0;a<itemizedoverlay.size();a++){
						if(notYetReachedPoints.get(i).equals(itemizedoverlay.getItem(a).getPoint()))
						itemizedoverlay.getItem(a).setMarker(icon);}
						
						notYetReachedPoints.remove(i);

						/*
						 * change color of flag (code not complete)
						 */

					}

				}

				/*
				 * not really tested yet
				 */
				TextView distance_to_point = (TextView) findViewById(R.id.distance_to_point);
				distance_to_point.setText("Distance to nearest point: "
						+ distanceToNearestPoint + "m");

				speed = location.getSpeed();
				TextView speed_text = (TextView) findViewById(R.id.speed);
				speed_text.setText("Speed: " + speed + "m/s");

				TextView totalDistance_text = (TextView) findViewById(R.id.total_distance);
				totalDistance_text.setText("Total distance: " + totalDistance);

				if (notYetReachedPoints.isEmpty()) {

					/*
					 * This is part of a strange bug-fix the location listener
					 * does not shut off when the "finish()" method is called
					 */
					lManager.removeUpdates(locationListener);
					lManager = null;

					Intent myIntent = new Intent(MainActivity.this,
							SummaryActivity.class);
					myIntent.putExtra("time", cm.getBase());
					myIntent.putExtra("totalDistance", totalDistance);
					myIntent.putExtra("geoPoints", arrayOfParcebleGeoPoints);
					myIntent.putExtra("visitedPoints", visitedPoints);
					myIntent.putExtra("mainPoint",mainPoint);

					MainActivity.this.startActivity(myIntent);
					finish();
				}
			}

		};

		lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				this.locationListener);
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
		// TODO Auto-generated method stub
		return false;
	}
}
