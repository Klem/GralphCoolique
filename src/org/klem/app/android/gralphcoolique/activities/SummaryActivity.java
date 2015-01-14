package org.klem.app.android.gralphcoolique.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import org.klem.android.gralphcoolique.consumption.ConsoDAO;
import org.klem.app.android.galphcoolique.drinks.DrinkBean;
import org.klem.app.android.galphcoolique.drinks.DrinksBDD;
import org.klem.app.android.gralphcoolique.activities.R;
import org.klem.app.android.gralphcoolique.activities.R.array;
import org.klem.app.android.gralphcoolique.activities.R.layout;

import android.R.anim;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Resources;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SummaryActivity extends Activity {
    /** Called when the activity is first created. */
	Resources res;
	Context ctx;
	SimpleAdapter adapter;
	ListView daysList;
	ImageView leftArrow;
	TextView currentWeek;
	ImageView rightArrow;
	String[] weekDays;
	ConsoDAO dao;
	 ArrayList<HashMap<String, String>> listItem = new  ArrayList<HashMap<String, String>>();
	
	Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Paris/France"), Locale.FRANCE);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        res= getResources();
        setContentView(R.layout.summary);
        weekDays = res.getStringArray(R.array.weekdays);
        daysList = (ListView) findViewById(R.id.summarylist);
        leftArrow = (ImageView) findViewById(R.id.left_calendar_arrow);
        currentWeek = (TextView) findViewById(R.id.currentweek);
        rightArrow = (ImageView) findViewById(R.id.right_calendar_arrow);
        dao = new ConsoDAO(ctx);
        dao.openForReading();
       
        //Libéllé
        registerForContextMenu(daysList);
       
        displayCalendarHeader();
        
        displayDaysList();
       
        adapter = new SimpleAdapter (ctx, listItem , R.layout.summaryitem,
        		 new String[] {"day", "alc", "units"},
                 new int[] {R.id.day_of_week, R.id.avg_alc_rate, R.id.consumed_units});
        
        leftArrow.setOnClickListener(new onCalendarArrowClickedLister());
        rightArrow.setOnClickListener(new onCalendarArrowClickedLister());
        daysList.setAdapter(adapter);
      
    }
    
  //Menu contextuel
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		String[] menuItems = res.getStringArray(R.array.summary_list_action);
		for (int i = 0; i < menuItems.length; i++) {
			menu.add(Menu.NONE, i, i, menuItems[i]);
		}
	}
	
	public boolean onContextItemSelected(MenuItem item) {
		 int itemIndex = item.getItemId();
		 //AJOUTER
	     if(itemIndex == 0) {
			  Intent intent = new Intent(ctx, DrinkPicker.class);	
	    	  startActivity(intent);

	     } else {
	    	 Toast.makeText(ctx, item.getTitle()+" non implémenté", Toast.LENGTH_SHORT).show();
	     }
	     return true;
	 }
	    
    
    public void displayCalendarHeader() {
    	StringBuffer sb = new StringBuffer();
        sb.append(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
        sb.append(" ");
        sb.append(cal.get(Calendar.YEAR));
        sb.append(" (");
        sb.append(cal.get(Calendar.WEEK_OF_YEAR));
        sb.append(")");
        
        currentWeek.setText(sb.toString());
        
    }
    
    public void displayDaysList() {
    	listItem.clear();
    	Calendar tmp = (Calendar) cal.clone();
    	for (int i=0; i< 7; i++) {
        	HashMap<String, String> map = new HashMap<String, String>();
        	int d = tmp.get(Calendar.DAY_OF_WEEK)-1;
        	String s = weekDays[d]+" "+tmp.get(Calendar.DATE);
        	//String s = ""+d+" "+cal.get(Calendar.DAY_OF_MONTH);
        	tmp.add(Calendar.DATE, 1);
        	map.put("unit", "unit"+i);
        	map.put("alc", "alc"+i);
        	map.put("day", s);
        	
        	listItem.add(map);

        }
    }
    
    public class onCalendarArrowClickedLister implements OnClickListener {

		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.left_calendar_arrow) {
				cal.roll(Calendar.WEEK_OF_YEAR, false);
				Log.d("calendar", "incémenté de -1 : "+cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
			} else {
				cal.roll(Calendar.WEEK_OF_YEAR, true);
				Log.d("calendar", "incémenté de 1 : "+cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
			}
			
			displayCalendarHeader();
			displayDaysList();
        	adapter.notifyDataSetChanged();
		       
		}
		
    }
    
    
    /**
    for (int day = 1; day < weekDays.length +1; day++) {
    	HashMap<String, String> map = new HashMap<String, String>();
        map.put("day", weekDays[day]);
       	map.put("alc", "0.1");
       	map.put("units", "2");
       	listItem.add(map);
	}
   

    adapter = new SimpleAdapter (this.getBaseContext(), listItem , R.layout.summaryitem,
              new String[] {"day", "alc", "units"},
              new int[] {R.id.day_of_week, R.id.avg_alc_rate, R.id.consumed_units});
    
    daysList.setAdapter(adapter);
    */
}