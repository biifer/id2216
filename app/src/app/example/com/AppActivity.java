package app.example.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.view.View.OnClickListener;


public class AppActivity extends Activity implements 
    /** Called when the activity is first created. */
	AdapterView.OnItemSelectedListener {
	String[] items = { "1km","2km","3km","infinity" };
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal);

Spinner spin = (Spinner) findViewById(R.id.spinner1);
		spin.setOnItemSelectedListener(this);

		ArrayAdapter<Object> aa = new ArrayAdapter<Object>(
				this,
				android.R.layout.simple_spinner_item, 
				items);

		aa.setDropDownViewResource(
		   android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(aa);
        setContentView(R.layout.normal);
         
        Button generateMap = (Button)findViewById(R.id.button1);
        generateMap.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), PreviewActivity.class);
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
