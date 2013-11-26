package tk.agarsia.tictac2.model.board;

public class BoardParser {
	public static int testBoardForWinner(int[] arr, int len, int wLen) {
		int count = 0;
		
		for (int x = 0; x < len; x++) {
			// horizontal testing 
			for (int y = 0; y < len - (wLen - 1); y++) {
				int tf = arr[y + x * len];
				System.out.println("(" + x + "," + y + ") " + tf);
				if (tf != 0) {
					for (int z = 1; z <= wLen; z++) {
						count++;
						System.out.println("- (" + x + "," + y + "," + z + ") "
								+ tf);
						if (wLen == z)
							return tf;
						else if (wLen > z && arr[y + x * len + z] != tf)
							break;
					}
				}
				
				//TODO merge horizontal & backslash (should work (99%))
				
				// diagonals backslash
				if (x < len - (wLen - 1)) {
					System.out.println("(" + x + "," + y + ") " + tf);
					if (tf != 0) {
						for (int z = 1; z <= wLen; z++) {
							count++;
							System.out.println("- (" + x + "," + y + "," + z
									+ ") " + tf);
							if (wLen == z)
								return tf;
							else if (wLen > z
									&& arr[y + (x + z) * len + z] != tf)
								break;
						}
					}
				}
			}
			
			//TODO merge all?? (50%)

			if (x < len - (wLen - 1)) {
				// vertical testing
				for (int y = 0; y < len; y++) {
					int tf = arr[y + x * len];
					System.out.println("(" + x + "," + y + ") " + tf);
					if (tf != 0) {
						for (int z = 1; z <= wLen; z++) {
							count++;
							System.out.println("- (" + x + "," + y + "," + z
									+ ") " + tf);
							if (wLen == z)
								return tf;
							else if (wLen > z && arr[y + (x + z) * len] != tf)
								break;
						}
					}
				}

				//TODO merge vertical & slash (should work too (60%))
				
				// diagonals slash
				for (int y = len - 1; y > len - wLen; y--) {
					int tf = arr[y + x * len];
					System.out.println("(" + x + "," + y + ") " + tf);
					if (tf != 0) {
						for (int z = 1; z <= wLen; z++) {
							count++;
							System.out.println("- (" + x + "," + y + "," + z
									+ ") " + tf);
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
				2,2,1,1,2,2, 
				1,1,2,2,1,1,
				2,2,1,1,2,2,
				1,1,2,2,1,1
		};

		
		System.out.println(testBoardForWinner(arr,4,3));

		System.out.println("\n\n");
		
		System.out.println(testBoardForWinner(arr2,6,3));

		System.out.println("\n\n");
		
		System.out.println(testBoardForWinner(arr3,6,3));
	}
}