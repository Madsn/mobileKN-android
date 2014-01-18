package com.noptech.android.realtimetodo.network;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import com.noptech.android.realtimetodo.MainActivity;
import com.noptech.android.realtimetodo.todolist.TodoAdapter;
import com.noptech.android.realtimetodo.todolist.TodoTask;

public class SocketSyncer extends NetworkInterface {

	private static final String TAG = "SOCKETIO";
	private String serverURL;
	private SocketIO socket;

	public SocketSyncer(String serverURL, TodoAdapter adapter, Activity activity) {
		super(adapter, activity);
		this.serverURL = serverURL;
		openConnection();
	}

	private void createSocket() {
		try {
			socket = new SocketIO(serverURL);
		} catch (MalformedURLException e) {
			Log.v(TAG, "MalformedURL");
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		return socket.isConnected();
	}

	private void openConnection() {
		createSocket();
		socket.connect(new IOCallback() {
			@Override
			public void onMessage(JSONObject json, IOAcknowledge ack) {
				Log.v(TAG, "Server said: " + json.toString());
			}

			@Override
			public void onMessage(String data, IOAcknowledge ack) {
				Log.v(TAG, "Server said: " + data);
			}

			@Override
			public void onError(SocketIOException socketIOException) {
				Log.v(TAG, "an Error occured" + socketIOException.toString());
				socketIOException.printStackTrace();
			}

			@Override
			public void onDisconnect() {
				Log.v(TAG, "Connection terminated.");
			}

			@Override
			public void onConnect() {
				Log.v(TAG, "Connection established");
				socket.emit("getTaskList");
			}

			@SuppressLint("DefaultLocale")
			@Override
			public void on(String event, IOAcknowledge ack, Object... args) {
				Log.v(TAG, "Server triggered event '" + event + "'");

				event = event.toLowerCase();
				if (event.equals("newtasks")) {
					Object obj = args[0];
					Log.v(TAG, "received: " + obj.toString());
					try {
						JSONObject json = new JSONObject(obj.toString());
						JSONArray jsonArray = json.getJSONArray("tasks");
						for (int i = 0; i < jsonArray.length(); i++) {
							Log.v(TAG, "task json object: "
									+ jsonArray.getJSONObject(i).toString());
							final TodoTask task = new TodoTask(jsonArray
									.getJSONObject(i));
							Log.v(TAG, "TodoTask created: " + task.debugInfo());
							activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									adapter.add(task);
								}
							});
							Log.v(TAG, "TodoTask saved");
						}
					} catch (JSONException e) {
						Log.v(TAG, "JSONException: " + e.toString());
						e.printStackTrace();
					}
				} else if (event.equals("tasklist")) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							adapter.clear();
						}
					});
					Object obj = args[0];
					Log.v(TAG, "received: " + obj.toString());
					try {
						JSONObject json = new JSONObject(obj.toString());
						JSONArray jsonArray = json.getJSONArray("tasks");
						for (int i = 0; i < jsonArray.length(); i++) {
							Log.v(TAG, "task json object: "
									+ jsonArray.getJSONObject(i).toString());
							final TodoTask task = new TodoTask(jsonArray
									.getJSONObject(i));
							Log.v(TAG, "TodoTask created: " + task.debugInfo());
							activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									adapter.add(task);
								}
							});
							Log.v(TAG, "TodoTask saved");
						}
					} catch (JSONException e) {
						Log.v(TAG, "JSONException: " + e.toString());
						e.printStackTrace();
					}
				} else if (event.equals("setdonestate")) {
					Object obj = args[0];
					Log.v(TAG, "received: " + obj.toString());
					try {
						JSONObject json = new JSONObject(obj.toString());
						JSONArray jsonArray = json.getJSONArray("tasks");
						for (int i = 0; i < jsonArray.length(); i++) {
							Log.v(TAG, "task json object: "
									+ jsonArray.getJSONObject(i).toString());
							final TodoTask task = new TodoTask(jsonArray
									.getJSONObject(i));
							Log.v(TAG, "todotask created: " + task.debugInfo());
							activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									adapter.setDoneState(task);
									adapter.notifyDataSetChanged();
								}
							});
							Log.v(TAG, "TodoTask saved");
						}
					} catch (JSONException e) {
						Log.v(TAG, "JSONException: " + e.toString());
						e.printStackTrace();
					}
				} else if (event.equals("deltasks")) {
					Object obj = args[0];
					Log.v(TAG, "received: " + obj.toString());
					try {
						JSONObject json = new JSONObject(obj.toString());
						JSONArray jsonArray = json.getJSONArray("tasks");
						for (int i = 0; i < jsonArray.length(); i++) {
							Log.v(TAG, "task json object: "
									+ jsonArray.getJSONObject(i).toString());
							final TodoTask task = new TodoTask(jsonArray
									.getJSONObject(i));
							Log.v(TAG, "todotask created: " + task.debugInfo());
							activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									adapter.remove(task);
									adapter.notifyDataSetChanged();
								}
							});
							Log.v(TAG, "TodoTask saved");
						}
					} catch (JSONException e) {
						Log.v(TAG, "JSONException: " + e.toString());
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public void add(String taskName) {
		this.add(new TodoTask(taskName));
	}

	@Override
	public void add(TodoTask task) {
		emitTaskAsArray("addTasks", task);
	}

	@Override
	public void toggleTaskDone(int position) {
		TodoTask task = adapter.getItem(position);
		task.done = !task.done;
		this.emitSetDoneState(task);
	}

	private void emitSetDoneState(TodoTask task) {
		emitTaskAsArray("setDoneState", task);
	}

	@Override
	public void clear() {
		socket.emit("clearTasks");
	}
	
	@Override
	public void deleteCompleted(){
		ArrayList<TodoTask> tasks = adapter.getAllCompleted();
		for (TodoTask task : tasks){
			emitTaskAsArray("delTasks", task);
		}
	}
	
	private void emitTaskAsArray(String msgType, TodoTask task){
		JSONArray array = new JSONArray();
		JSONObject msg = new JSONObject();
		try {
			array.put(task.toJSONObj());
			msg.put("tasks", array);
			socket.emit(msgType, msg.toString());
		} catch (JSONException e) {
			Log.v(TAG, "JSONException: " + e.toString());
			e.printStackTrace();
		}
	}

}
