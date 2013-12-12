package tk.agarsia.tictac2.controller.feedback;

import tk.agarsia.tictac2.controller.ApplicationControl;
import android.content.Context;
import android.os.Vibrator;

/**
 * Class to enable vibration support.
 * 
 * This controller requires initialisation by ApplicationControl.init().
 * It enables the phone to vibrate in patterns and to cancel vibration.
 * This requires the android.permission.VIBRATE permission.
 * 
 * @author agarsia (Bernhard Bücherl)
 * @version 1.0
 * @since 1.0
 */
public class Vibration {
	/**
	 * click vibration pattern {0,20,0}
	 */
	public static final long[] CLICK = new long[] { 0, 20, 0 };
	/**
	 * error vibration pattern {0,50,10,50,0}
	 */
	public static final long[] ERROR = new long[] { 0, 50, 10, 50, 0 };
	/**
	 * game end vibration pattern {0,150,20,80,30,50,20,100,70,200,0}
	 */
	public static final long[] END = new long[] {0, 150,20,80,30,50,20,100,70,200,0};

	// static instance of the vibrator
	private static Vibrator vibrator;

	/**
	 * Function to initialize the static instance of the vibrator service
	 * 
	 * @param context context object of the application
	 */
	public static void init(Context context) {
		vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
	}

	/**
	 * Function to vibrate for a given amount of time
	 * 
	 * @param time
	 *            time in ms
	 */
	public static void time(int time) {
		if (vibrator != null && ApplicationControl.getBooleanPref("pref_vibrate"))
			vibrator.vibrate(time);
	}

	/**
	 * Function to loop vibrate a given pattern.
	 * 
	 * @param pattern
	 *            pattern
	 */
	public static void loop(long[] pattern) {
		if (vibrator != null && ApplicationControl.getBooleanPref("pref_vibrate"))
			vibrator.vibrate(pattern, 0);
	}

	/**
	 * Function to vibrate a pattern once.
	 * 
	 * @param pattern
	 *            pattern
	 */
	public static void pattern(long[] pattern) {
		if (vibrator != null && ApplicationControl.getBooleanPref("pref_vibrate"))
			vibrator.vibrate(pattern, -1);
	}

	/**
	 * Function to cancel current vibration.
	 */
	public static void cancel() {
		if (vibrator != null)
			vibrator.cancel();
	}
}