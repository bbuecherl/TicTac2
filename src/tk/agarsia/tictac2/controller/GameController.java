package tk.agarsia.tictac2.controller;

import java.io.IOException;

import tk.agarsia.tictac2.controller.feedback.Sound;
import tk.agarsia.tictac2.controller.feedback.SoundController;
import tk.agarsia.tictac2.controller.feedback.Vibration;
import tk.agarsia.tictac2.model.Game;
import android.util.Log;

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
	private Sound[] sounds;

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
		sounds = new Sound[3];
		
		try {		
			sounds[0] = SoundController.newSound("click.wav");
			sounds[1] = SoundController.newSound("won.mp3");
			sounds[2] = SoundController.newSound("failed.wav");
		} catch(IOException e) {
			Log.i("GameController","failed to load sounds");
			e.printStackTrace();
		}
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
		if(sounds[0]!=null)
			sounds[0].play(Sound.LOUD);
		// handle click
		if (game.getGameRunning())
			if (game.handleLocalPlayerClick(posX, posY)) {
				Vibration.pattern(Vibration.CLICK);
			} else {
				Vibration.pattern(Vibration.ERROR);
			}
		
		// now test for winners
		if (!game.getGameRunning()) {
			if (ApplicationControl.getGameActivity() != null) {
				ApplicationControl.getGameActivity().gameFinished(
						game.getWinner());
				Vibration.loop(Vibration.END);
				if(sounds[1]!=null&&sounds[2]!=null)
					if(game.getWinner()==ApplicationControl.getMe())
						sounds[1].play(Sound.LOUD);
					else
						sounds[2].play(Sound.LOUD);
			}
		}
	}
}