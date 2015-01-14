package org.klem.app.android.gralphcoolique.activities;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

import org.klem.android.gralphcoolique.consumption.ConsoBean;
import org.klem.android.gralphcoolique.consumption.ConsoDAO;
import org.klem.app.android.galphcoolique.drinks.DrinksBDD;
import org.klem.app.android.galphcoolique.drinks.DrinksDAO;
import org.klem.app.android.gralphcoolique.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DrinkPicker extends Activity {
	DrinksDAO drinksDao;
	ConsoDAO consoDao;
	Context ctx;
	Resources res;
	Cursor drinks;
	AutoCompleteTextView selector;
	Button ok;
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.drinkpicker);
	        ctx = this;
	        res = getResources();
	        selector = (AutoCompleteTextView) findViewById(R.id.drinkselector);
	        ok = (Button) findViewById(R.id.button_pickdrink);
	        drinksDao = new DrinksDAO(this);
	        consoDao = new ConsoDAO(this);
	        drinksDao.openForReading();
	        consoDao.openForWriting();
	        
	        drinks = (Cursor) drinksDao.getDrinks();
	        
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
	        
	        while(drinks.moveToNext()) {
	        	adapter.add(drinks.getString(DrinksBDD.COL_LABEL_INDEX));
	        }
	        
	        selector.setAdapter(adapter);
	        
	        ok.setOnClickListener(new onOkClickedListener());
	        
	  }  
	
	public class onOkClickedListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			ConsoBean conso = new ConsoBean();
			conso.setDrinkId(adapter.);
			
			consoDao.insertConso(conso);
			DrinkPicker.this.finish();
			String s = drinks.getString(DrinksBDD.COL_LABEL_INDEX);
			Toast.makeText(ctx, res.getString(R.string.drink_picked, s), Toast.LENGTH_SHORT).show();
			DrinkPicker.this.finish();
			
		}
		
	}
}
