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
* Classe DemandeDeMoyenGridViewAdapter pour DemandeDeMoyensFragment : affiche la fen�tre de demande des moyens et permet de cr�er une liste de demandes de moyens
* et de la soumettre ensuite.
*
* @author Anthony LE MEE - 10003134
*/
public class DemandeDeMoyenGridViewAdapter extends BaseAdapter {
	
	/** Instance du fragment principale */
	private DemandeDeMoyensFragment fragment;
	
	/** Aller chercher dans la BDD et cr��er un tableau (moyens.values est un tableau) */
	private String[] moyens;
	
	/**
	 * Constructeur DemandeDeMoyenGridViewAdapter
	 * @param DemandeDeMoyensFragment Fragment
	 * @param moyens String[] tableau des �l�ments � afficher
	 */
	public DemandeDeMoyenGridViewAdapter (DemandeDeMoyensFragment demandeDeMoyensFragment, String[] moyens) {
		
		this.fragment = demandeDeMoyensFragment;
		this.moyens = moyens;
		
	}

	/**
	 * R�cup�ration du nombre d'�l�ment dans l'adapter
	 * @return int
	 */
	public int getCount() {
		return moyens.length;
	}

	/**
	 * M�thode appell�e afin d'obtenir un �l�ment � la position sp�cifi� du jeu de donn�es
	 * @param position int 
	 * @param convertView View
	 * @param parent ViewGroup
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// th�me des items de la gridView
		Button btn;
		
		if (convertView != null)
	  	{
	  		btn = (Button) convertView;
	  	}
		else
		{
			btn = new Button(this.fragment.getActivity());
		}
		
		// Cr�ation du boutton qui fera fois d'item dans la gridView
		btn.setText((String)(moyens[position]));
		btn.setTextSize(25);
		btn.setGravity(Gravity.CENTER);
		btn.setBackgroundColor(Color.parseColor("#293133"));
		btn.setPadding(0, 18, 0, 18);
		btn.setId(position);
		btn.setClickable(true);
		
		// Ajout du listener (Click)
		btn.setOnClickListener(new DisplayItemIntoGridView(this.fragment, position));

		// On retourne le boutton cr��
		return btn;
		
	}

	/**
	 * Non impl�ment�e
	 * @param position int
	 * @return Object
	 */
	public Object getItem(int position) {
		return null;
	}

	/**
	 * Non impl�ment�e
	 * @param position int
	 * @return long
	 */
	public long getItemId(int position) {
		return 0;
	} 

}

