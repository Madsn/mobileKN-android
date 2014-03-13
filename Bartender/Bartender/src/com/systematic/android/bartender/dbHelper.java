package com.systematic.android.bartender;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dbHelper extends SQLiteOpenHelper {

	public static final String TABLE_BARTABS = "bartabs";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_INITIALS = "initials";
	public static final String COLUMN_BEERS = "beers";
	public static final String COLUMN_SODAS = "sodas";
	public static final String COLUMN_CREATED_AT = "created_at";
	public static final String COLUMN_LAST_EDITED = "last_edited";

	private static final String DATABASE_NAME = "bartabs.db";
	private static final int DATABASE_VERSION = 2;
	
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_BARTABS + " (" 
			+ COLUMN_ID	+ " integer primary key autoincrement, "
			+ COLUMN_INITIALS + " text not null, "
			+ COLUMN_BEERS + " integer not null default 0, "
			+ COLUMN_SODAS + " integer not null default 0, "
			+ COLUMN_CREATED_AT + " text not null, "
			+ COLUMN_LAST_EDITED + " text not null" 
			+ ");";

	public dbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(dbHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARTABS);
		    onCreate(db);
	}

}
