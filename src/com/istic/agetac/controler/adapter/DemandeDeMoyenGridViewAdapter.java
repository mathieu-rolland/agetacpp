package com.istic.agetac.controler.adapter;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.istic.agetac.controllers.listeners.demandeDeMoyens.DisplayItemIntoGridView;
import com.istic.agetac.fragments.DemandeDeMoyensFragment;

/**
* Classe DemandeDeMoyenGridViewAdapter pour DemandeDeMoyensFragment : affiche la fenêtre de demande des moyens et permet de créer une liste de demandes de moyens
* et de la soumettre ensuite.
*
* @author Anthony LE MEE - 10003134
*/
public class DemandeDeMoyenGridViewAdapter extends BaseAdapter {
	
	/** Instance du fragment principale */
	private DemandeDeMoyensFragment fragment;
	
	/** Aller chercher dans la BDD et crééer un tableau (moyens.values est un tableau) */
	private String[] moyens;
	
	/**
	 * Constructeur DemandeDeMoyenGridViewAdapter
	 * @param DemandeDeMoyensFragment Fragment
	 * @param moyens String[] tableau des éléments à afficher
	 */
	public DemandeDeMoyenGridViewAdapter (DemandeDeMoyensFragment demandeDeMoyensFragment, String[] moyens) {
		
		this.fragment = demandeDeMoyensFragment;
		this.moyens = moyens;
		
	}

	/**
	 * Récupération du nombre d'élément dans l'adapter
	 * @return int
	 */
	public int getCount() {
		return moyens.length;
	}

	/**
	 * Méthode appellée afin d'obtenir un élément à la position spécifié du jeu de données
	 * @param position int 
	 * @param convertView View
	 * @param parent ViewGroup
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// thème des items de la gridView
		Button btn;
		
		if (convertView != null)
	  	{
	  		btn = (Button) convertView;
	  	}
		else
		{
			btn = new Button(this.fragment.getActivity());
		}
		
		// Création du boutton qui fera fois d'item dans la gridView
		btn.setText((String)(moyens[position]));
		btn.setTextSize(25);
		btn.setGravity(Gravity.CENTER);
		btn.setBackgroundColor(Color.parseColor("#293133"));
		btn.setPadding(0, 18, 0, 18);
		btn.setId(position);
		btn.setClickable(true);
		
		// Ajout du listener (Click)
		btn.setOnClickListener(new DisplayItemIntoGridView(this.fragment, position));

		// On retourne le boutton créé
		return btn;
		
	}

	/**
	 * Non implémentée
	 * @param position int
	 * @return Object
	 */
	public Object getItem(int position) {
		return null;
	}

	/**
	 * Non implémentée
	 * @param position int
	 * @return long
	 */
	public long getItemId(int position) {
		return 0;
	} 

}

