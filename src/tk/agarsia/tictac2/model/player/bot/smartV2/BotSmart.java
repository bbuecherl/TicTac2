package tk.agarsia.tictac2.model.player.bot.smartV2;

import tk.agarsia.tictac2.model.Game;
import tk.agarsia.tictac2.model.player.bot.AbstractBot;

/**
 * Class representing an intelligent bot
 * 
 * @author agarsia (Bernhard Bücherl)
 * @version 1.0
 * @since 2.0
 */
public class BotSmart extends AbstractBot {

	private int[][] decisiongrid;
	private boolean debug;
	private boolean hard;
	private int mark;
	private boolean first;
	
	/**
	 * Constructor
	 * 
	 * @param game
	 *            game object
	 * @param hard
	 *            true if bot should be intelligent or not
	 */
	public BotSmart(Game game, boolean hard) {
		this(game, hard, false);
	}

	/**
	 * Constructor
	 * 
	 * @param game
	 *            game object
	 * @param hard
	 *            true if bot should be intelligent or not
	 * @param debug
	 *            true to show debug messages
	 */
	public BotSmart(Game game, boolean hard, boolean debug) {
		super(game);
		this.hard = hard;
		this.debug = debug;
		mark = 0;
		setPlayerType(2);
		setName("SmartBotV2[" + Integer.toHexString(hashCode()) + "]");
		first = true;
	}

	@Override
	public void myTurn() {
		if(first)
			decisiongrid = DecisionGrid.get(game);

		first = false;
		if (mark++ == 0) {

			if (hard && game.getBoard().getHistory().size() != 0)
				DecisionGrid.refactor(decisiongrid, game.getBoard(),
						game.getMarksPerTurn(), game.getWinLength());

			if (debug) {
				System.out.println(getName() + ": Current DecisionTree:");
				DecisionGrid.print(decisiongrid);
			}
		}

		mark %= game.getMarksPerTurn();

		if (game.getGameRunning()) {
			int[] pos = DecisionGrid.decide(decisiongrid, game.getBoard());
			myChoice(pos[1], pos[2]);
		}
	}

	@Override
	public boolean myChoice(int row, int column) {
		if (debug)
			System.out.println("Decided for " + row + "," + column);
		return game.placeMark(row, column);
	}
}