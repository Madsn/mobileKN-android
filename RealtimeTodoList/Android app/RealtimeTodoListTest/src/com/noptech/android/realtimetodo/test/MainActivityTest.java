package com.noptech.android.realtimetodo.test;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.EditText;

import com.noptech.android.realtimetodo.MainActivity;
import com.noptech.android.realtimetodo.R;
import com.noptech.android.realtimetodo.TodoList;
import com.noptech.android.realtimetodo.TodoTask;

public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {

	private MainActivity activity;
	private TodoList todoList;
	private EditText taskNameField;
	private Button addButton;

	public MainActivityTest() {
		super(MainActivity.class);
	}

	@Override
	public void setUp() throws Exception{
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(), MainActivity.class);
		startActivity(intent, null, null);
		activity = getActivity();
		todoList = activity.getTodoList();
		todoList.clear();
		addButton = (Button) activity.findViewById(R.id.addButton);
		taskNameField = (EditText) activity.findViewById(R.id.editText1);
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
		assertEquals(false, task.done);
	}
	
	public void testTogglingTask() {
		addTask("Task1");
		
		TodoTask task = todoList.getItem(0);
		assertEquals(false, task.done);
		todoList.performItemClick(0);
		assertEquals(true, task.done);		
		todoList.performItemClick(0);
		assertEquals(false, task.done);
	}
	
	public void testNewTasksAddedAtTop() {
		addTask("addedFirst");
		addTask("addedSecond");
		TodoTask firstTask = todoList.getItem(0);
		TodoTask secondTask = todoList.getItem(1);
		assertEquals("addedSecond", firstTask.name);
		assertEquals("addedFirst", secondTask.name);
	}
	
	public void testCompletedTasksMovedToBottom() {
		// Task should be moved to bottom, but newly completed should be top-most of the completed
		addMultipleTasks(5);
		TodoTask task1 = todoList.getItem(0);
		TodoTask task2 = todoList.getItem(1);
		
		todoList.performItemClick(0);
		assertEquals(4, todoList.indexOf(task1));
		todoList.performItemClick(0);
		assertEquals(3, todoList.indexOf(task2)); // task 2 should be above task 1
		assertEquals(4, todoList.indexOf(task1));
	}
	
	public void testMarkedIncompleteMovedToTop() {
		addMultipleTasks(5);
		TodoTask task1 = todoList.getItem(0);
		TodoTask task2 = todoList.getItem(1);
		
		todoList.performItemClick(0);
		assertEquals(4, todoList.indexOf(task1));
		assertEquals(0, todoList.indexOf(task2));
		todoList.performItemClick(4);
		assertEquals(0, todoList.indexOf(task1));
		assertEquals(1, todoList.indexOf(task2));
	}
	
	private void addTask(String taskName){
		taskNameField.setText(taskName);
		addButton.performClick();
	}
	
	private void addMultipleTasks(int numberOfTasks){
		for (int i = 0; i< numberOfTasks; i++){
			addTask("Task number " + i);
		}
	}
	
}

