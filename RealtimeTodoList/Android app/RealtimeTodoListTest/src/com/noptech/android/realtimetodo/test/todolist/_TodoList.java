package com.noptech.android.realtimetodo.test.todolist;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

import com.noptech.android.realtimetodo.MainActivity;
import com.noptech.android.realtimetodo.todolist.TodoTask;

public class _TodoList extends ActivityUnitTestCase<MainActivity> {
	
	private TestTodoList todoList;
	
	public _TodoList(){
		super(MainActivity.class);
	}
	
	@Override
	public void setUp() throws Exception{
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(), MainActivity.class);
		startActivity(intent, null, null);
		MainActivity activity = getActivity();
		todoList = new TestTodoList(activity);
		todoList.clear();
	}
	
	public void testTogglingTask() {
		addTask("Task1");
		
		TodoTask task = todoList.getItem(0);
		assertFalse(task.done);
		todoList.performItemClick(0);
		assertTrue(task.done);		
		todoList.performItemClick(0);
		assertFalse(task.done);
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
	
	public void testDeleteCompleted(){
		addMultipleTasks(5);
		assertEquals(5, todoList.size());
		todoList.performItemClick(0);
		todoList.performItemClick(1);
		assertEquals(5, todoList.size());
		todoList.deleteCompleted();
		assertEquals(3, todoList.size());
	}
	
	private void addTask(String taskName){
		todoList.addTask(taskName);
	}
	
	private void addMultipleTasks(int numberOfTasks){
		for (int i = 0; i< numberOfTasks; i++){
			addTask("Task number " + i);
		}
	}
}
