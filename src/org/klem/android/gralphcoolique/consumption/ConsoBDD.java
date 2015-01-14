package org.klem.android.gralphcoolique.consumption;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class ConsoBDD  extends SQLiteOpenHelper {

	public static final int VERSION_BDD = 1;
	public static final String BDD_NAME = "gralphcoolique.db";
	public static final String TABLE_CONSO = "conso";
	public static final String COL_ID = "_id";
	public static final String COL_DRINK_ID = "drink_id";
	public static final String COL_CONSO_DATE = "date";
	
	public static final int COL_ID_INDEX = 0;
	public static final int COL_DRINK_ID_INDEX = 1;
	
	
	private static final String CREATE_BDD = " CREATE TABLE IF NOT EXISTS "+TABLE_CONSO+" ("
											 +COL_ID+" INTEGER PRIMARY KEY,"
											 +COL_DRINK_ID+" INTEGER NOT NULL,"
	 										 +COL_CONSO_DATE+" DATETIME NOT NULL);";
											 
	
	public ConsoBDD(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_BDD);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}

