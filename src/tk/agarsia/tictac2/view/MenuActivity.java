package tk.agarsia.tictac2.view;

import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.ApplicationControl.GameType;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.tictac2.R;

public class MenuActivity extends MainActivity {

	public MenuActivity() {
		super(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		ApplicationControl.newGame(GameType.INIT);

		findViewById(R.id.menu_singleplayer).setOnTouchListener(this);
		findViewById(R.id.menu_multiplayer).setOnTouchListener(this);
		findViewById(R.id.menu_ranks).setOnTouchListener(this);
		findViewById(R.id.menu_achievements).setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		switch (arg0.getId()) {
		case R.id.menu_singleplayer:
			ApplicationControl.newGame(GameType.SINGLEPLAYER);
			startActivity(new Intent(getApplicationContext(), OptActivity.class));
			break;
		case R.id.menu_multiplayer:
			ApplicationControl.newGame(GameType.MULTIPLAYER);
			startActivity(new Intent(getApplicationContext(), OptActivity.class));
			break;
		case R.id.menu_ranks:
			// TODO open GPS/ranks
			break;
		case R.id.menu_achievements:
			// TODO open GPS/achievements
			break;
		}
		return false;
	}
}