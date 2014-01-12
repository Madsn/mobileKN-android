package com.noptech.android.realtimetodo.network;

public interface SyncInterface {

	public void openConnection();
	public void addTasks();
	public void delTasks();
	public void getTaskList();
	public void clearTasks();
	public void closeConnection();
}
