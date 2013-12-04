package tk.agarsia.tictac2.model.player.bot.smartV2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import tk.agarsia.tictac2.model.Game;
import tk.agarsia.tictac2.model.board.Board;

/**
 * Abstract Class representing the real logic behind the BotSmart
 * 
 * @author agarsia (Bernhard Bücherl)
 * @version 1.0
 * @since 2.0
 */
public class DecisionGrid {
	private static final int[][][][] PREGRIDS = initGrids();
	private static final int[][][][][] PREWINS = initWins();
	private static Random rand = new Random();

	/**
	 * Static method to refactor the decision grid based on history events.
	 * 
	 * TODO performance optimization
	 * 
	 * @param grid
	 *            old decision grid that wil be refactored
	 * @param board
	 *            current board object
	 * @param mpt
	 *            marks per turn
	 * @param wLen
	 *            win length
	 */
	public static void refactor(int[][] grid, Board board, int mpt, int wLen) {
		ArrayList<int[]> h = board.getHistory();

		// use rules for other player history
		if(h.size()>=mpt)
			for (int a = h.size() - mpt; a < h.size(); a++) {
				System.out.println(Arrays.toString(h.get(a)));
				int mark = h.get(a)[0];
				int x = h.get(a)[1];
				int y = h.get(a)[2];
				ArrayList<int[][]> w = getWins(grid.length, wLen, x, y);
	
				// rule b. (set other players fields to 0)
				grid[x][y] = 0;
	
				for (int[][] b : w) {
					System.out.println(Arrays.deepToString(b));
					// rule c. (increment all affected fields)
					int[] t1 = new int[2];
					t1[0] = -1;
					t1[1] = -1;
					int t2 = 0;
					int doubleIt = 0;
					
					for (int c = 0; c < b.length; c++) {
						int tx = b[c][0];
						int ty = b[c][1];
						
						if ((tx == x && ty == y) || grid[tx][ty] == 1000)
							continue;
	
						//test for doubles (d.)
						for(int[][] wd : getWins(grid.length, wLen, tx, ty)) {
							if(!wd.equals(b))
								for(int d = 0; d < b.length; d++) {
									if ((wd[d][0] == tx && wd[d][0] == ty) || grid[tx][ty] == 1000)
										continue;
	
									if (board.getField(wd[d][0], wd[d][1]).isFree()) {
										
									} else if (board.getField(wd[d][0], wd[d][1]).getValue() == mark) {
										doubleIt++; // if enemy got another field in there..
									} else if (board.getField(wd[d][0], wd[d][1]).getValue() == mark % 2 + 1) {
										doubleIt--; // if we got another field in there...
									}
								}
						}
						
						if (board.getField(tx, ty).isFree()) {
							grid[tx][ty]++;
							t1[0] = tx;
							t1[1] = ty;
						} else if (board.getField(tx, ty).getValue() == mark) {
							t2++; // if enemy got another field in there..
						} else if (board.getField(tx, ty).getValue() == mark % 2 + 1) {
							t2--; // if we got another field in there...
						}
					}
	
					//e. 
					if (t2 == 1 && t1[0] != -1 && t1[1] != -1)
						grid[t1[0]][t1[1]] = 1000;
					
					//d.
					if(doubleIt>0 && t1[0] != -1 && t1[1] != -1) {
						grid[t1[0]][t1[1]] *=2;
					}
				}
	
			}

		// use rules for the bots history
		if(h.size()>=2*mpt)
			for (int a = h.size() - 2*mpt; a < h.size()-mpt; a++) {
				System.out.println(Arrays.toString(h.get(a)));
				int mark = h.get(a)[0];
				int x = h.get(a)[1];
				int y = h.get(a)[2];
				ArrayList<int[][]> w = getWins(grid.length, wLen, x, y);
	
				// rule b. (set bot players fields to 0)
				grid[x][y] = 0;
	
				for (int[][] b : w) {
					System.out.println(Arrays.deepToString(b));
					int[] t1 = new int[2];
					t1[0] = -1;
					t1[1] = -1;
					int t2 = 0;
					int tribbleIt = 0;
					
					for (int c = 0; c < b.length; c++) {
						int tx = b[c][0];
						int ty = b[c][1];
						
						if ((tx == x && ty == y) || grid[tx][ty] == 2000)
							continue;
	
						//test for squares (d.)
						for(int[][] wd : getWins(grid.length, wLen, tx, ty)) {
							if(!wd.equals(b))
								for(int d = 0; d < b.length; d++) {
									if ((wd[d][0] == tx && wd[d][0] == ty) || grid[tx][ty] == 2000)
										continue;
	
									if (board.getField(wd[d][0], wd[d][1]).isFree()) {
										
									} else if (board.getField(wd[d][0], wd[d][1]).getValue() == mark) {
										tribbleIt--; // if enemy got another field in there..
									} else if (board.getField(wd[d][0], wd[d][1]).getValue() == mark % 2 + 1) {
										tribbleIt++; // if we got another field in there...
									}
								}
						}
						
						if (board.getField(tx, ty).isFree()) {
							t1[0] = tx;
							t1[1] = ty;
						} else if (board.getField(tx, ty).getValue() == mark) {
							t2--; // if enemy got another field in there..
						} else if (board.getField(tx, ty).getValue() == mark % 2 + 1) {
							t2++; // if we got another field in there...
						}
					}
	
					//e. 
					if (t2 == 1 && t1[0] != -1 && t1[1] != -1)
						grid[t1[0]][t1[1]] = 2000;
					
					//d.
					if(tribbleIt>0 && t1[0] != -1 && t1[1] != -1) {
						grid[t1[0]][t1[1]] *=3;
					}
				}
	
			}
	}

