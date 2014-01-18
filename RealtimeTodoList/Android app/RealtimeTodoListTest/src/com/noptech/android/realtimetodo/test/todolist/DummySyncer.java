package com.noptech.android.realtimetodo.test.todolist;

import android.app.Activity;

import com.noptech.android.realtimetodo.network.NetworkInterface;
import com.noptech.android.realtimetodo.todolist.TodoAdapter;
import com.noptech.android.realtimetodo.todolist.TodoTask;

public class DummySyncer extends NetworkInterface {

	public DummySyncer(TodoAdapter adapter, Activity activity) {
		super(adapter, activity);
	}
	
	public TodoTask getItem(int position){
		return adapter.getItem(position);
	}

	public int indexOf(TodoTask task) {
		return adapter.indexOf(task);
	}

}
