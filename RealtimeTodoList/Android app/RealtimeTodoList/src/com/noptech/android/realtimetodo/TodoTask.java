package com.noptech.android.realtimetodo;

public class TodoTask {

	public String name;
	public boolean done;
	
	public TodoTask(String name) {
		this.name = name;
		this.done = false;
	}
	
	public String toString() {
		return this.name;
	}
}
