package tk.agarsia.tictac2.view;

import tk.agarsia.tictac2.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Options extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
}
