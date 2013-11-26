package tk.agarsia.tictac2.controller;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Class to support sound control
 * 
 * This controller stores the sound pool and enables you to play back sounds
 * stored in your assets. The controller needs to be instanciated by
 * ApplicationControl.init().
 * 
 * @author agarsia (Bernhard Bücherl)
 * @version 1.0
 * @since 1.0
 */
public class SoundController {
	/**
	 * maximum of parallel sounds (10 [0xA])
	 */
	public static final int MAX_SOUNDS = 0xA;

	// static instance
	private static SoundController instance;

	private SoundPool soundPool;
	private AssetManager assets;

	/**
	 * Custom constructor
	 * 
	 * Initializes the sound pool and the asset manager reference
	 * 
	 * @param context
	 *            context object of the application
	 */
	private SoundController(Context context) {
		soundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
		assets = context.getAssets();
	}

	/**
	 * Function to initialize the sound controller instance.
	 * 
	 * @param context
	 *            context object of the application
	 */
	public static void init(Context context) {
		instance = new SoundController(context);
	}

	/**
	 * Function to load a new sound object.
	 * 
	 * @param name
	 *            filename of the audio file
	 * @return sound object
	 * @throws IOException
	 *             Exception is thrown when file does not exist or is currupted.
	 */
	public static Sound newSound(String name) throws IOException {
		AssetFileDescriptor assetDescriptor = instance.assets.openFd(name);
		int soundId = instance.soundPool.load(assetDescriptor, 0);
		return new Sound(instance.soundPool, soundId);
	}
}