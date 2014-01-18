package com.noptech.android.realtimetodo.network;

import android.app.Activity;

import com.noptech.android.realtimetodo.todolist.TodoAdapter;
import com.noptech.android.realtimetodo.todolist.TodoTask;

public abstract class NetworkInterface {
	
	protected TodoAdapter adapter;
	protected Activity activity;

	public NetworkInterface(TodoAdapter adapter, Activity activity){
		this.adapter = adapter;
		this.activity = activity;
	}
	public void add(final String taskName){
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				adapter.add(new TodoTask(taskName));
			}
		});
		
	}
	public void add(final TodoTask task){
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				adapter.add(task);
			}
		});
		
	}
	public void toggleTaskDone(final int position){
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				adapter.toggleTaskDone(position);
			}
		});
		
	}
	public void clear(){
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				adapter.clear();
			}
		});
		
	}
	public int size() {
		return adapter.size();
	}

}
