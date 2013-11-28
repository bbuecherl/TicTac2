package tk.agarsia.tictac2.controller;

import java.util.ArrayList;

import tk.agarsia.tictac2.view.MainActivity;

public class AppStackController {
	private static ArrayList<MainActivity> games = new ArrayList<MainActivity>();
	private static Object lock = new Object();
	
	public static void toStack(MainActivity act) {
		synchronized(lock) {
			games.add(act);
		}
	}
	
	public static void clearStack() {
		synchronized(lock) {
			for(MainActivity g : games)
				g.finish();
			
			games.clear();
		}
	}
}