package tk.agarsia.tictac2.controller;

import tk.agarsia.tictac2.model.Game;
import tk.agarsia.tictac2.model.player.human.HumanLocal;
import android.app.Activity;
import android.content.Intent;

public class ApplicationControl {
	public static enum GameType {
		INIT, SINGLEPLAYER, MULTIPLAYER;
	}

	private static GameType type;
	private static HumanLocal me;
	private static Game game = new Game();

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
