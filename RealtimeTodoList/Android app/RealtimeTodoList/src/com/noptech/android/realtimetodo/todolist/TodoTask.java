package com.noptech.android.realtimetodo.todolist;

public class TodoTask {

	public String name;
	public boolean done;
	
	public TodoTask(String name) {
		this.name = name;
		this.done = false;
	}
	
	public TodoTask(String name, boolean done){
		this.name = name;
		this.done = done;
	}
	
	public String toString() {
		return this.name;
	}
}
