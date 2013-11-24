package tk.agarsia.tictac2.view.activities;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.FileController;
import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class TicTac2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		//load preferences
		PreferenceManager.setDefaultValues(this, R.xml.prefs, false);
		
		//init controller
		ApplicationControl.init(getApplicationContext());
		ApplicationControl.setMe("{}");
		
		//load File I/O
		FileController.init();
		
		//TODO GPS in ASYNC-Task

		
		ApplicationControl.start(this,MenuActivity.class);
	}

}