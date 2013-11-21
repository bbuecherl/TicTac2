package tk.agarsia.tictac2.view;

import tk.agarsia.tictac2.R;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Options extends PreferenceActivity {

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//yep, i know its deprecated, but it works on every device, unlike the new "workarounds"...
		addPreferencesFromResource(R.xml.prefs);
		
		//prevent exceptions on <11 devices, those need to use back button, sorry.
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
}
