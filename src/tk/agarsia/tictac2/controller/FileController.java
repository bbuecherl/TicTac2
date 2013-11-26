package tk.agarsia.tictac2.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

/**
 * Class to support file I/O operations.
 * 
 * This class supports static methods to read and write files.
 * It is not even necessary to initialize this controller.
 * This controller requires the android.permission.WRITE_EXTERNAL_STORAGE permission in AndroidManifest.xml
 * 
 * @author agarsia (Bernhard Bücherl)
 * @version 1.0
 * @since 1.0
 */
public class FileController {
	//static variable to store external file path
	private static String externalStoragePath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + File.separator;

	/**
	 * Function to read files from external storage.
	 * 
	 * @param fileName
	 *            relative path and filename in external storage directory
	 * @return InputStream of the file
	 * @throws IOException exception is thrown if file is missing or corupted or if we have no read permission
	 */
	public static InputStream read(String fileName) throws IOException {
		return new FileInputStream(externalStoragePath + fileName);
	}

	/**
	 * Function to write a file to external storage.
	 * 
	 * @param fileName
	 *            relative path and filename in external storage directory
	 * @return OutputStream of the file
	 * @throws IOException exception is thrown if file is corupted or if we have no write permission
	 */
	public static OutputStream write(String fileName) throws IOException {
		return new FileOutputStream(externalStoragePath + fileName);
	}
}