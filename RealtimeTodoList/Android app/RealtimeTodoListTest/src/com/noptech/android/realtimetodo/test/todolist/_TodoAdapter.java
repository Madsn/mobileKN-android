package com.noptech.android.realtimetodo.test.todolist;

import android.graphics.Color;
import android.graphics.Paint;
import android.test.AndroidTestCase;
import android.view.View;
import android.widget.TextView;

import com.noptech.android.realtimetodo.todolist.TodoAdapter;
import com.noptech.android.realtimetodo.todolist.TodoTask;

public class _TodoAdapter extends AndroidTestCase {
	
	TodoAdapter adapter;

	@Override
	public void setUp() throws Exception{
		super.setUp();
		adapter = new TodoAdapter(getContext(), android.R.layout.simple_list_item_1);
		adapter.clear();
	}
	
	public void testCompletedTasksStrikenThrough(){
		addTask("Task1", false);

		assertFalse(isRowStrikenThrough(0));
		toggleTaskDone(0);
		assertTrue(isRowStrikenThrough(0));
		toggleTaskDone(0);
		assertFalse(isRowStrikenThrough(0));
	}
	
	public void testCompletedTasksGray(){
		addTask("Task1", false);
		
		assertFalse(isRowTextGray(0));
		toggleTaskDone(0);
		assertTrue(isRowTextGray(0));
		toggleTaskDone(0);
		assertFalse(isRowTextGray(0));
	}
	
	public void testDeleteCompleted(){
		addTask("Task1", false);
		
		addTask("Task2", true);
		addTask("Task3", true);
		assertEquals(3, adapter.size());
		adapter.deleteCompleted();
		assertEquals(1, adapter.size());
	}
	
	private boolean isRowStrikenThrough(int position) {
		return (getRowPaintFlags(position) & Paint.STRIKE_THRU_TEXT_FLAG) != 0;
	}
	
	private boolean isRowTextGray(int position) {
		TextView row = getRow(position);
		return row.getCurrentTextColor() == Color.GRAY;
	}

	private int getRowPaintFlags(int position) {
		TextView row = getRow(position);
		return row.getPaintFlags();
	}
	
	private TextView getRow(int position) {
		View view = adapter.getView(0, null, null);
		TextView row = (TextView) view;
		return row;
	}
	
	private void addTask(String taskName, boolean done){
		TodoTask task = new TodoTask(taskName);
		task.done = done;
		adapter.add(task);
	}
	
	private void toggleTaskDone(int position){
		adapter.toggleTaskDone(position);
	}
}
