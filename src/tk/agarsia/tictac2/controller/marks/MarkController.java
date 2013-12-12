package tk.agarsia.tictac2.controller.marks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tk.agarsia.tictac2.controller.ApplicationControl;
import tk.agarsia.tictac2.controller.FileController;

public class MarkController {
	public static final String DB_PATH = "saves"+File.separator+"marks.json";

	private static final String DEFAULT_DB = "[{\"name\":\"default\",\"elements\":[{\"tag\":\"rect\",\"centerX\":50,\"centerY\":50,\"width\":100,\"height\":100,\"rotate\":0}]},{\"name\":\"test\",\"elements\":[{\"tag\":\"circle\",\"centerX\":50,\"centerY\":50,\"radius\":40},{\"tag\":\"rect\",\"centerX\":50,\"centerY\":75,\"width\":80,\"height\":50,\"rotate\":0}]}]";

	private static JSONArray DB;

	public static void init() {
		try {
			if (!FileController.exists(DB_PATH)) {
				DB = new JSONArray(DEFAULT_DB);
				save();
			} else {
				BufferedReader br = new BufferedReader(new FileReader(
						FileController.get(DB_PATH)));
				String line = null;
				StringBuilder sb = new StringBuilder();

				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

				br.close();

				DB = new JSONArray(sb.toString());
			}
		} catch (IOException e) {
			// TODO thats going to be critical...
			e.printStackTrace();
			try {
				DB = new JSONArray(DEFAULT_DB);
			} catch (JSONException jex) {
				// critical...
				jex.printStackTrace();
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
			try {
				DB = new JSONArray(DEFAULT_DB);
			} catch (JSONException jex) {
				// critical...
				jex.printStackTrace();
			}
		}
	}
	
	public static JSONArray getDB() {
		return DB;
	}
	
	public static CharSequence[][] getEntries() {
		CharSequence[][] out = new CharSequence[2][DB.length()];

		for(int i = 0; i < DB.length(); i++) {
			try {
				out[1][i] = DB.getJSONObject(i).getString("name");
			} catch (JSONException e) {
				out[1][i] = i+"";
				e.printStackTrace();
			}
			out[0][i] = i+"";
		}
		
		return out;
	}
	
	public static JSONObject getCurrent() {
		String pref = ApplicationControl.getStringPref("pref_mark");
		int index = 0;
		if(pref!=null&&pref.length()>=1)
			index = Integer.parseInt(pref);
		
		try {
			if(getDB().isNull(index))
				return getDB().getJSONObject(0);
			
			return getDB().getJSONObject(index);
		} catch(JSONException e) {
			e.printStackTrace();
			return new JSONObject();
		}
	}

	public static void delete(int ind) {
		JSONArray tmp = new JSONArray();
		
		for(int i = 0; i < DB.length(); i++)
			if(ind!=i)
				try {
					tmp.put(DB.get(i));
				} catch(JSONException e) {
					e.printStackTrace();
				}
		DB = tmp;
		
		save();
	}
	
	public static boolean save() {
		try {
			if(!FileController.exists(DB_PATH))
				FileController.get(DB_PATH).createNewFile();
			
			FileWriter fw = new FileWriter(FileController.get(DB_PATH));
			fw.write(DB.toString(2));
			fw.flush();
			fw.close();

			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}