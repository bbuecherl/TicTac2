package tk.agarsia.tictac2.model.board;

import java.util.ArrayList;

public class BoardParser {
	
	public static boolean testForEmpty(int[] arr) {
		// SIDENOTE:
		// 'for(int i=0;i<arr.len;i++)' takes 220~240ns
		// even if we predefine arr.len its only at 210~220ns

		// iterating is currently fastest with 170~190ns
		for (int b : arr)
			if (b == 0)
				return true;

		return false;
	}

	public static int getEmptyFields(int[] arr) {
		return getEmptyFields(arr,0,arr.length);
	}

	//SIDENOTE
	//this could replace testForEmpty, testing for empties is getEmptyFields(arr)==0
	public static int getEmptyFields(int[] arr, int offset, int length) {
		int i = 0;
		for (int x = offset; x < offset+length; x++)
			if (arr[x] == 0)
				++i;

		return i;
	}

	//my performance tests result [~300ns]
	public static ArrayList<Integer> getEmptyIndize(int[] arr){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(int i = 0; i < arr.length; i++)
			if(arr[i] == 0)
				temp.add(i);
		return temp;
	}

	public static int[] getEmptyIndizeOpt(int[] arr){		
		return getEmptyIndizeOpt(arr,0,arr.length);
	}

	
	// SIDENOTE: 
	//optimized version, we should not use arraylist, they have way
	//less performance compared to int arrays. 
	//performance tests [~230ns]
	public static int[] getEmptyIndizeOpt(int[] arr, int offset, int length){		
		int[] temp = new int[getEmptyFields(arr,offset,length)];
		int at = 0; //temp index
		for(int i = offset; i < offset+length;i++)
			if(arr[i] == 0)
				temp[at++] = i;
				
		return temp;
	}
	
	public static int testBoardForWinner(int[] arr, int len, int wLen) {
		// this test method executes in 4000~4600ns

		int tfh, tfb, tfv, tfs, field;

		// precalculate some basic stuff...
		int lnw = len - wLen;
		int lnwno = len - (wLen - 1);

		for (int x = 0; x < len; x++) {
			for (int y = 0; y < len; y++) {
				// initialize parent values...
				field = arr[y + x * len];
				tfv = (x < lnwno) ? field : 0;
				tfs = (x < lnwno && y >= lnw && y < len) ? field : 0;
				tfh = (y < lnwno) ? field : 0;
				tfb = (y < lnwno && x < lnwno) ? field : 0;

				// SIDENOTE:
				// could either init test values like above with boundings in
				// mind and test them for 0 in first test statement,
				// or init the test values to field and test the boundings
				// in every test statement at first.
				// current version is performing better, due to only one
				// bounding test per parent value and a simple '!=0' test in the
				// statements.

				if (field != 0) { // abort if field is free (no testing
									// required)
					for (int z = 1; z < wLen; z++) {
						// test horizontal
						if (tfh != 0 && arr[y + x * len + z] != tfh)
							tfh = 0;

						// test diagonal backslash
						if (tfb != 0 && arr[y + (x + z) * len + z] != tfb)
							tfb = 0;

						// test vertical
						if (tfv != 0 && arr[y + (x + z) * len] != tfv)
							tfv = 0;

						// test diagonal slash
						if (tfs != 0 && arr[y + (x + z) * len - z] != tfs)
							tfs = 0;

						// debug
						// System.out.println("("+x+","+y+","+z+") v"+tfv+" s"+tfs+" h"+tfh+" b"+tfb);

					}

					// output the winner if we got one
					if (tfv != 0)
						return tfv;
					if (tfs != 0)
						return tfs;
					if (tfh != 0)
						return tfh;
					if (tfb != 0)
						return tfb;
				}
			}
		}

		// we have no winner :(
		return 0;
	}
}