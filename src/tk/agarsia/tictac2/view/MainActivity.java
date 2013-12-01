package tk.agarsia.tictac2.view;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.AppStackController;
import tk.agarsia.tictac2.controller.ApplicationControl;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Abstract activity class for basic functionality.
 * 
 * This class implements basic functionality that every other activity in the
 * 'activities' package needs to implement. For example the support action bar,
 * basic onCreate stuff and populating the action bar. It also takes care of the
 * ApplicationControl initialisation state and refers to the splash activity if
 * the application needs to initialize.
 * 
 * @author agarsia (Bernhard Bücherl)
 * @version 1.0
 * @since 1.0
 */
public abstract class MainActivity extends ActionBarActivity implements
		View.OnClickListener {

	private AlertDialog.Builder about;
	private boolean actions;
	private int subtitle;

	/**
	 * Custom constructor for display configuration.
	 * @param actions boolean flag if actionbar actions should be displayed
	 * @param subtitle resource id of an subtitle, 0x0 = no subtitle
	 */
	public MainActivity(boolean actions, int subtitle) {
		this.actions = actions;
		this.subtitle = subtitle;
	}

	@Override
	protected void onCreate(Bundle saved) {
		super.onCreate(saved);

		// let us control the music volume for the soundcontroller
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		// game must be initialized, otherwise back to splash screen
		if (!ApplicationControl.isInit()) {
			startActivity(new Intent(getApplicationContext(), TicTac2.class));
			AppStackController.toStack(this);
			return;
		}
		
		// set the subtitle
		if (subtitle != 0)
			getSupportActionBar().setSubtitle(subtitle);

		//create the alert dialog for about
		about = new AlertDialog.Builder(this)
				.setTitle(R.string.about)
				.setMessage(R.string.about_text)
				.setNeutralButton(getResources().getString(R.string.close),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
	}
	
	@Override
	protected void onPause() {
		AppStackController.toStack(this);

		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (actions) { //show flag must be true
			MenuInflater inflater = getMenuInflater();
			//show actions in actionbar
			inflater.inflate(R.menu.main_activity_actions, menu);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_settings) {
			//settings icon on actionbar was clicked
			ApplicationControl.start(this, Options.class);
			return true;
		} else if (item.getItemId() == R.id.action_about) {
			//about icon on actionbar was clicked
			AlertDialog dialog = about.show();
			
			//refresh message layout
			((TextView) dialog.findViewById(android.R.id.message)).setGravity(Gravity.CENTER);
			((TextView) dialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());

			return true;
		}
		//nothing was selected
		return false;
	}
}