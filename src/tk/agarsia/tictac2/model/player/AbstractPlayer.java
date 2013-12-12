package tk.agarsia.tictac2.model.player;

import tk.agarsia.tictac2.model.Game;

public abstract class AbstractPlayer implements PlayerInterface {

	protected Game game;
	protected String name;
	protected int gamesWon = 0;
	protected String winningFields = "";
	
	/**
	 * 0 = local human player
	 * 1 = remote human player
	 * 2 = local bot
	 */
	private int playerType;
	
	public AbstractPlayer(Game game){
		this.game = game;
	}
	
	public void setPlayerType(int type){
		playerType = type;
	}
	
	public int getPlayerType(){
		return playerType;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void incrementGameWon(){
		gamesWon ++;
	}
	
	public void resetGamesWon(){
		gamesWon = 0;
	}
	
	public void setWinningFields(String winningFields){
		this.winningFields = winningFields;
	}
	
	public String getWinningFields(){
		return winningFields;
	}
	
}
