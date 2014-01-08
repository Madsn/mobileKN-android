package com.noptech.android.realtimetodo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;


public class TodoAdapter extends ArrayAdapter<TodoTask> {

	private ArrayList<TodoTask> taskList;
	private LayoutInflater mInflater;
	private int mResource;

	public TodoAdapter(Context context, int resource) {
		super(context, resource);
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mResource = resource;
		taskList = new ArrayList<TodoTask>();
	}

	public void add(String taskName) {
		TodoTask task = new TodoTask(taskName);
		this.add(task);
	}
	
	public void removeAtPosition(int position) {
		taskList.remove(position);
		this.notifyDataSetChanged();
	}
	
	private int getDoneTaskCount() {
		int doneTasks = 0;
		for (TodoTask task : taskList){
			if (task.done){
				doneTasks++;
			}
		}
		return doneTasks;
	}
	
	@Override
	public void add(TodoTask task) {
		taskList.add(task);
		this.notifyDataSetChanged();
	}

	@Override
	public TodoTask getItem(int position) {
		return taskList.get(position);
	}
	
	@Override
	public void addAll(Collection<? extends TodoTask> collection) {
		addAll(collection);
	}

	@Override
	public void addAll(TodoTask... items) {
		for (TodoTask task : items){
			taskList.add(task);
		}
		this.notifyDataSetChanged();
	}

	@Override
	public void remove(TodoTask object) {
		taskList.remove(object);
		this.notifyDataSetChanged();
	}

	@Override
	public void clear() {
		taskList.clear();
	}

	@Override
	public void sort(Comparator<? super TodoTask> comparator) {
		super.sort(comparator);
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}

	@Override
	public void setNotifyOnChange(boolean notifyOnChange) {
		// TODO Auto-generated method stub
		super.setNotifyOnChange(notifyOnChange);
	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return super.getContext();
	}

	@Override
	public int getCount() {
		return taskList.size();
	}

	@Override
	public int getPosition(TodoTask item) {
		return taskList.indexOf(item);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
        TextView row;
        
        if (convertView == null) {
            view = mInflater.inflate(mResource, parent, false);
        } else {
            view = convertView;
        }

        row = (TextView) view;
        TodoTask task = getItem(position);
        row.setText(task.toString());
        
        if (task.done){
        	row.setPaintFlags(row.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
        	row.setPaintFlags(row.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

        return view;
	}

	@Override
	public void setDropDownViewResource(int resource) {
		// TODO Auto-generated method stub
		super.setDropDownViewResource(resource);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return super.getDropDownView(position, convertView, parent);
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return super.getFilter();
	}

	public void insert(String taskName, int index) {
		TodoTask task = new TodoTask(taskName);
		insert(task, index);
	}
	
	@Override
	public void insert(TodoTask task, int index){
		taskList.add(index, task);
		this.notifyDataSetChanged();
	}

	public void toggleTaskDone(int position) {
		TodoTask task = taskList.get(position);
		task.done = !task.done;
		if (task.done){
			moveToBottomOfList(task);
		} else {
			moveToTopOfList(task);
		}
	}

	private void moveToTopOfList(TodoTask task) {
		taskList.remove(task);
		this.insert(task, 0);
	}

	private void moveToBottomOfList(TodoTask task) {
		taskList.remove(task);
		int insertAtIndex = this.getCount() - this.getDoneTaskCount();
		this.insert(task, insertAtIndex);
	}

	public int size() {
		return taskList.size();
	}

	public int indexOf(TodoTask task) {
		return taskList.indexOf(task);
	}

}