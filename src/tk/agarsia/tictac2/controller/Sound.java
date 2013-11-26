package tk.agarsia.tictac2.controller;

import android.media.SoundPool;

/**
 * Class for sound objects
 * 
 * Create a sound object via SoundController.newSound(filename). An object of
 * this class will be able to be played and disposed.
 * 
 * @author agarsia (Bernhard Bücherl)
 * @version 1.0
 * @since 1.0
 */
public class Sound {
	// constants for sample volumes

	/**
	 * volume quiet (50%)
	 */
	public static final float QUIET = 0.5f;
	/**
	 * volume medium (75%)
	 */
	public static final float MEDIUM = 0.75f;
	/**
	 * volume loud (100%)
	 */
	public static final float LOUD = 1f;

	int soundId;
	SoundPool soundPool;

	/**
	 * Function to initialize the sound.
	 * 
	 * @param soundPool
	 *            sound pool reference
	 * @param soundId
	 *            unique sound id
	 */
	public Sound(SoundPool soundPool, int soundId) {
		this.soundId = soundId;
		this.soundPool = soundPool;
	}

	/**
	 * Function to play the sound
	 * 
	 * @param volume
	 *            total volume (range 0-1)
	 */
	public void play(float volume) {
		soundPool.play(soundId, volume, volume, 0, 0, 1);
	}

	/**
	 * Function to play the sound
	 * 
	 * @param volumeLeft
	 *            left speaker volume (range 0-1)
	 * @param volumeRight
	 *            right speaker volume (range 0-1)
	 */
	public void play(float volumeLeft, float volumeRight) {
		soundPool.play(soundId, volumeLeft, volumeRight, 0, 0, 1);
	}

	/**
	 * Function to dispose the sound
	 */
	public void dispose() {
		soundPool.unload(soundId);
	}
}