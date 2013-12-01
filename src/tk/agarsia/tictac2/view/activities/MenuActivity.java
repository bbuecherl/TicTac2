package tk.agarsia.tictac2.view.activities;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.AppStackController;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.ApplicationControl.GameType;
import tk.agarsia.tictac2.view.MainActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

/**
 * Class for the menu activity
 * 
 * This activity implements MainActivity, displays the (TODO logo and) menu and
 * implements the menu button actions.
 * The layout file is located at res/layout/activity_menu.xml
 * 
 * @author agarisa (Bernhard Bücherl)
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
		
		//clear previous stack
		AppStackController.clearStack();

		// 'todo' alert dialog for unimplemented features
		todo = new AlertDialog.Builder(this).setMessage(
				getString(R.string.todo)).setNeutralButton(
				getString(R.string.ok), new DialogInterface.OnClickListener() {
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
			ApplicationControl.newGame(GameType.SINGLEPLAYER);
			ApplicationControl.start(this, OptActivity.class);
			break;
		case R.id.menu_local: // local multiplayer button was pressed
			ApplicationControl.newGame(GameType.MULTIPLAYER);
			ApplicationControl.start(this, OptActivity.class);
			break;
		default: // all other features are not implemented yet
			todo.show();
			break;
		}
	}
}