package com.systematic.android.bartender;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BartabDataSource {

	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { dbHelper.COLUMN_ID,
			dbHelper.COLUMN_INITIALS, dbHelper.COLUMN_BEERS,
			dbHelper.COLUMN_SODAS, dbHelper.COLUMN_CREATED_AT,
			dbHelper.COLUMN_LAST_EDITED };

	public BartabDataSource(Context context) {
		dbHelper = new DatabaseHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public void createBartab(Bartab tab) {
		
	}
}
