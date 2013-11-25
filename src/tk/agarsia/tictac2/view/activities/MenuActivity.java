package tk.agarsia.tictac2.view.activities;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.ApplicationControl.GameType;
import tk.agarsia.tictac2.view.MainActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends MainActivity {

	private AlertDialog.Builder todo;

	public MenuActivity() {
		super(true, R.string.menu_subtitle);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		// for unimplemented buttons
		todo = new AlertDialog.Builder(this).setMessage(
				getString(R.string.todo)).setNeutralButton(
				getString(R.string.ok), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		ApplicationControl.newGame(GameType.INIT);

		findViewById(R.id.menu_singleplayer).setOnClickListener(this);
		findViewById(R.id.menu_local).setOnClickListener(this);
		findViewById(R.id.menu_online).setOnClickListener(this);
		findViewById(R.id.menu_ranks).setOnClickListener(this);
		findViewById(R.id.menu_achievements).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_singleplayer:
			ApplicationControl.newGame(GameType.SINGLEPLAYER);
			ApplicationControl.start(this, OptActivity.class);
			break;
		case R.id.menu_local:
			ApplicationControl.newGame(GameType.MULTIPLAYER);
			ApplicationControl.start(this, OptActivity.class);
			break;
		default:
			todo.show();
			break;
		}
	}
}