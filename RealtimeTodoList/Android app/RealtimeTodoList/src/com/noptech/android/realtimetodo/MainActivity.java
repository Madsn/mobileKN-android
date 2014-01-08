package com.noptech.android.realtimetodo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	TodoList todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        todoList = new TodoList(this);
        todoList.initializeWithDummyData();
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	public void onAddTaskButtonPressed(View view) {
		EditText taskNameField = (EditText) findViewById(R.id.editText1);
		String taskName = taskNameField.getText().toString();
		taskNameField.setText("");
		todoList.addTask(taskName);
	}
	
	public TodoList getTodoList(){
		return todoList;
	}
	
}

