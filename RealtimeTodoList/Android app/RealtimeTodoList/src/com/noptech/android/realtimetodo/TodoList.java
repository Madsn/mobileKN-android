package com.noptech.android.realtimetodo;

import android.app.ListActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TodoList extends ListActivity {
	
	ListView todoListView;
	// an "adapter" binds the underlying data structure with the gui object
	TodoAdapter adapter;

	public TodoList(MainActivity mainActivity) {
		todoListView = (ListView) mainActivity.findViewById(R.id.listView1);
		adapter = new TodoAdapter(mainActivity, android.R.layout.simple_list_item_1);
		todoListView.setAdapter(adapter);
		todoListView.setOnItemClickListener(todoTaskClickedListener);
	}

	public void initializeWithDummyData() {
		adapter.add("Hello");
		adapter.add("world");
		adapter.add("Whats");
		adapter.add("UUUUP");
	}

	public void addTask(String taskName) {
		adapter.insert(taskName, 0);
	}

	private OnItemClickListener todoTaskClickedListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			performItemClick(position);
		}
	};

	public int size() {
		return adapter.size();
	}

	public void clear() {
		adapter.clear();
	}

	public TodoTask getItem(int position) {
		return adapter.getItem(position);
	}

	public void performItemClick(int position) {
		adapter.toggleTaskDone(position);
	}

	public int indexOf(TodoTask task) {
		return adapter.indexOf(task);
	}
}
