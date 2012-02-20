package app.example.com;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.*;
import android.view.View.OnClickListener;

public class AppActivity extends Activity implements
/** Called when the activity is first created. */
AdapterView.OnItemSelectedListener {
	
    protected ArrayAdapter<CharSequence> radiusAdapter;
    protected ArrayAdapter<CharSequence> flagAdapter;
    protected ArrayAdapter<CharSequence> distanceAdapter;

    private static final String PREFS_NAME = "MyPrefs";	
	
    boolean option1;
    boolean option2;
    boolean option3;
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
		Intent intent = new Intent();
	    switch (item.getItemId()) {
	        case R.id.options:
	            //Start OptionsActivity 
	            intent.setClass(AppActivity.this, OptionsActivity.class);
	          //  startActivityForResult(intent, 0);
	            startActivity(intent);
				break;
	        case R.id.help:
	        	 final Dialog dialog = new Dialog(AppActivity.this);
	                dialog.setContentView(R.layout.help);
	                dialog.setTitle("Help");
	                dialog.setCancelable(true);

	                TextView text = (TextView) dialog.findViewById(R.id.Text);
	                text.setText("Apa");

	                Button button = (Button) dialog.findViewById(R.id.cancel);
	                button.setOnClickListener(new OnClickListener() {
	                	
	                public void onClick(View v) {
	                        dialog.dismiss();
	                    }
	                });
	                dialog.show();
	        default:
	        	break;
	    }
	    return false;
	}
	
	private void saveToMyPrefs() {
		int value = 0;
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putInt("Distance", value);
	    editor.putInt("nrOfFlags", value);
	    editor.putInt("Radius", value);		
	}
	
	private void loadFromMyPrefs() {
		boolean defValue = false;
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		option1 = settings.getBoolean("settings_Option1", defValue);
		option2 = settings.getBoolean("settings_option2", defValue);
		option3 = settings.getBoolean("settings_option3", defValue);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.normal);

		
		Spinner radiusSpinner = (Spinner) findViewById(R.id.spinner1);
		Spinner flagSpinner = (Spinner) findViewById(R.id.spinner3);
		Spinner distanceSpinner = (Spinner) findViewById(R.id.spinner2);
		
		radiusSpinner.setOnItemSelectedListener(this);
		flagSpinner.setOnItemSelectedListener(this);
		distanceSpinner.setOnItemSelectedListener(this);

		this.radiusAdapter= ArrayAdapter.createFromResource(
	            this, R.array.radius_spinner, android.R.layout.simple_spinner_item);
		this.flagAdapter = ArrayAdapter.createFromResource(
	            this, R.array.flag_spinner, android.R.layout.simple_spinner_item);
		this.distanceAdapter = ArrayAdapter.createFromResource(
	            this, R.array.distance_spinner, android.R.layout.simple_spinner_item);

		radiusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		flagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		radiusSpinner.setAdapter(this.radiusAdapter);
		flagSpinner.setAdapter(this.flagAdapter);
		distanceSpinner.setAdapter(this.distanceAdapter);

		Button generateMap = (Button) findViewById(R.id.generate_map_button);
		generateMap.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				saveToMyPrefs();
				Intent myIntent = new Intent(v.getContext(),
						PreviewActivity.class);
				v.getContext().startActivity(myIntent);
			}
		});
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
	}

	public void onNothingSelected(AdapterView<?> arg0) {
	}
}
