package tk.agarsia.tictac2.view.activities;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.view.MainActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends MainActivity {

	public GameActivity() {
		super(true);
	}

	@Override
	protected void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.activity_game);

		ApplicationControl.getMe().setName(
				ApplicationControl.getStringPref("pref_player"));

		// populate information views
		((TextView) findViewById(R.id.player1_name)).setText(ApplicationControl
				.getGame().getPlayers()[1].getName());
		((TextView) findViewById(R.id.player2_name)).setText(ApplicationControl
				.getGame().getPlayers()[2].getName());

	}

	@Override
	public void onClick(View v) {
	}

}
