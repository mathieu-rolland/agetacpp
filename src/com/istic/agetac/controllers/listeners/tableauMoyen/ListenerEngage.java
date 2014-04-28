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
	private Moyen mItemMoyen; // Instance de l'item à traiter
	private MoyenListCodisAdapter mAdapterMoyen; // Instance de la vue d'où est joué le listener
	private View mView;

	public ListenerEngage(Moyen item, MoyenListCodisAdapter adapter, View view)
	{		
		this.mItemMoyen = item;
		this.mAdapterMoyen = adapter;
		this.mView = view;
	}
	
	
	@SuppressLint("SimpleDateFormat")
	@Override
	public void onClick(View v) {
		
        LayoutInflater factory = LayoutInflater.from(v.getContext());
        final View alertDialogView = factory.inflate(R.layout.dialog_tableau_moyen_engage, null);
 
        //Création de l'AlertDialog
        AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());
 
        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        adb.setView(alertDialogView);
 
        //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
            	String nom = ((EditText) alertDialogView.findViewById(R.id.dialog_tableau_moyen_name)).getText().toString();
				mItemMoyen.setLibelle(nom);
				mAdapterMoyen.setEngage(mItemMoyen, new Date());			
				dialog.cancel();
          } });
 
        adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	dialog.cancel();
          } });
        adb.show();
	}
}