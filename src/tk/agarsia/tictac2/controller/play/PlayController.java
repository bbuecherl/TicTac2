package tk.agarsia.tictac2.controller.play;

import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

@Deprecated
public class PlayController {
	public static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";

	public static int PLAY;
	private static boolean validated;
	private static String token;

	public static boolean isValidated() {
		return validated;
	}
	
	public static void setValidated(boolean v) {
		validated = v;
	}
	
	public static void setToken(String t) {
		token = t;
	}
	
	public static String getToken() {
		return token;
	}

	public static boolean isPlay() {
		return PLAY == ConnectionResult.SUCCESS;
	}

	public static void init(Context context) {
		validated = false;
		token = null;

		// Google Play Services check
		PLAY = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
	}
}