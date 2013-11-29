package tk.agarsia.tictac2.model.player.bot.smartV2;

import tk.agarsia.tictac2.model.Game;
import tk.agarsia.tictac2.model.player.bot.AbstractBot;

public class BotSmart extends AbstractBot {

	private int[][] decisiongrid;
	private boolean debug;
	private boolean hard;
	private boolean notFirst;
	private int mark;
	
	
	public BotSmart(Game game, boolean hard) {
		this(game,hard,false);
	}

	public BotSmart(Game game, boolean hard, boolean debug) {
		super(game);
		this.hard = hard;
		this.debug = debug;
		notFirst = false;
		mark = 0;
		setPlayerType(2);
		setName("SmartBotV2["+Integer.toHexString(hashCode())+"]");
		decisiongrid = DecisionGrid.get(game);
	}

	@Override
	public void myTurn() {
		if(mark++==0) {
			if(hard&&notFirst)
				DecisionGrid.refactor(decisiongrid, game.getBoard(), game.getMarksPerTurn(),game.getWinLength());

			notFirst = true;

			if(debug) {
				System.out.println(getName()+": Current DecisionTree:");
				DecisionGrid.print(decisiongrid);
			}	
		}

		mark%=game.getMarksPerTurn();
		
		if(game.getGameRunning()) {
			int[] pos = DecisionGrid.decide(decisiongrid, game.getBoard());
			myChoice(pos[1],pos[2]);
		}
	}

	@Override
	public boolean myChoice(int row, int column) {
		if(debug)
			System.out.println("Decided for "+row+","+column);
		return game.placeMark(row, column);
	}

}
