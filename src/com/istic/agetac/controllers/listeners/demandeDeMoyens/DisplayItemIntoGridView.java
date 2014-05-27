package com.istic.agetac.controllers.listeners.demandeDeMoyens;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.istic.agetac.fragments.DemandeDeMoyensFragment;

/**
 * Classe ListenerMoyens de DemandeDeMoyensFragment : Permet de changer le th�me
 * de l'item que l'on s�lectionne dans le gridView des moyens.
 * 
 * @author Anthony LE MEE - 10003134
 */
public class DisplayItemIntoGridView implements OnClickListener {

	/** Instance de l'activity principale */
	private DemandeDeMoyensFragment demandeDeMoyens;

	/** Position actuel du moyen s�lectionn� */
	private final int position;

	/**
	 * Construteur
	 * 
	 * @param a
	 *            DemandeDeMoyensFragment de DemandeDeMoyensFragment
	 * @param position
	 *            int de l'item
	 * @param s
	 *            savedInstanceStateDemandeMoyens de DemandeDeMoyensFragment
	 * @param listMoyen
	 *            GridView des moyens
	 */
	public DisplayItemIntoGridView(DemandeDeMoyensFragment a, int position) {
		this.demandeDeMoyens = (DemandeDeMoyensFragment) a;
		this.position = position;
	}

	/**
	 * Lors d'un click sur la View v
	 * 
	 * @param v
	 *            View
	 */
	public void onClick(View v) {

		if (v != null) {

			// Pour chaque moyen du GridView on les d�sactive
			for (int i = 0; i < this.demandeDeMoyens.getGridViewMoyens()
					.getCount(); i++) {

				try {
					if (i != position) {
						// Si ce n'est pas celui sur lequel j'ai cliqu�,
						// alors je mets le th�me sombre
						((Button) this.demandeDeMoyens.getGridViewMoyens()
								.getChildAt(i)).setBackgroundColor(Color
								.parseColor(new String("#293133")));

					} else {
						// Sinon je lance la surbrillance
						((Button) this.demandeDeMoyens.getGridViewMoyens()
								.getChildAt(i)).setBackgroundColor(Color
								.parseColor(new String("#efefef")));

						// On met � vide le champs autre moyen - ordre
						// important !!
						this.demandeDeMoyens.getTextViewAutresMoyens().setText(
								"");
						// Et ensuite on set l'indice
						this.demandeDeMoyens.getSauvegarde().setIndiceMoyen(i);

						this.demandeDeMoyens.setSelectedTypeMoyen(null);

					}
				} catch (Exception e) {
					// FIXME nullPointerException � cause du fait que le boutton
					// n'�tait pas affich� (trop d'�l�ment)
					// Ainsi l'indice n'est pas le m�me au scroll
					// Et on d�passe le tableau
				}

			}

		}

	}

	/**
	 * @param demandeDeMoyens
	 *            the demandeDeMoyens to set
	 */
	public void setDemandeDeMoyens(DemandeDeMoyensFragment demandeDeMoyens) {
		this.demandeDeMoyens = demandeDeMoyens;
	}

	/**
	 * @return the demandeDeMoyens
	 */
	public DemandeDeMoyensFragment getDemandeDeMoyens() {
		return demandeDeMoyens;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

}
