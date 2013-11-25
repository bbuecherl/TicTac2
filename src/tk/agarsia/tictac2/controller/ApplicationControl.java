package tk.agarsia.tictac2.controller;

import tk.agarsia.tictac2.model.Game;
import tk.agarsia.tictac2.model.player.human.HumanLocal;
import tk.agarsia.tictac2.view.activities.GameActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ApplicationControl {
	public static enum GameType {
		INIT, SINGLEPLAYER, MULTIPLAYER;
	}

	private static SharedPreferences prefs;
	private static GameType type;
	private static HumanLocal me;
	private static Game game;
	private static GameActivity act;
	private static GameController controller;
	private static boolean isInit = false;
	
	
	public static void init(Context context) {
		isInit = true;
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Vibration.init(context);
		reinit();
	}
	
	public static boolean isInit() {
		return isInit;
	}
	
	public static void reinit() {
		game = new Game();
		controller = new GameController(game);
		setMe(getStringPref("pref_player"));
	}
	
	public static String getStringPref(String key) {
		return getStringPref(key, "");
	}
	
	public static String getStringPref(String key, String def) {
		return prefs.getString(key, def);
	}
	
	public static boolean getBooleanPref(String key) {
		return prefs.getBoolean(key, false);
	}

	public static void newGame(GameType t) {
		type = t;
	}
	
	public static void start(GameActivity a) {
		act = a;
		game.start();
	}
	
	public static GameActivity getGameActivity() {
		return act;
	}
	
	public static GameController getGameController() {
		return controller;
	}

	public static GameType getGameType() {
		return type;
	}

	public static Game getGame() {
		return game;
	}

	public static void setMe(String m) {
		me = new HumanLocal(m, game);
	}

	public static HumanLocal getMe() {
		return me;
	}

	public static void start(Activity act, Class<?> class1) {
		act.startActivity(new Intent(act.getApplicationContext(),class1));
	}
}
