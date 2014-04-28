package com.istic.agetac.controllers.listeners.tableauMoyen;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;

import com.istic.agetac.controler.adapter.MoyenListIntervenantAdapter;
import com.istic.agetac.model.Moyen;

public class ListenerFree implements OnClickListener {
	
	/** Attributs */
	private Moyen mItemMoyen; // Instance de l'item à traiter
	private MoyenListIntervenantAdapter mAdapterMoyen; // Instance de la vue d'où est joué le listener

	public ListenerFree(Moyen item, MoyenListIntervenantAdapter adapter)
	{		
		this.mItemMoyen = item;
		this.mAdapterMoyen = adapter;
	}
	
	
	@SuppressLint("SimpleDateFormat")
	@Override
	public void onClick(View v) {		    
        
        //Création de l'AlertDialog
        AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());
        
        adb.setTitle("Confirmer la libération du véhicule");
        
        //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {             	
				mAdapterMoyen.setFree(mItemMoyen, new Date());			
				dialog.cancel();
          } });
 
        adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	dialog.cancel();
          } });
        adb.show();
	}
}
