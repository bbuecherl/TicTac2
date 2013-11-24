/*package tk.agarsia.tictac2.model;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import tk.agarsia.tictac2.model.player.AbstractPlayer;
import tk.agarsia.tictac2.model.player.bot.BotRandom;
import tk.agarsia.tictac2.model.player.bot.BotSmart;
import tk.agarsia.tictac2.model.player.human.HumanLocal;


public class DemoMain {

	public static void main(String[] args) throws IOException {

		Game g = new Game();		
		g.initModel(0, 4, 3, 1, 1);	//int interval, int boardDim, int winLength, int marksPerTurn, int startPlayerIndex	
		
		AbstractPlayer player1 = new BotRandom(g);//HumanLocal("Fritz", g);
		AbstractPlayer player2 = new BotSmart(g); //HumanLocal("Julia", g);
		g.setPlayers(player1, player2);		
		g.start();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));			
		String input = "";		
		
		while(g.getWinner() == null && g.getGameRunning()){
			input = br.readLine();		
			g.handleLocalPlayerClick(Integer.parseInt(input.split(" ")[0]), Integer.parseInt(input.split(" ")[1])); //expects exact format "row column" so for instance "0 2"		
		}
		if(g.getWinner() == null)
			System.out.println("board is full without winner");
		else
			System.out.println(g.getWinner().getName() + " won with the following chain of " + g.getBoard().getWinningIsland().getSize() + " fields: \n" + g.getWinner().getWinningFields());

		
	}

}*/