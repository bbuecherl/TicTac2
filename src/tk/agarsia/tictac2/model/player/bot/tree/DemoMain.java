/*package tk.agarsia.tictac2.model.player.bot.tree;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import tk.agarsia.tictac2.model.Game;
import tk.agarsia.tictac2.model.board.BoardParser;
import tk.agarsia.tictac2.model.board.BoardParserV2;
import tk.agarsia.tictac2.model.player.AbstractPlayer;
import tk.agarsia.tictac2.model.player.bot.BotRandom;
import tk.agarsia.tictac2.model.player.bot.BotSmart;
import tk.agarsia.tictac2.model.player.human.HumanLocal;


public class DemoMain {

	public static void main(String[] args) throws IOException {		
		Game g = new Game();		
		g.initModel(0, 4, 4, 1, 1);	//int interval, int boardDim, int winLength, int marksPerTurn, int startPlayerIndex	
		
		AbstractPlayer player1 = new BotSmart(g);//HumanLocal("human player", g);
		AbstractPlayer player2 = new HumanLocal("me", g);
		g.setPlayers(player1, player2);		
		g.start();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));			
		String input = "";		
		
		while(g.getGameRunning()){
			input = br.readLine();
			if(input.equals("export1")){
				((BotSmart) player1).exportLastGraph();
			}
			else 
				if(input.equals("export2")){
					((BotSmart) player2).exportLastGraph();
				}	
				else
					g.handleLocalPlayerClick(Integer.parseInt(input.split(" ")[0]), Integer.parseInt(input.split(" ")[1])); //expects exact format "row column" so for instance "0 2"		
		}
		if(g.getWinner() == null)
			System.out.println("board is full without winner");
		else{
			System.out.println(g.getWinner().getName() + " won with the following chain of " + g.getBoard().getWinningIsland().getSize() + " fields: \n" + g.getWinner().getWinningFields());
		}
	}

}*/