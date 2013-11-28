package tk.agarsia.tictac2.view.activities;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.AppStackController;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.Vibration;
import tk.agarsia.tictac2.model.player.AbstractPlayer;
import tk.agarsia.tictac2.view.MainActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

/**
 * Class for the main game activity.
 * 
 * This activity implements MainActivity and displays all the game magic. The
 * layout file is located at res/layout/activity_game.xml
 * 
 * @author agarsia (Bernhard Bücherl)
 * @version 1.0
 * @since 1.0
 */
public class GameActivity extends MainActivity {

	private AlertDialog.Builder alert;

	/**
	 * Custom constructor
	 * 
	 * Initializes with enabled actions and no subtitle.
	 */
	public GameActivity() {
		super(true, 0);
	}

	@Override
	protected void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.activity_game);

		// disable "up" navigation
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

		// alert dialog
		final MainActivity inst = this;
		alert = new AlertDialog.Builder(this)
				.setTitle(R.string.pause)
				.setMessage(R.string.pause_text)
				.setPositiveButton(getResources().getString(R.string.resume),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						})
				.setNegativeButton(getResources().getString(R.string.close),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								inst.finish();
								dialog.dismiss();
							}
						});

		// start the game!
		ApplicationControl.start(this);
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onBackPressed() {
		alert.show();
	}

	@Override
	public void onPause() {
		//clearing application stack
		AppStackController.clearStack();
		AppStackController.toStack(this);
		
		// TODO save current game process (feature for version 2.0)
		super.onPause();
	}

	/**
	 * Method to show the game finished dialog.
	 * 
	 * This method is executed by game controller, when the game is finished. It
	 * will display a alert dialog indicating the winner. TODO The share
	 * functionality will be implemented in version 2.0.
	 * 
	 * @param winner
	 *            winner of the game as abstract player object, null if there is
	 *            no winner
	 */
	public void gameFinished(AbstractPlayer winner) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this)
				.setTitle(
						(winner == ApplicationControl.getMe()) ? R.string.game_won
								: R.string.game_over)
				.setMessage(
						getString(R.string.game_whowon)
								.replace(
										"{win}",
										(winner == null) ? getString(R.string.game_noone)
												: winner.getName()))
				// XXX will be implemented in version 2.0
				// .setPositiveButton(getString(R.string.share),
				// new DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(DialogInterface dialog, int which) {
				// XXX share options here...
				// doGameFinish();
				// Vibration.cancel();
				// dialog.dismiss();
				// }
				// })
				.setNeutralButton(getString(R.string.ok),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Vibration.cancel();
								doGameFinish();
								dialog.dismiss();
							}
						});
		alert.setCancelable(false);

		alert.show();
	}

	/**
	 * Method to reinit the game controller and go back to the menu.
	 * 
	 * This is used by gameFinish() when clicking 'ok' or 'share'
	 */
	private void doGameFinish() {
		ApplicationControl.reinit();
		ApplicationControl.start(this, MenuActivity.class);
	}
}