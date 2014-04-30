package com.istic.agetac.controler.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.istic.agetac.R;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.controllers.dao.SecteurDao;
import com.istic.agetac.controllers.listeners.tableauMoyen.ListenerFree;
import com.istic.agetac.controllers.listeners.tableauMoyen.SwitchSector;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.Secteur;

public class MoyenListIntervenantAdapter extends AMoyenListAdapter {

	/* Instances des mod�les � utiliser */
	private SecteurDao mSecteur; // Mod�le Secteur

	/* Controlers */
	private SwitchSector cSecteur;

	/* Context */
	private Context context;

	/* �l�ments graphiques / Adapter */
	private ArrayAdapter<Secteur> adapterSecteurs; // Adapter des secteurs

	private List<Spinner> spinners;

	public MoyenListIntervenantAdapter(Context context) {
		super(context);
		this.spinners = new ArrayList<Spinner>();
		this.context = context;
//		this.mSecteur = new SecteurDao(new SecteurViewReceiver());
//		this.mSecteur.findAll();
		this.setAdapterSecteurs(new ArrayAdapter<Secteur>(context,
				android.R.layout.simple_spinner_item, this.datasListSecteur));
	}
	
	public void updateDatasSpinners(List<Spinner> spinners) {
			
		for(Spinner spinner : this.spinners) {
			this.setAdapterSecteurs(new ArrayAdapter<Secteur>(context,
					R.layout.item_secteur_tableau_moyen, datasListSecteur));
			spinner.setAdapter( getAdapterSecteurs() );
		}
		
	}
	
	public void updateDataSpinner(Spinner spinner) {
		this.setAdapterSecteurs(new ArrayAdapter<Secteur>(context,
				R.layout.item_secteur_tableau_moyen, datasListSecteur));
		spinner.setAdapter( getAdapterSecteurs() );
	}
	
