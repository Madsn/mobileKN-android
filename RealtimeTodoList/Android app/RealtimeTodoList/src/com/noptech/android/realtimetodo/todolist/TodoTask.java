package com.noptech.android.realtimetodo.todolist;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.JsonObject;

public class TodoTask {

	private static final String TAG = "TODOTASK";
	public String name;
	public boolean done;
	public String _id;

	public TodoTask(JSONObject json) {
		try {
			this.done = json.getBoolean("done");
			this.name = json.getString("name");
			this._id = json.getString("_id");
		} catch (JSONException e) {
			Log.v(TAG, "JSON Exception: " + e.toString());
			e.printStackTrace();
		}
	}

	public TodoTask(String taskName) {
		this.name = taskName;
		this.done = false;
	}

	public String toString() {
		return this.name;
	}

	public String debugInfo() {
		return "name: " + this.name + ", done: " + this.done + ", _id: "
				+ this._id;
	}

	public JSONObject toJSONObj() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("done", this.done);
		json.put("name", this.name);
		if (_id != null) {
			json.put("_id", this._id);
		}
		return json;
	}
}
