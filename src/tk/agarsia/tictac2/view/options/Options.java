package tk.agarsia.tictac2.view.options;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.AppStackController;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.view.TicTac2;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Custom preference activity class.
 * 
 * Simple preference class, the content is parsed from the res/xml/prefs.xml
 * file.
 * 
 * @author agarsia (Bernhard Bücherl)
 * @version 1.1
 * @since 1.0
 */
public class Options extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (!ApplicationControl.isInit()) {
			startActivity(new Intent(getApplicationContext(), TicTac2.class));
			AppStackController.toStack(this);
			return;
		}

		// yep, i know its deprecated, but it works on every device (API 1 and
		// up!), unlike the new "workarounds". And I've never seen Android
		// remove an deprecated method from new APIs.
		addPreferencesFromResource(R.xml.prefs);
		((MarkListPreference) findPreference("pref_mark")).setParent(this);
	}

	public void onOpenCreator(int index) {
		MarkCreator.CUR_ID = index; // i would love to use extra bundles, but
									// its API 16+ for preference activities
		startActivity(new Intent(this, MarkCreator.class));
	}
}