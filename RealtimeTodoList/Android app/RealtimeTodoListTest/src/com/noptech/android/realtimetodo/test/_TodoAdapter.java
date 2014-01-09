package com.noptech.android.realtimetodo.test;

import android.graphics.Color;
import android.graphics.Paint;
import android.test.AndroidTestCase;
import android.view.View;
import android.widget.TextView;

import com.noptech.android.realtimetodo.TodoAdapter;
import com.noptech.android.realtimetodo.TodoTask;

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

		assertEquals(false, isRowStrikenThrough(0));
		toggleTaskDone(0);
		assertEquals(true, isRowStrikenThrough(0));
		toggleTaskDone(0);
		assertEquals(false, isRowStrikenThrough(0));
	}
	
	public void testCompletedTasksGray(){
		addTask("Task1", false);
		
		assertEquals(false, isRowTextGray(0));
		toggleTaskDone(0);
		assertEquals(true, isRowTextGray(0));
		toggleTaskDone(0);
		assertEquals(false, isRowTextGray(0));
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
		adapter.add(new TodoTask(taskName, done));
	}
	
	private void toggleTaskDone(int position){
		adapter.toggleTaskDone(position);
	}
}
