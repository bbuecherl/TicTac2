package com.tictac2.view;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.tictac2.R;

public class Options extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
}
