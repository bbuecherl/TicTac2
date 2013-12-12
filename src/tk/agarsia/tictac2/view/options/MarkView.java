package tk.agarsia.tictac2.view.options;

import org.json.JSONException;

import tk.agarsia.tictac2.R;
import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.marks.Mark;
import tk.agarsia.tictac2.controller.marks.MarkController;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

public class MarkView extends View implements View.OnLongClickListener,
		View.OnClickListener {
	private Mark mark;
	private Paint paint;
	private int index;
	private MarkListPreference pref;
	private CharSequence value;
	private RadioButton rdio;

	public MarkView(Context context, AttributeSet attrs) {
		super(context, attrs);

		paint = new Paint();
		index = -1;
		value = "";
	}

	public void init(int i, MarkListPreference pref, CharSequence value,
			View parent) {
		try {
			index = i;
			this.pref = pref;
			this.value = value;

			parent.setOnLongClickListener(this);
			parent.setOnClickListener(this);

			rdio = ((RadioButton) parent.findViewById(R.id.mark_active));

			if (Integer.parseInt(pref.getValue()) == Integer.parseInt((value
					.toString())))
				rdio.setChecked(true);
			else
				rdio.setChecked(false);

			Log.i("debug", ApplicationControl.getStringPref("pref_mark") + " "
					+ value + " " + pref.getValue());

			mark = new Mark(MarkController.getDB().getJSONObject(index),
					Color.parseColor(ApplicationControl.getStringPref(
							"pref_color_me", "#007200")));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mark != null)
			mark.draw(paint, canvas, 0, 0, canvas.getWidth());
	}

	@Override
	public void onClick(View v) {
		pref.setValue(value.toString());
		pref.getDialog().hide();
	}

	@Override
	public boolean onLongClick(View v) {
		final int ind = this.index;
		if (ind != 0 && ind != Integer.parseInt(pref.getValue())) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
			builder.setTitle(R.string.edit);
			builder.setPositiveButton(R.string.edit,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							pref.openCreator(index);
							dialog.dismiss();
							pref.getDialog().hide();
						}
					});
			builder.setNegativeButton(R.string.delete,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							MarkController.delete(ind);
							dialog.dismiss();
							pref.getDialog().hide();
						}
					});
			builder.show();
			return true;
		} else {
			return false;
		}
	}
}