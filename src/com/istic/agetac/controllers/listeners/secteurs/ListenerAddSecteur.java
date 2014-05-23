package com.istic.agetac.controllers.listeners.secteurs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.istic.agetac.controler.adapter.SecteurAdapter;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Secteur;

public class ListenerAddSecteur implements OnClickListener {

	private SecteurAdapter adapter;
	private Context context;
	
	private EditText name;
	private LinearLayout colorEdit;
	
	private Intervention intervention;
	
	public ListenerAddSecteur( SecteurAdapter adapter,
								Context context,
								EditText name,
								LinearLayout colorEdit,
								Intervention intervention)
	{
		this.adapter = adapter;
		this.context = context;
		this.name = name;
		this.colorEdit = colorEdit;
		this.intervention = intervention;
	}
	
	@Override
	public void onClick(View arg0)
	{
		String label = name.getText().toString();
		int color = ((ColorDrawable)colorEdit.getBackground()).getColor();
		String strColor = String.format("#%06X", 0xFFFFFF & color);
		
		if( label.trim().equals("") ){
			return;
		}
		
		for( int i = 0 ; i < adapter.getCount() ; i++ ){
			Secteur storedSecteur = (Secteur) adapter.getItem(i);
			if( storedSecteur.getName().equals( label ) ){
				Toast.makeText(context, "Le secteur "+ label + " existe déjà."
						, Toast.LENGTH_LONG).show();
				return;
			}
		}
		Secteur created = new Secteur(intervention);
		created.setName(label);
		created.setColor(strColor);
		adapter.addSecteur(created);
		adapter.notifyDataSetChanged();
		created.save();
		
		this.name.setText("");
		this.colorEdit.setBackgroundColor( Color.WHITE );
		 
		//Masquer le clavier :
		InputMethodManager imm = (InputMethodManager) context.getSystemService(
			      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow( this.name.getWindowToken(), 0);
		
	}

}
