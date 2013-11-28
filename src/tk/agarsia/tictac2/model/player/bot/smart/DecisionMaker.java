package tk.agarsia.tictac2.model.player.bot.smart;

import java.util.Arrays;

public class DecisionMaker {

	public static boolean DEBUG = false;

	public static final int BOARD_OFFSET = 3;
	
	public static void mkTree(int[] board, int player, int mpr) {
		int depth = 3;
		int size = BOARD_OFFSET + board.length; //calculate z size
		
		//init x
		int[][][] tree = new int[depth][][];
		
		//make the first node
		tree[0] = new int[1][size];
		tree[0][0][0] = board.length; //save length in first
		tree[0][0][1] = player;
		tree[0][0][2] = mpr;
		copyIn(tree[0][0],board,BOARD_OFFSET);
//		for(int i = 0; i<board.length; i++) {
//			tree[0][0][i+1] = board[i];  
//		}
		
		//iterate over x
		for(int x = 1; x < depth; x++) {
			int length = (int) Math.pow(x+1, depth);
			
			//init y, z
			tree[x] = new int[length][size];
			
			//iterate over y
			for(int y = 0; y < length; y++) {
				//TODO
			}
		}
		
		if(DEBUG)
			toString(tree);
	}
	
	
	public static void copyIn(int[] arr, int[] arr2, int offset) {
		for(int i = 0; i< arr2.length; i++) {
			arr[i+offset] = arr2[i];
		}
	}
	
	public static void toString(int[][][] arr) {
		for(int i = 0; i<arr.length; i++) {
			for(int j = 0; j <arr[i].length;j++) {
				if(j!=0)
					System.out.print("  | ");
				System.out.print("("+i+","+j+"): "+Arrays.toString(arr[i][j]));
			}
			System.out.println();
		}
	}
}