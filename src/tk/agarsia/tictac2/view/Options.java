package tk.agarsia.tictac2.view;

import tk.agarsia.tictac2.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Custom preference activity class.
 * 
 * Simple preference class, the content is parsed from the res/xml/prefs.xml
 * file.
 * 
 * @author agarsia (Bernhard Bücherl)
 * @version 1.0
 * @since 1.0
 */
public class Options extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// yep, i know its deprecated, but it works on every device (API 1 and
		// up!), unlike the new "workarounds". And I've never seen Android
		// remove an deprecated method from new APIs.
		addPreferencesFromResource(R.xml.prefs);
	}
}