	/**
	 * Static method to fetch an arraylist of all winning moves for a specific
	 * field
	 * 
	 * @param len
	 *            board size
	 * @param wLen
	 *            win length
	 * @param x
	 *            x coordinate of the field
	 * @param y
	 *            y coordinate of the field
	 * @return winning moves
	 */
	private static ArrayList<int[][]> getWins(int len, int wLen, int x, int y) {
		ArrayList<int[][]> out = new ArrayList<int[][]>();

		for (int i = 0; i < PREWINS[len - 3][wLen - 3].length; i++) {
			for (int j = 0; j < wLen; j++) {
				if (PREWINS[len - 3][wLen - 3][i][j][0] == x
						&& PREWINS[len - 3][wLen - 3][i][j][1] == y)
					out.add(PREWINS[len - 3][wLen - 3][i]);
			}
		}

		return out;
	}

	/**
	 * Static method to make a decision based on a desicion grid.
	 * 
	 * @param grid
	 *            decision grid
	 * @param board
	 *            board object
	 * @return decided position
	 */
	public static int[] decide(int[][] grid, Board board) {
		int[][] out = new int[board.getFreeFieldCount()][3];
		int sameCount = 0;

		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				if (board.getField(x, y).isFree())
					if (out[0][0] < grid[x][y]) {
						out[0][0] = grid[x][y];
						out[0][1] = x;
						out[0][2] = y;
						sameCount = 0;
					} else if (out[0][0] == grid[x][y]) {
						if (rand.nextInt(1) == 0) {
							out[++sameCount][0] = grid[x][y];
							out[sameCount][1] = x;
							out[sameCount][2] = y;
						}
					}
			}
		}

		if (sameCount == 0)
			return out[0];
		return out[rand.nextInt(sameCount)];
	}

	/**
	 * Static getter for a pre decision grid
	 * 
	 * @param game
	 *            current game object
	 * @return pre decision grid
	 */
	public static int[][] get(Game game) {
		return PREGRIDS[game.getBoardDim() - 3][game.getWinLength() - 3];
	}

	/**
	 * Static m ethod to print a decision grid
	 * 
	 * @param grid
	 *            decision grid
	 */
	public static void print(int[][] grid) {
		for (int[] row : grid) {
			for (int field : row) {
				System.out.printf(" %04d ", field);
			}
			System.out.println();
		}
	}

	/**
	 * Static method to initialize all predecision grids.
	 * 
	 * @return 4 dimensional array storing all predecision grids
	 */
	public static int[][][][] initGrids() {
		int[][][][] grids = new int[4][][][];

		// 3x3
		grids[0] = new int[1][][];
		grids[0][0] = new int[][] { { 3, 2, 3 }, { 2, 4, 2 }, { 3, 2, 3 } };

		// 4x4s
		grids[1] = new int[2][][];
		grids[1][0] = new int[][] { { 3, 4, 4, 3 }, { 4, 7, 7, 4 },
				{ 4, 7, 7, 4 }, { 3, 4, 4, 3 } };
		grids[1][1] = new int[][] { { 3, 2, 2, 3 }, { 2, 3, 3, 2 },
				{ 2, 3, 3, 2 }, { 3, 2, 2, 3 } };

		// 5x5s
		grids[2] = new int[3][][];
		grids[2][0] = new int[][] { { 3, 4, 6, 4, 3 }, { 4, 7, 9, 7, 4 },
				{ 6, 9, 12, 9, 6 }, { 4, 7, 9, 7, 4 }, { 3, 4, 6, 4, 3 } };
		grids[2][1] = new int[][] { { 3, 4, 3, 4, 3 }, { 4, 6, 6, 6, 4 },
				{ 3, 6, 8, 6, 3 }, { 4, 6, 6, 6, 4 }, { 3, 4, 3, 4, 3 } };
		grids[2][2] = new int[][] { { 3, 2, 2, 2, 3 }, { 2, 3, 2, 3, 2 },
				{ 2, 2, 4, 2, 2 }, { 2, 3, 2, 3, 2 }, { 3, 2, 2, 2, 3 } };

		// 6x6s
		grids[3] = new int[4][][];
		grids[3][0] = new int[][] { { 3, 4, 6, 6, 4, 3 }, { 4, 7, 8, 8, 7, 4 },
				{ 6, 8, 12, 12, 8, 6 }, { 6, 8, 12, 12, 8, 6 },
				{ 4, 7, 8, 8, 7, 4 }, { 3, 4, 6, 6, 4, 3 } };
		grids[3][1] = new int[][] { { 3, 4, 5, 5, 4, 3 }, { 4, 6, 7, 7, 6, 4 },
				{ 5, 7, 9, 9, 7, 5 }, { 5, 7, 9, 9, 7, 5 },
				{ 4, 6, 7, 7, 6, 4 }, { 3, 4, 5, 5, 4, 3 } };
		grids[3][2] = new int[][] { { 3, 4, 3, 3, 4, 3 }, { 4, 6, 5, 5, 6, 4 },
				{ 3, 5, 7, 7, 5, 3 }, { 3, 5, 7, 7, 5, 3 },
				{ 4, 6, 5, 5, 6, 4 }, { 3, 4, 3, 3, 4, 3 } };
		grids[3][3] = new int[][] { { 3, 2, 2, 2, 2, 3 }, { 2, 3, 2, 2, 3, 2 },
				{ 2, 2, 3, 3, 2, 2 }, { 2, 2, 3, 3, 2, 2 },
				{ 2, 3, 2, 2, 3, 2 }, { 3, 2, 2, 2, 2, 3 } };

		return grids;
	}

	/**
	 * Static method to initialize all winning move arrays
	 * 
	 * @return all winning moves in a 5 dimensional array
	 */
	public static int[][][][][] initWins() {
		int[][][][][] wins = new int[4][][][][];

		// 3x3
		wins[0] = new int[1][][][];
		wins[0][0] = new int[8][3][2];
		wins[0][0][0] = new int[][] { { 0, 0 }, { 0, 1 }, { 0, 2 } };
		wins[0][0][1] = new int[][] { { 1, 0 }, { 1, 1 }, { 1, 2 } };
		wins[0][0][2] = new int[][] { { 2, 0 }, { 2, 1 }, { 2, 2 } };
		wins[0][0][3] = new int[][] { { 0, 0 }, { 1, 0 }, { 2, 0 } };
		wins[0][0][4] = new int[][] { { 0, 1 }, { 1, 1 }, { 2, 1 } };
		wins[0][0][5] = new int[][] { { 0, 2 }, { 1, 2 }, { 2, 2 } };
		wins[0][0][6] = new int[][] { { 0, 0 }, { 1, 1 }, { 2, 2 } };
		wins[0][0][7] = new int[][] { { 0, 2 }, { 1, 1 }, { 2, 0 } };

		return wins;
	}
}