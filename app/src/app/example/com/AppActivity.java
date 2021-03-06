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

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
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
			text.setText("Application done by Group6, v0.02 (beta)");

			aboutDialog.show();
			break;
		case R.id.help:
			final Dialog helpDioalog = new Dialog(myContext);
			helpDioalog.setContentView(R.layout.help);
			helpDioalog.setTitle("Help");
			helpDioalog.setCancelable(true);

			text = (TextView) helpDioalog.findViewById(R.id.Text);
			text.setText("Click on the spinners to change the number of flags and the radius away from you they can spawn");

			Button button = (Button) helpDioalog.findViewById(R.id.cancel);
			button.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					helpDioalog.dismiss();
				}
			});
			helpDioalog.show();
		default:
			break;
		}
		return false;
	}

	protected ArrayAdapter<CharSequence> radiusAdapter;
	protected ArrayAdapter<CharSequence> flagAdapter;
	protected ArrayAdapter<CharSequence> distanceAdapter;

	public static final String PREFS_NAME = "PrefsFile";
	SharedPreferences settings;
	SharedPreferences.Editor editor;

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.normal);
		Spinner radiusSpinner = (Spinner) findViewById(R.id.radiusSpinner);
		Spinner flagSpinner = (Spinner) findViewById(R.id.flagSpinner);
	//	Spinner distanceSpinner = (Spinner) findViewById(R.id.distanceSpinner);

		radiusSpinner.setOnItemSelectedListener(this);
		flagSpinner.setOnItemSelectedListener(this);
	//	distanceSpinner.setOnItemSelectedListener(this);

		this.radiusAdapter = ArrayAdapter.createFromResource(this,
				R.array.radius_spinner, android.R.layout.simple_spinner_item);
		this.flagAdapter = ArrayAdapter.createFromResource(this,
				R.array.flag_spinner, android.R.layout.simple_spinner_item);
	/*	this.distanceAdapter = ArrayAdapter.createFromResource(this,
				R.array.distance_spinner, android.R.layout.simple_spinner_item);*/

		radiusAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		flagAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		/*distanceAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
		radiusSpinner.setAdapter(this.radiusAdapter);
		flagSpinner.setAdapter(this.flagAdapter);
		//distanceSpinner.setAdapter(this.distanceAdapter);

		Button generateMap = (Button) findViewById(R.id.generate_map_button);
		generateMap.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(),
						PreviewActivity.class);
				v.getContext().startActivity(myIntent);
			}
		});

	}

	public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
			long arg3) {

		// String selected = parent.getItemAtPosition(pos).toString();
		Object id = parent.getAdapter();
		if (id == radiusAdapter) {
			String selected = parent.getItemAtPosition(pos).toString();
			saveStringToMyPrefs("radius", selected);
		} else if (id == flagAdapter) {
			String selected = parent.getItemAtPosition(pos).toString();
			saveStringToMyPrefs("flags", selected);
		}/* else if (id == distanceAdapter) {
			String selected = parent.getItemAtPosition(pos).toString();
			saveStringToMyPrefs("distance", selected);
		}*/

	}

	public void onNothingSelected(AdapterView<?> arg0) {
	}
}
