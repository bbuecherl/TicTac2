package tk.agarsia.tictac2.view.activities;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.view.MainActivity;
import android.os.Bundle;
import android.view.View;

public class GameActivity extends MainActivity {

	public GameActivity() {
		super(true);
	}

	@Override
	protected void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.activity_game);

		// populate information views
		//((TextView) findViewById(R.id.player1_name)).setText(ApplicationControl
		//		.getMe().getName());

	}

	@Override
	public void onClick(View v) {
	}

}
