package tk.agarsia.tictac2.controller;

import java.util.ArrayList;

import android.app.Activity;

/**
 * Abstract class to control the application stack
 * 
 * All Activities that are paused are registered to this stack.
 * When the MenuActivity is started, it calls the clear stack method.
 * 
 * @author agarsia (Bernhard Bücherl)
 * @version 1.0
 * @since 1.0
 */
public abstract class AppStackController {
	private static ArrayList<Activity> games = new ArrayList<Activity>();
	private static Object lock = new Object();
	
	/**
	 * Static method to add an activity to the stack list
	 * 
	 * @param act activity
	 */
	public static void toStack(Activity act) {
		synchronized(lock) {
			games.add(act);
		}
	}
	
	/**
	 * Static method to clear the activity stack.
	 * 
	 * The method finishes all activities registered on the stack.
	 */
	public static void clearStack() {
		synchronized(lock) {
			for(Activity g : games)
				g.finish();
			
			games.clear();
		}
	}
}