package com.istic.agetac.controllers.listeners.tableauMoyen;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.istic.agetac.R;
import com.istic.agetac.controler.adapter.MoyenListCodisAdapter;
import com.istic.agetac.model.Moyen;

public class ListenerEngage implements OnClickListener {

	/** Attributs */
	private Moyen mItemMoyen; // Instance de l'item � traiter
	private MoyenListCodisAdapter mAdapterMoyen; // Instance de la vue d'o� est jou� le listener

	public ListenerEngage(Moyen item, MoyenListCodisAdapter adapter)
	{		
		this.mItemMoyen = item;
		this.mAdapterMoyen = adapter;
	}
	
	
	@SuppressLint("SimpleDateFormat")
	@Override
	public void onClick(View v) {
		
        LayoutInflater factory = LayoutInflater.from(v.getContext());
        final View alertDialogView = factory.inflate(R.layout.dialog_tableau_moyen_engage, null);
 
        //Cr�ation de l'AlertDialog
        AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());
 
        //On affecte la vue personnalis� que l'on a cr�e � notre AlertDialog
        adb.setView(alertDialogView);
 
        //On affecte un bouton "OK" � notre AlertDialog et on lui affecte un �v�nement
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
            	String nom = ((EditText) alertDialogView.findViewById(R.id.dialog_tableau_moyen_name)).getText().toString();
            	if(nom.equals(""))
            		return;
            	
				mItemMoyen.setLibelle(nom);
				mAdapterMoyen.setEngage(mItemMoyen, new Date());
				mItemMoyen.save();
				dialog.cancel();
          } });
 
        adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	dialog.cancel();
          } });
        adb.show();
	}
}