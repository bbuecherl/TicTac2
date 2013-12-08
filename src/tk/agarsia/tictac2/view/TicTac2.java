package tk.agarsia.tictac2.view;

import tk.agarsia.tictac2.controller.AppStackController;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.play.PlayValidator;
import tk.agarsia.tictac2.view.activities.MenuActivity;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.auth.GoogleAuthUtil;

/**
 * Activity class for the splash screen.
 * 
 * This activity just initializes the controller and starts the menu activity.
 * 
 * @author agarsia (Bernhard Bücherl)
 * @version 1.0
 * @since 1.0
 */
public class TicTac2 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
				
		//init controller
		ApplicationControl.init(getApplicationContext());
		
		Account[] accounts = AccountManager.get(getApplicationContext())
				.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);

		// TODO let user choose the account to link (currently first)
		Account chosen = accounts[0];

		new PlayValidator(chosen);
		
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