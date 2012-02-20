package app.example.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.*;
import android.view.View.OnClickListener;

public class AppActivity extends Activity implements
/** Called when the activity is first created. */
AdapterView.OnItemSelectedListener {
	String[] items = { "1km", "2km", "3km", "infinity" };
		
    protected ArrayAdapter<CharSequence> radiusAdapter;
    protected ArrayAdapter<CharSequence> flagAdapter;
    protected ArrayAdapter<CharSequence> distanceAdapter;

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.normal);

		Spinner radiusSpinner = (Spinner) findViewById(R.id.spinner1);
		Spinner flagSpinner = (Spinner) findViewById(R.id.spinner3);
		Spinner distanceSpinner = (Spinner) findViewById(R.id.spinner2);
		
		radiusSpinner.setOnItemSelectedListener(this);
		flagSpinner.setOnItemSelectedListener(this);
		distanceSpinner.setOnItemSelectedListener(this);

		radiusAdapter= ArrayAdapter.createFromResource(
	            this, R.array.radius_spinner, android.R.layout.simple_spinner_item);
		flagAdapter = ArrayAdapter.createFromResource(
	            this, R.array.flag_spinner, android.R.layout.simple_spinner_item);
		distanceAdapter = ArrayAdapter.createFromResource(
	            this, R.array.distance_spinner, android.R.layout.simple_spinner_item);

		radiusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		flagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		radiusSpinner.setAdapter(radiusAdapter);
		flagSpinner.setAdapter(flagAdapter);
		distanceSpinner.setAdapter(distanceAdapter);
		//setContentView(R.layout.normal);

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

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
	}

	public void onNothingSelected(AdapterView<?> arg0) {
	}
}
