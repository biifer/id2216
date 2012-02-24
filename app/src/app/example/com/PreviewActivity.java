package app.example.com;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

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
import android.graphics.*;
import android.graphics.drawable.Drawable;

import com.google.android.maps.*;

public class PreviewActivity extends MapActivity {
	
	SharedPreferences settings;
	private int loadIntFromMyPrefs(String key) {
		int defValue = -1;
		settings = getSharedPreferences(PREFS_NAME, 0);
		int value = settings.getInt(key, defValue);
		return value;
	}

	int flags;
	int radius;
	MapView mapView;
	MapController mc;
	MyLocationOverlay myLocationOverlay;
	
	public static final String PREFS_NAME = "PrefsFile"; //I filen sparar vi: time, averageSpeed, name, nrOfFLags, 

	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu2, menu);
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

	                TextView text = (TextView) dialog.findViewById(R.id.aboutText);
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
	                break;
	   	        case	R.id.newG:
	   					Intent myIntent = new Intent(this, AppActivity.class);
	   					this.startActivity(myIntent);	
	   	        	break;
	        default:
	        	break;
	    }
	    return false;
	}
	private void saveToMyPrefs() {
		//TODO lägga in alla parametrar som ska sparas till summary, koordinater av flaggor: hur lösa det?
		
	}
	GeoPoint p;
	List<Overlay> mapOverlays;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.preview);

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mc = mapView.getController();
		mapOverlays = mapView.getOverlays();
		myLocationOverlay = new MyLocationOverlay(this, mapView);	
	    mc.setZoom(5);
	    onFix();
	    
	    mapView.postInvalidate();
 
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
				markers();
			}
		});
         
       
	}
	protected void markers(){
		if(p != null){
		Random generatorCord = new Random();
		Drawable drawable = this.getResources().getDrawable(R.drawable.map_pin_24);
		MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(drawable);
		radius = 2000;
		int temp = loadIntFromMyPrefs("flags"); 
		 for(flags=0;flags<temp;flags++){
				int lo = p.getLongitudeE6()+(generatorCord.nextInt(radius)-1000);
				int la = p.getLatitudeE6()+(generatorCord.nextInt(radius)-1000);
				GeoPoint point = new GeoPoint(la,lo);
				OverlayItem overlayitem = new OverlayItem(point, null, null);
				itemizedoverlay.addOverlay(overlayitem);
				};
				mapOverlays.clear();
				mapOverlays.add(myLocationOverlay); 
				mapOverlays.add(itemizedoverlay);
				mapView.postInvalidate();
		}
	}
	 protected void onFix(){
		 myLocationOverlay.runOnFirstFix(new Runnable() { 
		    	public void run() {
		    		p = myLocationOverlay.getMyLocation();
		        mc.animateTo(p);
		        mc.setZoom(17);
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
		// TODO Auto-generated method stub
		return true;
	}
}
