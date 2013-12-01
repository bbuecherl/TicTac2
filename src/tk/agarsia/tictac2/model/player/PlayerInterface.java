package tk.agarsia.tictac2.model.player;

public interface PlayerInterface {

	/**
	 * Notifies the player that it's his turn. Once he made his choice he will call myChoice that will call a method on game
	 */
	public void myTurn();
	
	/**
	 * When the player made his choice (through click on the smartphone or a finished "thinking" process of a bot) he uses
	 * this method to let game know about his choice
	 * 
	 * @param row of chosen cell
	 * @param column of chosen cell
	 * @return true: the field was marked with this players index, false: the field was already taken
	 */
	public boolean myChoice(int row, int column);
	
}
