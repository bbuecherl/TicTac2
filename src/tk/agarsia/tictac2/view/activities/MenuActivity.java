package tk.agarsia.tictac2.view.activities;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.AppStackController;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.ApplicationControl.GameType;
import tk.agarsia.tictac2.view.MainActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Class for the menu activity
 * 
 * This activity implements MainActivity, displays the (TODO logo and) menu and
 * implements the menu button actions. The layout file is located at
 * res/layout/activity_menu.xml
 * 
 * @author agarisa (Bernhard B��cherl)
 * @version 1.0
 * @since 1.0
 */
public class MenuActivity extends MainActivity {

	// todo message
	private AlertDialog.Builder todo;

	/**
	 * Custom constructor
	 * 
	 * Initializes with enabled actions and the menu subtitle.
	 */
	public MenuActivity() {
		super(true, R.string.menu_subtitle);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		if (!ApplicationControl.isInit())
			return;

		// clear previous stack
		AppStackController.clearStack();

		// 'todo' alert dialog for unimplemented features
		todo = new AlertDialog.Builder(this).setMessage(
				getString(R.string.todo)).setNeutralButton(
				getString(R.string.ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		// initialize the game type
		ApplicationControl.newGame(GameType.INIT);

		// set this activity as click listener for the menu buttons
		findViewById(R.id.menu_singleplayer).setOnClickListener(this);
		findViewById(R.id.menu_local).setOnClickListener(this);
		findViewById(R.id.menu_online).setOnClickListener(this);
		findViewById(R.id.menu_ranks).setOnClickListener(this);
		findViewById(R.id.menu_achievements).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_singleplayer: // singleplayer button was pressed
			ApplicationControl.newGame(GameType.SINGLE);
			ApplicationControl.start(this, OptActivity.class);
			break;
		case R.id.menu_local: // local multiplayer button was pressed
			ApplicationControl.newGame(GameType.LOCAL);
			ApplicationControl.start(this, OptActivity.class);
			break;
		case R.id.menu_online: // online multiplayeer
			ApplicationControl.newGame(GameType.ONLINE);
			validate();
			break;
		case R.id.menu_achievements:
			todo.show();
			// UIController.startOnline(this, AchievementActivity.class);
			break;
		case R.id.menu_ranks:
			todo.show();
			// UIController.startOnline(this, RankActivity.class);
			break;
		}
	}

	private void validate() {
	}

	public void finished(final String token) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(ApplicationControl.getContext(), token,
						Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1001) {
			if (data == null) {
				Log.i("menu", "unknown error");
				return;
			} else if (resultCode == RESULT_OK) {
				Log.i("menu", "retrying");
				validate();
				return;
			} else if (resultCode == RESULT_CANCELED) {
				Log.i("menu", "user rejected");
				return;
			} else {
				Log.i("menu", "unknown error");
				return;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}