package com.noptech.android.realtimetodo.test;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.EditText;

import com.noptech.android.realtimetodo.MainActivity;
import com.noptech.android.realtimetodo.R;
import com.noptech.android.realtimetodo.test.todolist.TestTodoList;
import com.noptech.android.realtimetodo.todolist.TodoTask;

public class _MainActivity extends ActivityUnitTestCase<MainActivity> {

	private MainActivity activity;
	private TestTodoList todoList;
	private EditText taskNameField;
	private Button addButton;

	public _MainActivity() {
		super(MainActivity.class);
	}

	@Override
	public void setUp() throws Exception{
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(), MainActivity.class);
		startActivity(intent, null, null);
		activity = getActivity();
		todoList = new TestTodoList(activity);
		todoList.clear();
		addButton = (Button) activity.findViewById(R.id.addButton);
		taskNameField = (EditText) activity.findViewById(R.id.editText1);
		activity.setTodoList(todoList);
	}

	@Override
	public void tearDown(){

	}
	
	public void testAddingTask() {
		String taskName = "Test task 1 !#'9���";
		
		addTask(taskName);
		
		TodoTask task = todoList.getItem(0);
		assertEquals(1, todoList.size());
		assertEquals(taskName, task.name);
		assertFalse(task.done);
	}
	
	private void addTask(String taskName){
		taskNameField.setText(taskName);
		addButton.performClick();
	}
	
}

