package tk.agarsia.tictac2.view.activities;

import tk.agarsia.tictac2.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class TicTac2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		//load preferences
		PreferenceManager.setDefaultValues(this, R.xml.prefs, false);
		
		//TODO GPS in ASYNC-Task
		
		startActivity(new Intent(getApplicationContext(), MenuActivity.class));
	}

}