	public void secteurDataChanged(){
		this.adapterSecteurs.notifyDataSetChanged();
		this.updateDatasSpinners(this.spinners);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Moyen current;

//		if (datasListMoyen == null) {
//			current = null;
//		} else {
//			current = datasListMoyen.get(position);
//		}
		current = mList.get(position);

		ViewHolder holder;

		if (convertView == null) {

			convertView = super.mInflater.inflate(R.layout.item_moyen, null);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder = new ViewHolder();
		holder.logo = (ImageView) convertView
				.findViewById(R.id.list_moyen_logo);
		holder.hourDemand = (TextView) convertView
				.findViewById(R.id.list_moyen_hour_demand);
		holder.hourEngage = (TextView) convertView
				.findViewById(R.id.list_moyen_hour_engage);
		holder.hourArrived = (TextView) convertView
				.findViewById(R.id.list_moyen_hour_arrive);
		holder.hourFree = (TextView) convertView
				.findViewById(R.id.list_moyen_hour_free);
		holder.buttonFree = (Button) convertView
				.findViewById(R.id.list_moyen_button_free);
		
		holder.spinnerChoixSecteurs = (Spinner) convertView
				.findViewById(R.id.list_moyen_button_sector);
		holder.buttonDemand = (Button) convertView
				.findViewById(R.id.list_moyen_button_engage);
		holder.name = (TextView) convertView.findViewById(R.id.list_moyen_name);
		
		holder.buttonFree.setVisibility(Button.GONE);
		holder.buttonDemand.setVisibility(Button.GONE);
		holder.spinnerChoixSecteurs.setVisibility(Button.GONE);
		holder.name.setText("");
		
		if (current != null) {
			
			holder.logo.setImageResource(current.getRepresentationOK().getDrawable());
			holder.buttonFree.setOnClickListener(new ListenerFree(current, this));
			
			if (!AMoyenListAdapter.isNullOrBlank(current.getHDemande())) {
				holder.hourDemand.setText(current.getHDemande());
				if (AMoyenListAdapter.isNullOrBlank(current.getHEngagement())) {
					holder.hourEngage.setText("En attente du codis");
					holder.hourEngage.setVisibility(View.VISIBLE);
				}
			}

			if (!AMoyenListAdapter.isNullOrBlank(current.getHEngagement())) {
				holder.hourEngage.setText(current.getHEngagement());
				holder.hourEngage.setVisibility(View.VISIBLE);
				holder.name.setText(current.getLibelle());
				holder.buttonDemand.setVisibility(View.GONE);
				//FIXME
				holder.spinnerChoixSecteurs.setVisibility(Button.GONE);
			}

			if (!AMoyenListAdapter.isNullOrBlank(current.getHArrival())) {
				holder.hourArrived.setText(current.getHArrival());
				holder.hourArrived.setVisibility(View.VISIBLE);
				holder.buttonFree.setVisibility(View.VISIBLE);
			}

			if (!AMoyenListAdapter.isNullOrBlank(current.getHFree())) {
				holder.hourFree.setVisibility(View.VISIBLE);
				holder.buttonFree.setVisibility(View.GONE);
				holder.hourFree.setText(current.getHFree());
			}
		}

		
		if (!this.spinners.contains(holder.spinnerChoixSecteurs)) {
			this.spinners.add(holder.spinnerChoixSecteurs);
			updateDataSpinner(holder.spinnerChoixSecteurs);
			this.cSecteur = new SwitchSector(this,holder.spinnerChoixSecteurs);
			holder.spinnerChoixSecteurs.setOnItemSelectedListener(cSecteur);
		}
		
		//this.updateDatasSpinners(this.spinners);
		convertView.setTag(holder);

		return convertView;
	}

	/**
	 * M�thode qui affiche un toast suite � la r�ception d'un message
	 * 
	 * @param message
	 */
	public void onMessageReveive(String message) {
		try {
			Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
		}
	}

	public class SecteurViewReceiver implements IViewReceiver<Secteur> {
		@Override
		public void notifyResponseSuccess(List<Secteur> secteurs) {
			Log.d("Antho", String.valueOf(secteurs.size()));
			datasListSecteur = secteurs;
			getAdapterSecteurs().notifyDataSetChanged();
			onMessageReveive("R�cup�ration r�ussie des secteurs");
			if (spinners != null) {
				updateDatasSpinners(spinners);
			}

		}

		@Override
		public void notifyResponseFail(VolleyError error) {
			onMessageReveive("Impossible de r�cup�rer les secteurs");
		}

	}

	public void setFree(Moyen mItemMoyen, Date date) {
		mItemMoyen.setHFree(date);
		this.notifyDataSetChanged();
	}

	/**
	 * @return the mSecteur
	 */
	public SecteurDao getmSecteur() {
		return mSecteur;
	}

	/**
	 * @param mSecteur
	 *            the mSecteur to set
	 */
	public void setmSecteur(SecteurDao mSecteur) {
		this.mSecteur = mSecteur;
	}

	/**
	 * @return the adapterSecteurs
	 */
	public ArrayAdapter<Secteur> getAdapterSecteurs() {
		return adapterSecteurs;
	}

	/**
	 * @param adapterSecteurs
	 *            the adapterSecteurs to set
	 */
	public void setAdapterSecteurs(ArrayAdapter<Secteur> adapterSecteurs) {
		this.adapterSecteurs = adapterSecteurs;
	}

	/**
	 * @return the cSecteur
	 */
	public SwitchSector getcSecteur() {
		return cSecteur;
	}

	/**
	 * @param cSecteur
	 *            the cSecteur to set
	 */
	public void setcSecteur(SwitchSector cSecteur) {
		this.cSecteur = cSecteur;
	}
	
	/**
	 * @return the datasListSecteur
	 */
	public List<Secteur> getDatasListSecteur() {
		return datasListSecteur;
	}

	/**
	 * @param datasListSecteur
	 *            the datasListSecteur to set
	 */
	public void setDatasListSecteur(List<Secteur> datasListSecteur) {
		this.datasListSecteur = datasListSecteur;
	}

	/**
	 * @return the spinners
	 */
	public List<Spinner> getSpinners() {
		return spinners;
	}

	/**
	 * @param spinners the spinners to set
	 */
	public void setSpinners(List<Spinner> spinners) {
		this.spinners = spinners;
	}

	public void notifySecteurChanged(Spinner spinner, int position) {
		Log.d("Antho", "notifySecteurChanged");
		Secteur secteurSelected = (Secteur) spinner.getSelectedItem();
	}

}
