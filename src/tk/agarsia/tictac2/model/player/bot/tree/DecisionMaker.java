/*package tk.agarsia.tictac2.model.player.bot.tree;

import java.util.Arrays;

import tk.agarsia.tictac2.model.board.BoardParser;

*//**
 * In development...
 * 
 * Can be executed by:
 * 
 * 	public static void main(String... args) throws Exception {
 *		DecisionMaker.DEBUG = true;
 *		DecisionMaker.DEBUG_AWESOME = true;
 *		DecisionMaker.DEPTH = 3;
 *		
 *		DecisionMaker.generateTree(3,1,1);
 *	}
 *
 * (change the depth if you feel brave.)
 * 
 * @author agarsia (Bernhard B??cherl)
 *//*
public class DecisionMaker {

	//DEBUG
	public static boolean DEBUG = false;
	public static boolean DEBUG_AWESOME = false;
	public static int DEPTH = 3;
	
	private static final String DEBUG_AWESOME_INFO = "-| Num | Weight |  Parent   |   Board   |";
	private static final String DEBUG_AWESOME_F = "-| %03d | %6d | (%03d,%03d) |%11s|";
	private static final String DEBUG_AWESOME_FL = "-|     |        |           |%11s|";
	
	//INDIZE
	public static final int INFO_USED = 0;
	public static final int INFO_WEIGHT = 1;
	public static final int INFO_PARENTX = 2;
	public static final int INFO_PARENTY = 3;
	public static final int BOARD_OFFSET = 4;
	
	public static int[][][] generateTree(int board, int player, int mpt) {
		int depth = DEPTH;
		int size = BOARD_OFFSET + board*board*//**2*//*; //calculate z size (INFO+board+children)
		int boardSize = board * board;
		int length = 1;
		
		//init x
		int[][][] tree = new int[depth][][];
		
		//make the first node
		tree[0] = new int[1][size];
		tree[0][0][INFO_USED] = 1; //is this node initialized & used?
		tree[0][0][INFO_WEIGHT] = 0; //init weight
		tree[0][0][INFO_PARENTX] = -1; //no parent
		tree[0][0][INFO_PARENTY] = -1; //no parent
		copyIn(tree[0][0],new int[boardSize],BOARD_OFFSET);
		
		
		//iterate over x
		for(int x = 1; x < depth; x++) {
			int lastLen = length;
			length = (int) Math.pow(depth*depth,x);
			
			//init y, z
			tree[x] = new int[length][size];
			
			int yOffset = 0; //init offset
			//iterate over lastLen to generate nodes from their parents
			for(int l = 0; l < lastLen; l++) {
				yOffset += generateDecisionsFor(tree[x-1][l],tree[x],yOffset,x-1,l, player, boardSize);
			}
			player = player%2+1; //next player (TODO include MPT!)
		}
		
		if(DEBUG)
			if(DEBUG_AWESOME)
				printAwesome(tree, board);
			else
				print(tree);
		
		return tree;
	}

	public static int generateDecisionsFor(int[] parent, int[][] arr, int offset, int px, int py, int player, int boardSize) {
		int[] empties = BoardParser.getEmptyIndizeOpt(parent,BOARD_OFFSET,boardSize);
		
		
		for(int i = 0; i < empties.length; i++) {
			arr[offset+i][INFO_USED] = 1; //initialized
			arr[offset+i][INFO_WEIGHT] = 0; //init weight again...
			arr[offset+i][INFO_PARENTX] = px; //parent x
			arr[offset+i][INFO_PARENTY] = py; //parent y
			copy(arr[offset+i], parent, BOARD_OFFSET, boardSize); //copy board from parent
			arr[offset+i][empties[i]] = player;
		}

		return empties.length; //return offset for next round
	}
	
	public static void copy(int[] arr, int[] arr2, int offset, int boardSize) {
		for(int i = 0; i< boardSize; i++) {
			arr[i+offset] = arr2[i+offset];
		}
	}
	
	public static void copyIn(int[] arr, int[] arr2, int offset) {
		for(int i = 0; i< arr2.length; i++) {
			arr[i+offset] = arr2[i];
		}
	}
	
	public static void print(int[][][] arr) {
		for(int i = 0; i<arr.length; i++) {
			System.out.print(i+" | ");
			print(arr[i]);
			System.out.println();
		}
	}
	
	public static void print(int[][] arr) {
		for(int j = 0; j <arr.length;j++) {
			if(j!=0)
				System.out.print(" ,");
			System.out.print(j+": ");
			print(arr[j]);
		}
	}
	
	public static void print(int[] arr) {
		System.out.print(Arrays.toString(arr));
	}
	
	public static void printAwesome(int[][][] arr, int board) {
		System.out.println("   "+DEBUG_AWESOME_INFO);
		String[] forms = new String[board-1];
		String[] temp;
		for(int x = 0; x<arr.length; x++) {
			System.out.printf("%03d", x);
			for(int y = 0; y<arr[x].length; y++) {
				temp = printAwesomeString(arr[x][y],y,board);
				System.out.print(temp[0]);
				
				for(int z = 0; z < board-1; z++) {
					if(y==0)
						forms[z] = "";
					forms[z] += temp[z+1];
					
				}
			}			
			System.out.println();
			for(int a = 1; a<board; a++) {
				System.out.println("   "+forms[a-1]);
			}
		}
	}
	
	public static String[] printAwesomeString(int[] arr, int y, int board) {
		String[] temp = new String[board];
		
		String b = "";
		
		for(int i = 0; i<board; i++) {
			if(i!=0)
				b += " ";
			b += ""+arr[BOARD_OFFSET+i];
		}
		
		temp[0] = String.format(DEBUG_AWESOME_F, y,arr[INFO_WEIGHT], arr[INFO_PARENTX],arr[INFO_PARENTY], b);

		for(int j = 1; j<board; j++) {
			b = "";
			for(int i = 0; i<board; i++) {
				if(i!=0)
					b += " ";
				b += ""+arr[BOARD_OFFSET+i+j*board];
			}			
			temp[j] = String.format(DEBUG_AWESOME_FL,b);
		}
		
		return temp;
	}
}*/