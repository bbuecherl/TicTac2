package tk.agarsia.tictac2.model.player.bot;

import java.util.Random;

import tk.agarsia.tictac2.model.Game;
import tk.agarsia.tictac2.model.board.Field;


public class BotRandom extends AbstractBot{

	public BotRandom(Game game) {
		super(game);
		setPlayerType(2);
		setName("RandomBot" + this.hashCode());
	}


	@Override
	public void myTurn() {
		int choice = 1 + (int) (new Random().nextDouble() * board.getEmptyFieldCount());	
		Field chosenField = board.getChosenFreeField(choice);		
		myChoice(chosenField.getRow(), chosenField.getColumn());
	}

	@Override
	public boolean myChoice(int row, int column) {
		return game.placeMark(row, column);
	}

}


