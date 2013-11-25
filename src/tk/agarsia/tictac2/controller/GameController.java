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
			if(game.handleLocalPlayerClick(posX,posY))
				Vibration.pattern(Vibration.CLICK);
			else
				Vibration.pattern(Vibration.ERROR);
		
		//now test for winners
		if(!game.getGameRunning()) {
			if(ApplicationControl.getGameActivity()!=null) {
				ApplicationControl.getGameActivity().gameFinished(game.getWinner());
				Vibration.pattern(Vibration.WON);
			}
		}
	}
}
