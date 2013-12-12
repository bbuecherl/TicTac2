package tk.agarsia.tictac2.view.options;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.marks.MarkController;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MarkAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private CharSequence[] entries;
	private CharSequence[] values;
	private MarkListPreference pref;

	public MarkAdapter(MarkListPreference pref) {
		CharSequence[][] entries = MarkController.getEntries();
		this.entries = entries[1];
		this.values = entries[0];
		this.pref = pref;
		inflater = LayoutInflater.from(pref.getContext());
	}

	@Override
	public int getCount() {
		return values.length;
	}

	@Override
	public Object getItem(int arg0) {
		return values[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int i, View out, ViewGroup group) {
		if (out == null) // reuse old view if existing
			out = inflater.inflate(R.layout.mark_view, group, false);

		((MarkView) out.findViewById(R.id.mark_preview)).init(i, pref,
				values[i], out);
		((TextView) out.findViewById(R.id.mark_name)).setText(entries[i]);

		return out;
	}
}