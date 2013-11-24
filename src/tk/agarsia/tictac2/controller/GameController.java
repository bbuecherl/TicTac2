package tk.agarsia.tictac2.controller;

import tk.agarsia.tictac2.model.Game;

public class GameController {
	private Game game;
	
	public GameController(Game game) {
		this.game = game;
	}
	
	public void handleClick(int posX, int posY) {
		//handle click
		if(game.getGameRunning())
			game.handleLocalPlayerClick(posX,posY);
		
		//now test for winners
		if(!game.getGameRunning()) {
			if(game.getWinner()==null) {
				//no winner
			} else {
				//someone won!
			}
		}
	}
}
