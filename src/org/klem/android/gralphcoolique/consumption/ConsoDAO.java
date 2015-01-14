package org.klem.android.gralphcoolique.consumption;

import java.sql.Date;
import java.util.ArrayList;

import org.klem.app.android.galphcoolique.drinks.DrinkBean;
import org.klem.app.android.galphcoolique.drinks.DrinksBDD;
import org.klem.app.android.gralphcoolique.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ConsoDAO {
	private SQLiteDatabase bdd;
	private ConsoBDD conso;

	
	public ConsoBDD getDrinksBDD() {
		return conso;
	}

	public ConsoDAO(Context ctx) {
		conso = new ConsoBDD(ctx, ConsoBDD.BDD_NAME, null, ConsoBDD.VERSION_BDD);
		
	}
	
	public float insertConso(ConsoBean c) {
		ContentValues values = new ContentValues();
		values.put(ConsoBDD.COL_DRINK_ID, c.getDrinkId());
		values.put(ConsoBDD.COL_CONSO_DATE, Utils.getWellFormattedNow());
		
		
		return  bdd.insertOrThrow(ConsoBDD.TABLE_CONSO, null, values);
	}
	
	
	
	public void deleteConso(int consoId) {
		bdd.delete(ConsoBDD.TABLE_CONSO, ConsoBDD.COL_ID+"="+consoId, null);
	}
	
	public void openForWriting(){
		//on ouvre la BDD en écriture
		bdd = conso.getWritableDatabase();
	}
	
	public void openForReading(){
		//on ouvre la BDD en écriture
		bdd = conso.getReadableDatabase();
	}
	
	public void close(){
		//on ouvre la BDD en écriture
		bdd.close();
	}
	
}
