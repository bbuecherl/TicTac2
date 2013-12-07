package tk.agarsia.tictac2.controller.marks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Mark {

	private int color;
	private JSONArray mark;

	public Mark(int color) {
		this(MarkController.getCurrent(), color);
	}

	public Mark(JSONObject mark, int color) {
		this.color = color;
		try {
			this.mark = mark.getJSONArray("elements");
		} catch (JSONException e) {
			this.mark = new JSONArray();
		}
	}

	public int getColor() {
		return color;
	}

	public void draw(Paint paint, Canvas canvas, int left, int top, int right,
			int bottom, int box) {
		paint.setColor(color);
		paint.setAlpha(255);

		JSONObject json;

		for (int i = 0; i < mark.length(); i++) {
			json = mark.optJSONObject(i);

			if (json.optString("tag").equals("rect")) {
				drawRect(paint, canvas, left, top, box, json);
			} else if(json.optString("tag").equals("circle")) {
				drawCircle();
			}
		}
	}
	
	private void drawCircle() {
		
	}
	

	private void drawRect(Paint paint, Canvas canvas, int left, int top,
			int box, JSONObject json) {
		int centerX = json.optInt("centerX", 0);
		int centerY = json.optInt("centerY", 0);
		int width = json.optInt("width", 0);
		int height = json.optInt("height", 0);
		int rotate = json.optInt("rotate", 0);
		
		if(width>100)
			width = 100;
		if(height>100)
			height = 100;
		
		if(centerX<width/2)
			centerX = width/2;
		if(centerX>100-width/2)
			centerX = 100-width/2;
		
		if(centerY<height/2)
			centerY = height/2;
		if(centerY>100-height/2)
			centerY = 100-height/2;
		
		
		canvas.save();
		canvas.rotate(rotate, left+centerX, top+centerY);

		canvas.drawRect(left + (centerX - width / 2) * box / 100, top
				+ (centerY - height / 2) * box / 100, left
				+ (centerX + width / 2) * box / 100, top
				+ (centerY + height / 2) * box / 100, paint);

		canvas.restore();
	}
}