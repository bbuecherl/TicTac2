package tk.agarsia.tictac2.model.board;

public class BoardParser {


	public static int testBoardForWinner9(int[] arr, int len, int wLen) {
		int count = 0;
		
		for (int x = 0; x < len; x++) {
			// horizontal testing
			for (int y = 0; y < len - (wLen - 1); y++) {
				int tf = arr[y + x * len];
				//System.out.println("(" + x + "," + y + ") " + tf);
				if (tf != 0) {
					for (int z = 1; z <= wLen; z++) {
						count++;
						//System.out.println("- (" + x + "," + y + "," + z + ") "
						//		+ tf);
						if (wLen == z)
							return tf;
						else if (wLen > z && arr[y + x * len + z] != tf)
							break;
					}
				}
			}

			if (x < len - (wLen - 1)) {
				// vertical testing
				for (int y = 0; y < len; y++) {
					int tf = arr[y + x * len];
					//System.out.println("(" + x + "," + y + ") " + tf);
					if (tf != 0) {
						for (int z = 1; z <= wLen; z++) {
							count++;
						//	System.out.println("- (" + x + "," + y + "," + z
						//			+ ") " + tf);
							if (wLen == z)
								return tf;
							else if (wLen > z && arr[y + (x + z) * len] != tf)
								break;
						}
					}
				}

				// diagonals top left
				for (int y = 0; y < len - (wLen - 1); y++) {
					int tf = arr[y + x * len];
					//System.out.println("(" + x + "," + y + ") " + tf);
					if (tf != 0) {
						for (int z = 1; z <= wLen; z++) {
							count++;
						//	System.out.println("- (" + x + "," + y + "," + z
						//			+ ") " + tf);
							if (wLen == z)
								return tf;
							else if (wLen > z
									&& arr[y + (x + z) * len + z] != tf)
								break;
						}
					}
				}

				// diagonals top right
				for (int y = len - 1; y > len - wLen; y--) {
					int tf = arr[y + x * len];
					//System.out.println("(" + x + "," + y + ") " + tf);
					if (tf != 0) {
						for (int z = 1; z <= wLen; z++) {
							count++;
						//	System.out.println("- (" + x + "," + y + "," + z
						//			+ ") " + tf);
							if (wLen == z)
								return tf;
							else if (wLen > z
									&& arr[y + (x + z) * len - z] != tf)
								break;
						}
					}
				}
			}
		}

		System.out.println("------"+count);

		return 0;
	}
	
	public static int testBoardForWinner5(int[] arr, int len, int wLen) {
		int count = 0;
		int tfh,tfb,tfv,tfs;
		
		for (int x = 0; x < len; x++) {
			for (int y = 0; y < len - (wLen - 1); y++) {
				tfh = arr[y + x * len]; //tfh for horizontals
				tfb = arr[y + x * len]; //tfb for backslash
				
				if (tfh != 0) { //abort if field are free (tfh==tfb==0)
					for (int z = 1; z <= wLen; z++) {
						System.out.println("- (" + x + "," + y + "," + z + ") "
								+ tfh+ " - "+tfb);
						
						if(tfh!=0) //horizontal
							if (wLen == z)
								return tfh;
							else if (wLen > z && arr[y + x * len + z] != tfh) {
								count++;
								tfh = 0;
							}
						
						if (x < len - (wLen - 1)&&tfb!=0) //backslash
							if (wLen == z)
								return tfb;
							else if (wLen > z
									&& arr[y + (x + z) * len + z] != tfb) {
								count++;
								tfb = 0;
							}
					}
				}
			}

			if (x < len - (wLen - 1)) {
				// vertical testing
				for (int y = 0; y < len; y++) {
					tfv = arr[y + x * len]; //for vertical
					tfs = arr[y + x * len]; //for slash
					
					if (tfv != 0) { //free field no need to test (tfv==tfs==0)
						for (int z = 1; z <= wLen; z++) {
							System.out.println("- (" + x + "," + y + "," + z
									+ ") " + tfv);
							
							//vertical
							if(tfv!=0)
								if (wLen == z)
									return tfv;
								else if (wLen > z && arr[y + (x + z) * len] != tfv) {
									count++;
									tfv=0;
								}
									
							//slash
							if(y>len-wLen&&y<len&&tfs!=0)
								if (wLen == z)
									return tfs;
								else if (wLen > z
										&& arr[y + (x + z) * len - z] != tfs) {
									count++;
									tfs=0;
								}
						}
					}
				}

			}
		}

		System.out.println("------"+count);

		return 0;
	}
	
