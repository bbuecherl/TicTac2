package tk.agarsia.tictac2.view.activities;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.ApplicationControl.GameType;
import tk.agarsia.tictac2.view.MainActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends MainActivity {

	public MenuActivity() {
		super(true,R.string.menu_subtitle);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		ApplicationControl.newGame(GameType.INIT);
		
		findViewById(R.id.menu_singleplayer).setOnClickListener(this);
		findViewById(R.id.menu_multiplayer).setOnClickListener(this);
		findViewById(R.id.menu_ranks).setOnClickListener(this);
		findViewById(R.id.menu_achievements).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.menu_singleplayer:
			ApplicationControl.newGame(GameType.SINGLEPLAYER);
			ApplicationControl.start(this,OptActivity.class);
			break;
		case R.id.menu_multiplayer:
			ApplicationControl.newGame(GameType.MULTIPLAYER);
			ApplicationControl.start(this,OptActivity.class);
			break;
		//TODO ranks & achievements
		}
	}
}