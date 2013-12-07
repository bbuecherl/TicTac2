package tk.agarsia.tictac2.view.options;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.marks.MarkController;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.ListPreference;
import android.util.AttributeSet;

public class MarkListPreference extends ListPreference {
	
	private Options act;

	public MarkListPreference(Context context, AttributeSet attrs) {
		super(context, attrs);

		CharSequence[][] entries = MarkController.getEntries();
		setEntries(entries[1]);
		setEntryValues(entries[0]);
		setDefaultValue(entries[0][0]);
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		super.onPrepareDialogBuilder(builder);
		
		builder.setNeutralButton(R.string.pref_mark_add, this);
	}
	
	public void setParent(Options act) {
		this.act = act;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_NEUTRAL)
			act.onOpenCreator();
	}
}