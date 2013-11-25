package tk.agarsia.tictac2.view.activities;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.model.player.AbstractPlayer;
import tk.agarsia.tictac2.view.MainActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

public class GameActivity extends MainActivity {

	public GameActivity() {
		super(true, 0);
	}

	@Override
	protected void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.activity_game);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

		// start the game!
		ApplicationControl.start(this);
	}

	@Override
	public void onClick(View v) {
	}

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
				.setPositiveButton(getString(R.string.share),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO share options, currently only dismissing
								doGameFinish();
								dialog.dismiss();
							}
						})
				.setNeutralButton(getString(R.string.ok),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								doGameFinish();
								dialog.dismiss();
							}
						});
		alert.setCancelable(false);
		
		alert.show();
	}

	private void doGameFinish() {
		ApplicationControl.reinit();
		ApplicationControl.start(this, MenuActivity.class);
	}
}