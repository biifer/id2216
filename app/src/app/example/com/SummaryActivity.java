package app.example.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SummaryActivity extends Activity {

	int totalTime;
	int totalDistance;
	int averageSpeed;
	int numberOfFlags;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.summary);
		
		Button menu = (Button)findViewById(R.id.menu_button);
        menu.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
        
    	Button share = (Button)findViewById(R.id.share_button);
        share.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
			}
		});
	}
	
	public void setTotalTime (int t) {
		
	}
	
	public void setTotalDistance (int d) {
		
	}
	
	public void setAverageSpeed (int s) {
		
	}
	
	public void setNumberOfFlags (int n) {
		
	}
}