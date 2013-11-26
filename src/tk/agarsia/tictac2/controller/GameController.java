package tk.agarsia.tictac2.controller;

import tk.agarsia.tictac2.model.Game;

/**
 * Class to enable interaction between model, view and controller.
 * 
 * This controller has to be initialized in ApplicationControl. It handles click
 * events off BoardTouchListener and tests for winners after every move.
 * 
 * @author agarsia (Bernhard Bücherl)
 * @version 1.0
 * @since 1.0
 */
public class GameController {
	private Game game;

	/**
	 * Custom constructor
	 * 
	 * creates a new controller object and initializes its reference to the
	 * current game.
	 * 
	 * @param game
	 *            current game object.
	 */
	public GameController(Game game) {
		// XXX could be replaced by ApplicationControl.getGame()
		this.game = game;
	}

	/**
	 * Method to handle a click event on a board field
	 * 
	 * This method is used by BoardTouchListener.onTouch and will perform
	 * handleLocalPlayerClick on game. If field could be marked it will vibrate
	 * the Vibration.CLICK pattern, otherwise the Vibration.ERROR pattern.
	 * Afterwards it test if the game is still running, otherwise it will finish
	 * the game and vibrate the Vibration.WON pattern.
	 * 
	 * @param posX
	 *            x coordinate of field [0:(boardDim-1)]
	 * @param posY
	 *            y coordinate of field [0:(boardDim-1)]
	 */
	public void handleClick(int posX, int posY) {
		// handle click
		if (game.getGameRunning())
			if (game.handleLocalPlayerClick(posX, posY))
				Vibration.pattern(Vibration.CLICK);
			else
				Vibration.pattern(Vibration.ERROR);

		// now test for winners
		if (!game.getGameRunning()) {
			if (ApplicationControl.getGameActivity() != null) {
				ApplicationControl.getGameActivity().gameFinished(
						game.getWinner());
				Vibration.loop(Vibration.END);
			}
		}
	}
}