	public static int testBoardForWinner(int[] arr, int len, int wLen) {
		int count = 0;
		int tfh, tfb, tfv, tfs;

		for (int x = 0; x < len; x++) {
			for (int y = 0; y < len; y++) {
				tfv = arr[y + x * len]; // tfv for vertical
				tfs = arr[y + x * len]; // tfs for slash
				tfh = arr[y + x * len]; // tfh for horizontals
				tfb = arr[y + x * len]; // tfb for backslash

				if (y < len - (wLen - 1) && tfh != 0) { // abort if field are
														// free (tfh==tfb==0)
					for (int z = 1; z <= wLen; z++) {
						//System.out.println("- (" + x + "," + y + "," + z + ") "
						//		+ tfh + " - " + tfb);

						if (tfh != 0) // horizontal
							if (wLen == z)
								return tfh;
							else if (wLen > z && arr[y + x * len + z] != tfh) {
								count++;
								tfh = 0;
							}

						if (x < len - (wLen - 1) && tfb != 0) // backslash
							if (wLen == z)
								return tfb;
							else if (wLen > z
									&& arr[y + (x + z) * len + z] != tfb) {
								count++;
								tfb = 0;
							}
					}
				}

				if (x < len - (wLen - 1) && tfv != 0) { // free field no
														// need to test
														// (tfv==tfs==0)
					for (int z = 1; z <= wLen; z++) {
						//System.out.println("- (" + x + "," + y + "," + z + ") "
						//		+ tfv);

						// vertical
						if (tfv != 0)
							if (wLen == z)
								return tfv;
							else if (wLen > z && arr[y + (x + z) * len] != tfv) {
								count++;
								tfv = 0;
							}

						// slash TODO debug
						if (y > len - wLen && y < len && tfs != 0)
							if (wLen == z)
								return tfs;
							else if (wLen > z
									&& arr[y + (x + z) * len - z] != tfs) {
								count++;
								tfs = 0;
							}
					}
				}
			}
		}

		System.out.println("------" + count);

		return 0;
	}

	
	public static void main(String...args) {
		int[] arr = new int[] {
						0,1,2,2, 
						1,1,0,1, 
						2,0,2,1, 
						2,0,0,1
				};

		int[] arr2 = new int[] {
				0,1,0,0,1,2,
				1,1,2,2,1,0,
				1,2,1,1,0,2, 
				0,1,2,0,1,2,
				0,1,2,1,1,0,
				1,2,1,1,2,2
		};
		

		int[] arr3 = new int[] {
				2,2,1,1,2,2,
				1,1,2,2,1,1,
				2,2,0,0,2,2, 
				1,0,1,1,0,1,
				2,2,1,0,2,2,
				1,1,2,2,1,1
		};

		int[] arr4 = new int[] {
				2,2,1,1,2,2,
				1,1,2,2,1,1,
				2,2,0,0,2,2, 
				1,0,1,0,1,1,
				2,2,0,1,2,2,
				1,0,1,2,1,1
		};

		int[] arr5 = new int[] {
				2,2,1,1,2,2,
				1,1,2,2,1,1,
				2,2,0,0,2,2, 
				1,0,1,0,0,1,
				2,2,0,0,1,2,
				1,0,2,1,0,1
		};


		long start = System.nanoTime();
		System.out.println(testBoardForWinner9(arr,4,3));
		System.out.println(testBoardForWinner9(arr2,6,3));
		System.out.println(testBoardForWinner9(arr3,6,3));
		System.out.println((System.nanoTime()-start)+"ns");

		System.out.println("\n\n=========================================================\n");

		start = System.nanoTime();
		System.out.println(testBoardForWinner(arr,4,3));
		System.out.println(testBoardForWinner(arr2,6,3));
		System.out.print("Problematic case: ");
		System.out.println(testBoardForWinner(arr3,6,3));
		System.out.println(testBoardForWinner(arr4,6,3));
		System.out.println(testBoardForWinner(arr5,6,3));
		System.out.println((System.nanoTime()-start)+"ns");
	}
}
