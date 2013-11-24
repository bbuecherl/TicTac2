package tk.agarsia.tictac2.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class FileController {
	private static String externalStoragePath;

	/**
	 * Function to read files from external storage.
	 * 
	 * @param fileName
	 *            relative path and filename in external storage directory
	 * @return InputStream of the file
	 * @throws IOException
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
	 * @throws IOException
	 */
	public static OutputStream write(String fileName) throws IOException {
		return new FileOutputStream(externalStoragePath + fileName);
	}
	
	/**
	 * Initializes storage path variables
	 */
	public static void init() {
		externalStoragePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator;
	}
}