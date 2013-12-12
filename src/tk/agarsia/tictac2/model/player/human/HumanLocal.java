package tk.agarsia.tictac2.model.player.human;

import tk.agarsia.tictac2.model.Game;
import tk.agarsia.tictac2.model.player.AbstractPlayer;

public class HumanLocal extends AbstractPlayer{

	//private int turnCount = 0;
	
	public HumanLocal(String name, Game game) {
		super(game);
		setPlayerType(0);
		setName(name);		
	}

	@Override
	public void myTurn() {
		
		game.awaitingClick();
		
/*		if(turnCount > 3)
			game.awaitingClick();
		else{
			if(turnCount == 0)
				myChoice(2, 1);
			if(turnCount == 1)
				myChoice(0, 2);
			if(turnCount == 2)
				myChoice(1, 0);
			if(turnCount == 3)
				myChoice(3, 1);
						
			turnCount ++;	
		}*/
				
	}

	@Override
	public boolean myChoice(int row, int column) {
		return game.placeMark(row, column);
	}
	


	
}
