package com.tictac2.controller;


public class ApplicationControl {
	public static enum GameType {
		INIT,SINGLEPLAYER,MULTIPLAYER;
	}
	
	private static GameType type;

	
	public static void newGame(GameType t) {
		type = t;
	}
	
	public static GameType getGameType() {
		return type;
	}
}
