package org.klem.app.android.galphcoolique.drinks;

import java.sql.Date;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DrinksDAO {
	

	private SQLiteDatabase bdd;
	private DrinksBDD drinks;
	private ArrayList<DrinkBean> drinkList = new ArrayList<DrinkBean>();
	
	public DrinksBDD getDrinksBDD() {
		return drinks;
	}

	public DrinksDAO(Context ctx) {
		drinks = new DrinksBDD(ctx, DrinksBDD.BDD_NAME, null, DrinksBDD.VERSION_BDD);
		
	}
	
	public float insertDrink(DrinkBean d) {
		ContentValues values = new ContentValues();
		values.put(DrinksBDD.COL_LABEL, d.getLabel());
		values.put(DrinksBDD.COL_TYPE, d.getType());
		values.put(DrinksBDD.COL_VOLUME, d.getVolume());
		values.put(DrinksBDD.COL_PROOF, d.getProof());
		values.put(DrinksBDD.COL_UNITS, d.getUnits());
		
		return  bdd.insertOrThrow(DrinksBDD.TABLE_DRINKS, null, values);
	}
	
	public float updateDrink(DrinkBean d) {
		ContentValues values = new ContentValues();
		values.put(DrinksBDD.COL_LABEL, d.getLabel());
		values.put(DrinksBDD.COL_TYPE, d.getType());
		values.put(DrinksBDD.COL_VOLUME, d.getVolume());
		values.put(DrinksBDD.COL_PROOF, d.getProof());
		values.put(DrinksBDD.COL_UNITS, d.getUnits());

		return  bdd.update(DrinksBDD.TABLE_DRINKS, values, DrinksBDD.COL_ID+"="+d.getId(), null);
	}
	
	
	public void deleteDrink(int drinkId) {
		bdd.delete(DrinksBDD.TABLE_DRINKS, DrinksBDD.COL_ID+"="+drinkId, null);
	}
	
	
	public Cursor getDrinks() {
		Cursor c = bdd.query(DrinksBDD.TABLE_DRINKS, null, null, null, null, null, DrinksBDD.COL_LABEL+" ASC");
		return c;
	}
	
	public void getDrinkFromId(int drinkId) {
		//TODO
	}
	
	public void getDrinkFromDate(Date start, Date end) {
		//TODO
	}
	
	public void openForWriting(){
		//on ouvre la BDD en écriture
		bdd = drinks.getWritableDatabase();
	}
	
	public void openForReading(){
		//on ouvre la BDD en écriture
		bdd = drinks.getReadableDatabase();
	}
	
	public void close(){
		//on ouvre la BDD en écriture
		bdd.close();
	}
	
	private DrinkBean cursorToDrink(Cursor c){
		//si aucun élément n'a été retourné dans la requête, on renvoie null
		if (c.getCount() == 0) {
			System.out.println("Aucune boisson retrouvée");
			return null;
		} 
		
		System.out.println(c.getCount()+" boissons trouvées");
		
		//On créé un livre
		DrinkBean drink = new DrinkBean();
		//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
		drink.setId(c.getInt(DrinksBDD.COL_ID_INDEX));
		drink.setLabel(c.getString(DrinksBDD.COL_LABEL_INDEX));
		drink.setVolume(c.getFloat(DrinksBDD.COL_VOLUME_INDEX));
		drink.setProof(c.getFloat(DrinksBDD.COL_PROOF_INDEX));
		drink.setUnits(c.getFloat(DrinksBDD.COL_UNITS_INDEX));

		System.out.println(drink.toString());
 
		//On retourne le livre
		return drink;
	}
}
