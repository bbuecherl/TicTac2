package tk.agarsia.tictac2.controller;

import java.util.ArrayList;

import android.app.Activity;

/**
 * Abstract class to control the application stack
 * 
 * All Activities that are paused are registered to this stack. When the
 * MenuActivity is started, it calls the clear stack method.
 * 
 * @author agarsia (Bernhard Bücherl)
 * @version 1.0
 * @since 1.0
 */
public abstract class AppStackController {
	private static ArrayList<Activity> stack = new ArrayList<Activity>();
	private static Object lock = new Object();

	/**
	 * Static method to add an activity to the stack list
	 * 
	 * @param act
	 *            activity
	 */
	public static void toStack(Activity act) {
		synchronized (lock) {
			stack.add(act);
		}
	}

	/**
	 * Static method to clear the activity stack.
	 * 
	 * The method finishes all activities registered on the stack.
	 */
	public static void clearStack() {
		synchronized (lock) {
			for (Activity a : stack)
				a.finish();

			stack.clear();
		}
	}

	/**
	 * Static method to clear the activity stack of only one activity type.
	 * 
	 * Finishes all activities of the given class and clears them from stack.
	 * Testing super classes for given depth. d=0 tests sub class only.
	 * (Currently unnecessary for the project, but it is a nice sample for other
	 * projects)<br />
	 * Example usage: <br />
	 * <code>
	 * AppStackController.clearStack(0,MainActivity.class);
	 * </code> will finish all activities with the explicit class MainActivity<br />
	 * <code>AppStackController.clearStack(5,MainActivity.class);</code> will
	 * finish all activities with the class/super class (max depth 5)
	 * MainActivity.
	 * 
	 * @param d
	 *            test depth (0 for testing sub class only)
	 * @param cl
	 *            class (or array of classes)
	 */
	public static void clearStack(int d, Class<Activity>...cl) {
		Class<?> tmp;
		synchronized (lock) {
			for (Class<Activity> c : cl) {
				for (Activity a : stack) {
					tmp = a.getClass();
					if (tmp == c) {
						a.finish();
						stack.remove(a);
					} else if (d > 0 && tmp != Activity.class) {
						for (int i = 0; i < d; i++) {
							tmp = tmp.getSuperclass();
							if (tmp == Activity.class) {
								break; // prevent testing for super of
										// Activities
							} else {
								if (tmp == c) {
									a.finish();
									stack.remove(a);
								}
							}
						}
					}
				}
			}
		}
	}
}