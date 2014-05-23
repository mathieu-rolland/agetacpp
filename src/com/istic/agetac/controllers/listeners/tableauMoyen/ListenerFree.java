package com.istic.agetac.controllers.listeners.tableauMoyen;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;

import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.controler.adapter.MoyenListExpIntervenantAdapter;

public class ListenerFree implements OnClickListener {
	
	/** Attributs */
	private IMoyen mItemMoyen; // Instance de l'item � traiter
	private MoyenListExpIntervenantAdapter mAdapterMoyen; // Instance de la vue d'o� est jou� le listener

	public ListenerFree(IMoyen item, MoyenListExpIntervenantAdapter adapter)
	{		
		this.mItemMoyen = item;
		this.mAdapterMoyen = adapter;
	}
	
	
	@SuppressLint("SimpleDateFormat")
	@Override
	public void onClick(View v) {		    
        
        //Cr�ation de l'AlertDialog
        AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());
        
        adb.setTitle("Confirmer la lib�ration du v�hicule");
        
        //On affecte un bouton "OK" � notre AlertDialog et on lui affecte un �v�nement
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {             	
				mAdapterMoyen.setFree(mItemMoyen, new Date());
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
