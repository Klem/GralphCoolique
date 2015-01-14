package org.klem.app.android.galphcoolique.drinks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DrinksBDD extends SQLiteOpenHelper {

	public static final int VERSION_BDD = 1;
	public static final String BDD_NAME = "gralphcoolique.db";
	public static final String TABLE_DRINKS = "drinks";
	public static final String COL_ID = "_id";
	public static final String COL_LABEL = "LABEL";
	public static final String COL_TYPE = "TYPE";
	public static final String COL_VOLUME = "VOLUME";
	public static final String COL_PROOF = "PROOF";
	public static final String COL_UNITS = "UNITS";
	public static final int COL_ID_INDEX = 0;
	public static final int COL_LABEL_INDEX = 1;
	public static final int COL_TYPE_INDEX = 2;
	public static final int COL_VOLUME_INDEX = 3;
	public static final int COL_PROOF_INDEX = 4;
	public static final int COL_UNITS_INDEX = 5;
	
	private static final String CREATE_BDD = " CREATE TABLE IF NOT EXISTS "+TABLE_DRINKS+" ("
											 +COL_ID+" INTEGER PRIMARY KEY,"
											 +COL_LABEL+" TEXT NOT NULL, "
											 +COL_TYPE+" TEXT NOT NULL, "
											 +COL_VOLUME+" FLOAT NOT NULL, "
											 +COL_PROOF+" FLOAT NOT NULL, "
											 +COL_UNITS+" FLOAT NOT NULL);";
											 
	
	public DrinksBDD(Context context, String name, CursorFactory factory, int version) {
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
