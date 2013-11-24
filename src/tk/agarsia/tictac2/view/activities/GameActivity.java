package tk.agarsia.tictac2.view.activities;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.view.MainActivity;
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

		//update player name...
		ApplicationControl.getMe().setName(
				ApplicationControl.getStringPref("pref_player"));

		//start the game!
		ApplicationControl.start(this);
	}

	@Override
	public void onClick(View v) {
	}

}
