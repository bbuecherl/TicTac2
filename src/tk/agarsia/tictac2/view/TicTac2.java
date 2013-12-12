package tk.agarsia.tictac2.view;

import tk.agarsia.tictac2.controller.AppStackController;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.view.activities.MenuActivity;
import android.app.Activity;
import android.os.Bundle;

/**
 * Activity class for the splash screen.
 * 
 * This activity just initializes the controller and starts the menu activity.
 * 
 * @author agarsia (Bernhard B��cherl)
 * @version 1.0
 * @since 1.0
 */
public class TicTac2 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
				
		//init controller
		ApplicationControl.init(getApplicationContext());

		//start menu activity
		ApplicationControl.start(this,MenuActivity.class);
	}
	
	@Override
	protected void onPause() {
		//add to the stack
		AppStackController.toStack(this);
		
		super.onPause();
	}	
}