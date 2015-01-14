package org.klem.app.android.gralphcoolique.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.klem.app.android.galphcoolique.drinks.DrinkBean;
import org.klem.app.android.galphcoolique.drinks.DrinksBDD;
import org.klem.app.android.galphcoolique.drinks.DrinksDAO;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkListActivity extends Activity {
    /** Called when the activity is first created. */
	DrinksDAO dao;
	Cursor cur;
	SimpleCursorAdapter adapter;
	Context ctx;
	Resources res;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drinklist);
        ListView drinkList = (ListView) findViewById(R.id.drinklist);
        Button add = (Button) findViewById(R.id.add);
        
        ctx = this;
        res = getResources();
       
        dao = new DrinksDAO(ctx);
        dao.openForReading();
        cur = (Cursor) dao.getDrinks();
        
        adapter = new SimpleCursorAdapter (this.getBaseContext(), R.layout.drinkitem, cur,
                new String[] { DrinksBDD.COL_LABEL, DrinksBDD.COL_VOLUME, DrinksBDD.COL_PROOF, DrinksBDD.COL_UNITS}, 
                new int[] { R.id.drink_item_label, R.id.drink_item_volume, R.id.drink_item_proof, R.id.drink_item_units});
          
        drinkList.setAdapter(adapter);
        registerForContextMenu(drinkList);
        
        add.setOnClickListener(new addButtonListener());
        	
        
    }
    
    public class addButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			 Intent intent = new Intent(ctx, DrinksActivity.class);
	    	 startActivity(intent);

		}
    	
    }
    //Menu contextuel
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.setHeaderTitle(cur.getString(DrinksBDD.COL_LABEL_INDEX));
		String[] menuItems = res.getStringArray(R.array.drink_list_action);
		for (int i = 0; i < menuItems.length; i++) {
			menu.add(Menu.NONE, i, i, menuItems[i]);
		}
	}
    
    //item du menu séléctionné
    @Override
    public boolean onContextItemSelected(MenuItem item) {
      final int drinkId = cur.getInt(DrinksBDD.COL_ID_INDEX);
      String drinkLabel = cur.getString(DrinksBDD.COL_LABEL_INDEX);
      
      AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
      
      int menuItemIndex = item.getItemId();
      String[] menuItems = res.getStringArray(R.array.drink_list_action);
      String menuItemName = menuItems[menuItemIndex];
      //supprimer
      if(menuItemIndex == 1){
	      //supprimer
	      //box de confirmation
	      AlertDialog.Builder confirm = new AlertDialog.Builder(ctx);
	      confirm.setMessage(res.getString(R.string.drink_alert_delete) +" "+ drinkLabel +" ?");
	      confirm.setIcon(R.drawable.ic_menu_dialog);
	      //bouton, oui
	      confirm.setPositiveButton(res.getString(R.string.yes), new DialogInterface.OnClickListener() {
	          //listerner bouton oui
	          public void onClick(DialogInterface arg0, int arg1) {
	        	  dao.deleteDrink(drinkId);
	              Toast.makeText(ctx, cur.getString(DrinksBDD.COL_LABEL_INDEX)+" "+res.getString(R.string.drink_conf_deleted), Toast.LENGTH_SHORT).show();
	              cur.requery();
	          	  adapter.notifyDataSetChanged();
	          }
	      });
	      
	      confirm.setNegativeButton(res.getString(R.string.no), new DialogInterface.OnClickListener() {
	          
	           public void onClick(DialogInterface arg0, int arg1) {
	           }
	       });
	     
	      confirm.show();
	      //Modification
      } else {
    	  DrinkBean drink = new DrinkBean();
    	  drink.setId(cur.getInt(DrinksBDD.COL_ID_INDEX));
    	  drink.setLabel(cur.getString(DrinksBDD.COL_LABEL_INDEX));
		  drink.setType(cur.getString(DrinksBDD.COL_TYPE_INDEX));
		  drink.setProof(cur.getFloat(DrinksBDD.COL_PROOF_INDEX));
		  drink.setVolume(cur.getFloat(DrinksBDD.COL_VOLUME_INDEX));
		  drink.setUnits(cur.getFloat(DrinksBDD.COL_UNITS_INDEX));
    	 
		  Intent intent = new Intent(ctx, DrinksActivity.class);	
    	  intent.putExtra(DrinkBean.DRINK_INTENT_NAME, drink);
    	  startActivity(intent);
    	  
    	 // Toast.makeText(ctx, "modif :"+menuItemIndex, Toast.LENGTH_SHORT).show();
      }

      return true;
    }
    
   
    @Override
    protected void onResume() {
    	super.onResume();
    	cur.requery();
    	adapter.notifyDataSetChanged();
    }

}