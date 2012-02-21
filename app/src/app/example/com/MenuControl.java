package app.example.com;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MenuControl extends Activity{
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		Context myContext = this; 
	    switch (item.getItemId()) {
	        case R.id.options:
				break;
	        case R.id.help:
	        	 final Dialog dialog = new Dialog(myContext);
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
}
