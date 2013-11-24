package tk.agarsia.tictac2.controller;

import tk.agarsia.tictac2.model.Game;
import tk.agarsia.tictac2.model.player.human.HumanLocal;
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
	
	public static void init(Context context) {
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		game = new Game();
	}
	
	public static String getStringPref(String key) {
		return prefs.getString(key, "");
	}
	
	public static boolean getBooleanPref(String key) {
		return prefs.getBoolean(key, false);
	}

	public static void newGame(GameType t) {
		type = t;
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
