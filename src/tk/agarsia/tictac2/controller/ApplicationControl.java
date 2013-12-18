package tk.agarsia.tictac2.controller;

import tk.agarsia.tictac2.controller.feedback.SoundController;
import tk.agarsia.tictac2.controller.feedback.Vibration;
import tk.agarsia.tictac2.controller.marks.MarkController;
import tk.agarsia.tictac2.model.Game;
import tk.agarsia.tictac2.model.player.human.HumanLocal;
import tk.agarsia.tictac2.view.activities.GameActivity;
import tk.agarsia.tictac2.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Abstract class supporting static getter, setter and initialisation for the
 * controller framework
 * 
 * @author agarsia (Bernhard B��cherl)
 * @version 1.0
 * @since 1.0
 */
public abstract class ApplicationControl {
	/**
	 * Static enum used for identifing game type
	 * 
	 * @author agarsia (Bernhard B��cherl)
	 * @version 1.0
	 * @since 1.0
	 */
	public static enum GameType {
		INIT, SINGLE, LOCAL, ONLINE;
	}

	// static references
	private static SharedPreferences prefs;
	private static GameType type;
	private static HumanLocal me;
	private static Game game;
	private static GameActivity act;
	private static GameController controller;
	private static Context context;
	private static boolean isInit = false;

	/**
	 * Static initialisation method.
	 * 
	 * This method needs to be activated when the application starts. It
	 * initializes a preferences shortcut, initializes the game and all
	 * necessary controllers for Sound, Vibration and Game. It sets isInit to
	 * true and uses reinit().
	 * 
	 * @param context
	 *            context object of the application
	 */
	public static void init(Context context) {
		ApplicationControl.context = context;

		// initialize file controller
		FileController.init();
		
		// load preferences
		PreferenceManager.setDefaultValues(context, R.xml.prefs, false);

		// initialize preferences
		prefs = PreferenceManager.getDefaultSharedPreferences(context);

		
		//initialize mark controller
		MarkController.init();
				
		// initialize sound controller
		SoundController.init(context);

		// initialize vibration controller
		Vibration.init(context);

		// initialize game
		reinit();

		// finally initialized
		isInit = true;
	}

	/**
	 * Static getter for the initialisation state.
	 * 
	 * The isInit value will be true if ApplicationControl.init() has been
	 * excecuted previously, otherwise it will return false.
	 * 
	 * @return initialisation state
	 */
	public static boolean isInit() {
		return isInit;
	}

	/**
	 * Static getter method for the applications context
	 * 
	 * @return context object
	 */
	public static Context getContext() {
		return context;
	}

	/**
	 * Static method to (re-)initialize the game and its controller.
	 * 
	 * ApplicationControl.isInit() has to be true to execute this method (no
	 * testing required).
	 */
	public static void reinit() {
		// ApplicationControl must be initialized
		if (!isInit())
			return;

		// initialize game & controller
		game = new Game();
		controller = new GameController(game);

		// set the players username
		setMe(getStringPref("pref_player"));
	}

	/**
	 * Static getter for a preference string.
	 * 
	 * This will execute ApplicationControl.getStringPref(key,def) with 'def' as
	 * empty string.
	 * 
	 * @param key
	 *            key
	 * @return stored preference value or "" if the preference does not exist
	 */
	public static String getStringPref(String key) {
		return getStringPref(key, "");
	}

	/**
	 * Static getter for a preference string.
	 * 
	 * This will return the string preference associated with the input key. If
	 * no preference matches, the function will return the input default value.
	 * 
	 * @param key
	 *            key
	 * @param def
	 *            default value
	 * @return stored preference value or default value if the preference does
	 *         not exist
	 */
	public static String getStringPref(String key, String def) {
		return prefs.getString(key, def);
	}

	/**
	 * Static getter for a preference boolean.
	 * 
	 * This will return the boolean preference associated with the input key. If
	 * no preference matches, the function will return false.
	 * 
	 * @param key
	 *            key
	 * @return stored preference value or false
	 */
	public static boolean getBooleanPref(String key) {
		return prefs.getBoolean(key, false);
	}

	/**
	 * Static setter for the current game type.
	 * 
	 * This is mainly used for OptActivity display purposes and defines if game
	 * will play against a local human or bot
	 * 
	 * @param t
	 *            new type
	 */
	public static void newGame(GameType t) {
		type = t;
	}

	/**
	 * Static method to start a game.
	 * 
	 * This will set a reference to the current game activity (for displaying an
	 * alert at the end of the game) and starts the game.
	 * 
	 * @param a
	 *            game activity object
	 */
	public static void start(GameActivity a) {
		act = a;
		game.start();
	}

	/**
	 * Static getter for the game activity.
	 * 
	 * @return game activity object.
	 */
	public static GameActivity getGameActivity() {
		return act;
	}

	/**
	 * Static getter for the game controller.
	 * 
	 * @return game controller object
	 */
	public static GameController getGameController() {
		return controller;
	}

	/**
	 * Static getter for the current game type.
	 * 
	 * @return game type
	 */
	public static GameType getGameType() {
		return type;
	}

	/**
	 * Static getter for the current game.
	 * 
	 * @return game object
	 */
	public static Game getGame() {
		return game;
	}

	/**
	 * Static setter for a new local player object.
	 * 
	 * Will initialize a new local player object with the input text as name.
	 * 
	 * @param m
	 *            the player name
	 */
	public static void setMe(String m) {
		me = new HumanLocal(m, game);
	}

	/**
	 * Static getter for the current local player.
	 * 
	 * @return local player object
	 */
	public static HumanLocal getMe() {
		return me;
	}

	/**
	 * Static method to switch activity.
	 * 
	 * This function will create a new intent and start the activity.
	 * 
	 * @param act
	 *            current activity object
	 * @param class1
	 *            activity class that will be started
	 */
	public static void start(Activity act, Class<?> class1) {
		act.startActivity(new Intent(act.getApplicationContext(), class1));
	}
}