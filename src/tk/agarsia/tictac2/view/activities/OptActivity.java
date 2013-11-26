package tk.agarsia.tictac2.view.activities;

import java.util.Random;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.ApplicationControl.GameType;
import tk.agarsia.tictac2.model.player.AbstractPlayer;
import tk.agarsia.tictac2.model.player.bot.BotRandom;
import tk.agarsia.tictac2.model.player.human.HumanLocal;
import tk.agarsia.tictac2.view.MainActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

/**
 * Class for the game options activity.
 * 
 * This activity implements MainActivity, displays and handles game option
 * changes. The layout file is located at res/layout/activity_opt.xml
 * 
 * @author agarsia (Bernhard Bücherl)
 * @version 1.0
 * @since 1.0
 */
public class OptActivity extends MainActivity {

	// java.util.Random object for starting with random player
	private Random rand;

	/**
	 * Custom constructor
	 * 
	 * Initializes with disabled actions andthe game settings subtitle.
	 */
	public OptActivity() {
		super(false, R.string.opt_subtitle);
		rand = new Random();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opt);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// set start local player text to the local player name
		((RadioButton) findViewById(R.id.opt_start_me))
				.setText(ApplicationControl.getStringPref("pref_player"));

		// layout configurations depending on the current game type
		switch (ApplicationControl.getGameType()) {
		case INIT:
			// not initialized, go back to menu
			back();
			break;
		case SINGLEPLAYER: // hide other player name option (we play vs a bot!)
			((RadioButton) findViewById(R.id.opt_start_other))
					.setText(getString(R.string.opt_bot));
			findViewById(R.id.opt_name_desc).setVisibility(View.GONE);
			findViewById(R.id.opt_name).setVisibility(View.GONE);
			findViewById(R.id.space4).setVisibility(View.GONE);
			break;
		case MULTIPLAYER: // XXX (not sure if this could be removed) make sure
							// to show other player name options
			findViewById(R.id.opt_name_desc).setVisibility(View.VISIBLE);
			findViewById(R.id.opt_name).setVisibility(View.VISIBLE);
			findViewById(R.id.space4).setVisibility(View.VISIBLE);
			break;
		}
	}

	@Override
	public Intent getSupportParentActivityIntent() {
		// "up" navigation to return to menu
		return new Intent(getApplicationContext(), MenuActivity.class);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// add options menu next & back button to the actionbar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.opt_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// add options menu next & back button on select support
		if (item.getItemId() == R.id.action_next) {
			next();
			return true;
		} else if (item.getItemId() == R.id.action_back) {
			back();
			return true;
		}
		return false;
	}

	/**
	 * Method to go back to the menu.
	 * 
	 * Will start the MenuActivity.
	 */
	private void back() {
		ApplicationControl.start(this, MenuActivity.class);
	}

	/**
	 * Method to start the game.
	 * 
	 * This function starts the game with given settings.
	 */
	private void next() {
		// make sure the game controller is empty initialized.
		ApplicationControl.reinit();

		// fetch option views
		Spinner size = (Spinner) findViewById(R.id.opt_boardsize_id);
		Spinner win = (Spinner) findViewById(R.id.opt_winlength_id);
		Spinner mpr = (Spinner) findViewById(R.id.opt_mpr_id);
		RadioGroup index = (RadioGroup) findViewById(R.id.opt_start);

		int interval = 0; // XXX interval currently alwas 0 (do we even
							// implement the speedmode?
		int boardDim = 3 + size.getSelectedItemPosition(); // board dimension
															// (3x3,4x4,5x5,6x6)
		int winLength = 3 + win.getSelectedItemPosition(); // win length
															// (3,4,5,6)
		int mpt = 1 + mpr.getSelectedItemPosition(); // marks per turn (1,2,3)
		int spi = 1 + rand.nextInt(2); // preinitialize start player index with
										// random

		switch (index.getCheckedRadioButtonId()) {
		case R.id.opt_start_me:
			spi = 1;
			break;
		case R.id.opt_start_other:
			spi = 2;
			break;
		}

		// init other player
		AbstractPlayer player2 = null;
		if (ApplicationControl.getGameType() == GameType.SINGLEPLAYER) {
			// start singleplayer (vs Bot)
			player2 = new BotRandom(ApplicationControl.getGame());
		} else if (ApplicationControl.getGameType() == GameType.MULTIPLAYER) {
			// start local (vs other local human)
			String name = ((EditText) findViewById(R.id.opt_name)).getText()
					.toString();
			if (name == "")
				name = getString(R.string.opt_default);

			player2 = new HumanLocal(name, ApplicationControl.getGame());
		}

		// initialize game model
		ApplicationControl.getGame().initModel(interval, boardDim, winLength,
				mpt, spi);

		// set players for the current game
		ApplicationControl.getGame().setPlayers(ApplicationControl.getMe(),
				player2);

		// start the game activity
		ApplicationControl.start(this, GameActivity.class);
	}

	@Override
	public void onClick(View v) {
	}
}