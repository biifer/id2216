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

		Spinner spin_1 = (Spinner) findViewById(R.id.spinner1);
		Spinner spin_3 = (Spinner) findViewById(R.id.spinner3);
		Spinner spin_2 = (Spinner) findViewById(R.id.spinner2);
		
		spin_1.setOnItemSelectedListener(this);
		spin_3.setOnItemSelectedListener(this);
		spin_2.setOnItemSelectedListener(this);

		ArrayAdapter<CharSequence> adapter_1 = ArrayAdapter.createFromResource(
	            this, R.array.radius_spinner, android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> adapter_3 = ArrayAdapter.createFromResource(
	            this, R.array.flag_spinner, android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> adapter_2 = ArrayAdapter.createFromResource(
	            this, R.array.distance_spinner, android.R.layout.simple_spinner_item);

		adapter_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter_3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin_1.setAdapter(adapter_1);
		spin_3.setAdapter(adapter_3);
		spin_2.setAdapter(adapter_2);
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
