package com.knb.sounds;

import java.io.IOException;

import min3d.Shared;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.util.Log;

import com.knb.R;

public class SoundManager {

	private static SoundPool soundPool;
	private static int soundKatanaMovement;
	private static int soundBallCut;
	private static int gameOver;
	private static float volume;
	private static MediaPlayer mainActivityMediaPlayer;
	private static MediaPlayer frontPageMediaPlayer;

	public static void loadAllSounds(Activity activity) {

		if (soundPool == null) {
			// Set the hardware buttons to control the music
			activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);

			// Load the sound
			soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

			// Loading all sounds
			soundKatanaMovement = soundPool.load(Shared.context(),
					R.raw.katana_movement, 1);
			soundBallCut = soundPool.load(Shared.context(), R.raw.ball_cut, 1);
			gameOver = soundPool.load(Shared.context(), R.raw.gameover, 1);

			AudioManager audioManager = (AudioManager) Shared.context()
					.getSystemService(Shared.context().AUDIO_SERVICE);
			float actualVolume = (float) audioManager
					.getStreamVolume(AudioManager.STREAM_MUSIC);
			float maxVolume = (float) audioManager
					.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			volume = actualVolume / maxVolume;

		}
	}

	public static void loadAllMediaFiles(Activity activity) {

		mainActivityMediaPlayer = MediaPlayer.create(activity,
				R.raw.mainactivity);
		frontPageMediaPlayer = MediaPlayer.create(activity, R.raw.frontpage);

	}

	public static void playKatanaMovement() {
		soundPool.play(soundKatanaMovement, volume, volume, 1, 0, 1f);
	}

	public static void playBallCut() {
		soundPool.play(soundBallCut, volume, volume, 1, 0, 1f);

	}

	public static void playGameOver() {
		soundPool.play(gameOver, volume, volume, 1, 0, 1f);
	}

	public static void playMainActivityMusic() {
		Log.i("SamuraiBall", "Main Activity music is playing");
		mainActivityMediaPlayer.setLooping(true);
		mainActivityMediaPlayer.start();
	}

	public static void stopMainActivityMusic() {

		// This stop is put in thread because it is taking quite long time.
		Thread t = new Thread() {
			public void run() {
				mainActivityMediaPlayer.stop();
				mainActivityMediaPlayer.reset();

				try {
					mainActivityMediaPlayer
							.setDataSource(
									Shared.context(),
									Uri
											.parse("android.resource://com.knb/raw/mainactivity"));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					mainActivityMediaPlayer.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		t.start();
	}

	public static void playFrontPageMusic() {
		Log.i("SamuraiBall", "Front Page music is playing");

		frontPageMediaPlayer.setLooping(true);
		frontPageMediaPlayer.start();
	}

	public static void stopFrontPageMusic() {

		if (frontPageMediaPlayer.isPlaying()) {
			frontPageMediaPlayer.stop();
			frontPageMediaPlayer.reset();

			try {
				frontPageMediaPlayer.setDataSource(Shared.context(), Uri
						.parse("android.resource://com.knb/raw/frontpage"));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				frontPageMediaPlayer.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
