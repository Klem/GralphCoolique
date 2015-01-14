package org.klem.app.android.gralphcoolique.activities;



import org.klem.app.android.galphcoolique.drinks.DrinkBean;
import org.klem.app.android.galphcoolique.drinks.DrinksDAO;
import org.klem.app.android.gralphcoolique.activities.R;
import org.klem.app.android.gralphcoolique.activities.R.id;
import org.klem.app.android.gralphcoolique.activities.R.layout;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DrinksActivity extends Activity {
	 /** Called when the activity is first created. */
	 public Context ctx;
	 public EditText label;
	 public EditText volume;
	 public EditText proof ;
	 public TextView units;
    // Spinner typeSpinner;
	 public Button save;
	 public float unitsValue = 0F;
	 public float proofValue = 0F;
	 public float volumeValue = 0F;
	 public String labelValue ="";
	 public int id = 0;
	 public float ALCOOL_DENSITY = 0.8F;
	 public DrinkBean drink = new DrinkBean();
	 public boolean editMode = false;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drinks);
        
        ctx = this;
        label = (EditText) findViewById(R.id.drink_label);
        volume = (EditText) findViewById(R.id.drink_volume);
        proof = (EditText) findViewById(R.id.drink_proof);
        units = (TextView) findViewById(R.id.drink_units);
      
        save = (Button) findViewById(R.id.save_drink);
        /**
        typeSpinner = (Spinner) findViewById(R.id.drink_types);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.drink_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        */
        TextWatcher watcher = new onValuesChangedListener();

        volume.addTextChangedListener(watcher);
        proof.addTextChangedListener(watcher); 
        
        save.setOnClickListener(new onSaveDrinkListener());
        
        //récupération des valeur si ouvert en mode edition
        if(getIntent().getExtras() != null) {
        	
        	DrinkBean drink = (DrinkBean) getIntent().getExtras().get(DrinkBean.DRINK_INTENT_NAME);
        	editMode = true;
        	System.out.println("Entering edit mode for "+drink.getLabel());
        	label.setText(drink.getLabel());
        	volume.setText(String.valueOf(drink.getVolume()));
        	proof.setText(String.valueOf(drink.getProof()));
        	units.setText(String.valueOf(drink.getUnits()));
        	id = drink.getId();
        }
    }
    

    public class onValuesChangedListener implements TextWatcher {
    	String vol = "";
    	String prf = "";
		@Override
		public void afterTextChanged(Editable s) {
			
			if(s.length() > 0) {
				vol = volume.getText().toString();
				prf = proof.getText().toString();
				System.out.println("Got volume : "+vol);
				System.out.println("Got proof : "+prf);
					
			}
			
			if(!"".equals(vol)) {
				try {
					volumeValue = Float.parseFloat(vol);
					
					if(volumeValue < 0) {
						Toast.makeText(ctx, ctx.getString(R.string.drink_err_volume_invalid_value), Toast.LENGTH_SHORT).show();
						volume.setText("0");
					}
				} catch (NumberFormatException nfe) {
					Toast.makeText(ctx, ctx.getString(R.string.drink_err_not_a_number), Toast.LENGTH_SHORT).show();
					volume.setText("0");
					return;
				}
			}
			
			if(!"".equals(prf)) {
				try {
					proofValue = Float.parseFloat(prf);
					if(proofValue < 0 || proofValue > 100) {
						Toast.makeText(ctx, ctx.getString(R.string.drink_err_proof_invalid_value), Toast.LENGTH_SHORT).show();
						proof.setText("0");
					}	
				} catch (NumberFormatException nfe) {
					Toast.makeText(ctx, ctx.getString(R.string.drink_err_not_a_number), Toast.LENGTH_SHORT).show();
					proof.setText("0");
					return;
				}
			}
			
			unitsValue = proofValue * (volumeValue / 100) * ALCOOL_DENSITY;
			
			units.setText(String.valueOf(unitsValue));
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}	
    }
    
    public class onSaveDrinkListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			labelValue = label.getText().toString();
			if(!"".equals(labelValue)) {
				//TODO créer bean
				drink.setLabel(labelValue);
				drink.setType("category");
				drink.setProof(proofValue);
				drink.setVolume(volumeValue);
				drink.setUnits(unitsValue);
				drink.setId(id);
				DrinksDAO dao = new DrinksDAO(ctx);
				dao.openForWriting();
				//insertion
				if(!editMode) {
					if(dao.insertDrink(drink) != -1) {
						Toast.makeText(ctx, ctx.getString(R.string.drink_conf_saved), Toast.LENGTH_SHORT).show();
						
					} else {
						Toast.makeText(ctx, ctx.getString(R.string.drink_err_not_saved), Toast.LENGTH_SHORT).show();
					}
				//Edition	
				} else {
					
					if(dao.updateDrink(drink) != -1) {
						//Toast.makeText(ctx, drink.toString(), Toast.LENGTH_LONG).show();
						Toast.makeText(ctx, ctx.getString(R.string.drink_conf_upaded), Toast.LENGTH_SHORT).show();
						
					} else {
						Toast.makeText(ctx, ctx.getString(R.string.drink_err_not_saved), Toast.LENGTH_SHORT).show();
					}
				}
				dao.close();
				//DrinkListActivity.this.cur.requery();
				DrinksActivity.this.finish();
			} else {
				Toast.makeText(ctx, ctx.getString(R.string.drink_err_label_invalid_value), Toast.LENGTH_SHORT).show();
				return;
			}	
			
		}
    	
    }
}


