package tk.agarsia.tictac2.view.options;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.marks.MarkController;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.util.Log;

public class MarkListPreference extends ListPreference {
	public static final boolean MARKCREATOR_ENABLED = false; //will be enabled as soon as completely implemented

	private Options act;

	public MarkListPreference(Context context, AttributeSet attrs) {
		super(context, attrs);

		CharSequence[][] entries = MarkController.getEntries();
		setEntries(entries[1]);
		setEntryValues(entries[0]);
		setDefaultValue(entries[0][0]);
		
		Log.i("debug",ApplicationControl.getStringPref("pref_mark"));
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		builder.setPositiveButton(null, null);
		if(MARKCREATOR_ENABLED)
			builder.setNeutralButton(R.string.pref_mark_add, this);
		builder.setAdapter(new MarkAdapter(this), new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {

			}
		});
		Log.i("debug",ApplicationControl.getStringPref("pref_mark"));
	}

	public void setParent(Options act) {
		this.act = act;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_NEUTRAL)
			openCreator(MarkCreator.NEW_MARK);
	}
	
	public void openCreator(int id) {
		if(MARKCREATOR_ENABLED)
			act.onOpenCreator(id);
	}
}