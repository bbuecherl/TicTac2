package tk.agarsia.tictac2.controller;

import java.io.File;
import java.io.IOException;

import android.os.Environment;

/**
 * Class to support file I/O operations.
 * 
 * This class supports static methods to read and write files. It is not even
 * necessary to initialize this controller. This controller requires the
 * android.permission.WRITE_EXTERNAL_STORAGE permission in AndroidManifest.xml
 * 
 * @author agarsia (Bernhard Bücherl)
 * @version 1.0
 * @since 1.0
 */
public class FileController {
	// static variable to store external file path
	private static File PATH;

	/**
	 * Static method to make sure the directories do exist.
	 */
	public static void init() {
		PATH = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ File.separator
				+ "tictac2"
				+ File.separator);
		if (!PATH.exists())
			PATH.mkdirs();

		File savePath = new File(PATH, "saves" + File.separator);
		if (!savePath.exists())
			savePath.mkdir();

		File replayPath = new File(PATH, "replays" + File.separator);
		if (!replayPath.exists())
			replayPath.mkdir();
	}

	/**
	 * Function to get/create files from/on external storage.
	 * 
	 * @param fileName
	 *            relative path and filename in external storage directory
	 * @return File
	 * @throws IOException
	 */
	public static File get(String fileName) throws IOException {
		return new File(PATH, fileName);
	}

	/**
	 * Function to test if file exists.
	 * 
	 * @param fileName
	 *            relative path and filename in external storage directory
	 * @return true if file does exist
	 * @throws IOException
	 */
	public static boolean exists(String fileName) throws IOException {
		return new File(PATH, fileName).exists();
	}
}