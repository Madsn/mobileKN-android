package com.noptech.android.realtimetodo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.noptech.android.realtimetodo.network.NetworkInterface;
import com.noptech.android.realtimetodo.todolist.TodoList;

public class MainActivity extends Activity {

	TodoList todoList;
	NetworkInterface network;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		todoList = new TodoList(this);
		// todoList.initializeWithDummyData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete_completed:
			todoList.deleteCompleted();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public void onAddTaskButtonPressed(View view) {
		EditText taskNameField = (EditText) findViewById(R.id.editText1);
		String taskName = taskNameField.getText().toString();
		taskNameField.setText("");
		todoList.addTask(taskName);
	}

	public void setTodoList(TodoList todoList) {
		this.todoList = todoList; // for testing
	}

}
