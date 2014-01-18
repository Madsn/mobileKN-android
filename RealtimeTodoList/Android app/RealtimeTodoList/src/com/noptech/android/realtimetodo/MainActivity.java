package com.noptech.android.realtimetodo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.noptech.android.realtimetodo.network.NetworkInterface;
import com.noptech.android.realtimetodo.todolist.TodoList;

public class MainActivity extends Activity {

	protected static final String TAG = "MAINACTIVITY";
	TodoList todoList;
	NetworkInterface network;
	private EditText taskNameField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		todoList = new TodoList(this);
		// todoList.initializeWithDummyData();

		taskNameField = (EditText) findViewById(R.id.editText1);
		taskNameField.setOnKeyListener(new EditText.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				Log.v(TAG, "Triggered event: " + event.toString());
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					switch (keyCode) {
					case KeyEvent.KEYCODE_DPAD_CENTER:
					case KeyEvent.KEYCODE_ENTER:
						onAddTaskButtonPressed(v);
						return true;
					default:
						break;
					}
				}
				return false;
			}
		});
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
		// taskNameField = (EditText) findViewById(R.id.editText1);
		String taskName = taskNameField.getText().toString();
		if (taskName.equals(""))
			return;
		taskNameField.setText("");
		todoList.addTask(taskName);
	}

	public void setTodoList(TodoList todoList) {
		this.todoList = todoList; // for testing
	}

}
