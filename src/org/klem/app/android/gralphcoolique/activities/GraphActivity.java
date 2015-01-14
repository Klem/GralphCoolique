package org.klem.app.android.gralphcoolique.activities;

import org.klem.app.android.gralphcoolique.activities.R;
import org.klem.app.android.gralphcoolique.activities.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GraphActivity extends Activity {
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TextView textview = new TextView(this);
        textview.setText("Graphs");
        setContentView(textview);
        
        setContentView(R.layout.main);
    }
}
