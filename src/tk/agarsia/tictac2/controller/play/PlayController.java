package tk.agarsia.tictac2.controller.play;

import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class PlayController {
	public static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";

	public static int PLAY;
	private static boolean validated;

	public static boolean isValidated() {
		return validated;
	}

	public static boolean isPlay() {
		return PLAY == ConnectionResult.SUCCESS;
	}

	public static void init(Context context) {
		validated = false;

		// Google Play Services check
		PLAY = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
	}
}