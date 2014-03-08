package com.noptech.android.realtimetodo.todolist;

import android.app.ListActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.noptech.android.realtimetodo.MainActivity;
import com.noptech.android.realtimetodo.R;
import com.noptech.android.realtimetodo.network.NetworkInterface;
import com.noptech.android.realtimetodo.network.SocketSyncer;

public class TodoList extends ListActivity {
	
	private static final String serverURL = "http://nodeserv.noptech.com";
	private ListView todoListView;
	protected NetworkInterface syncer;
	protected TodoAdapter adapter;
	// an "adapter" binds the underlying data structure with the gui object

	public TodoList(MainActivity mainActivity) {
		todoListView = (ListView) mainActivity.findViewById(R.id.listView1);
		adapter = new TodoAdapter(mainActivity, android.R.layout.simple_list_item_1);
		todoListView.setAdapter(adapter);
		todoListView.setOnItemClickListener(todoTaskClickedListener);
		syncer = createSyncer(mainActivity);
	}

	protected NetworkInterface createSyncer(MainActivity mainActivity) {
		return new SocketSyncer(serverURL, adapter, mainActivity);
	}

	public void initializeWithDummyData() {
		syncer.add("Hello");
		syncer.add("world");
		syncer.add("Whats");
		syncer.add("UUUUP");
	}

	public void addTask(String taskName) {
		syncer.add(taskName);
	}

	private OnItemClickListener todoTaskClickedListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			performItemClick(position);
		}
	};
	
	public void performItemClick(int position) {
		syncer.toggleTaskDone(position);
	}

	public int size() {
		return syncer.size();
	}

	public void clear() {
		syncer.clear();
	}

	public void addTask(TodoTask task) {
		syncer.add(task);
	}

	protected NetworkInterface createSyncer() {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteCompleted() {
		syncer.deleteCompleted();
	}
}
