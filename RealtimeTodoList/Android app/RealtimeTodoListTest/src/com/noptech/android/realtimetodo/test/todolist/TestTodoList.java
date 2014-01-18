package com.noptech.android.realtimetodo.test.todolist;

import android.app.Activity;

import com.noptech.android.realtimetodo.MainActivity;
import com.noptech.android.realtimetodo.network.NetworkInterface;
import com.noptech.android.realtimetodo.todolist.TodoList;
import com.noptech.android.realtimetodo.todolist.TodoTask;

public class TestTodoList extends TodoList {
	
	private Activity mainActivity;
	
	public TestTodoList(MainActivity mainActivity) {
		super(mainActivity);
		this.mainActivity = mainActivity;
		syncer = createSyncer();
	}

	protected NetworkInterface createSyncer(){
		return new DummySyncer(adapter, mainActivity);
	}

	public TodoTask getItem(int pos) {
		return ((DummySyncer) syncer).getItem(pos);
	}

	public int indexOf(TodoTask task) {
		return ((DummySyncer) syncer).indexOf(task);
	}
	
}
