package com.istic.agetac.controler.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
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
import com.istic.agetac.controllers.listeners.tableauMoyen.ListenerEngage;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.Secteur;

public class MoyenListCodisAdapter extends AMoyenListAdapter {

	/* Instances des modèles à utiliser */
	private SecteurDao mSecteur; // Modèle Secteur

	/* Données récupérées */
	private List<Moyen> datasListMoyen;
	private List<Secteur> datasListSecteur;

	/* Context */
	private Context context;

	/* Éléments graphiques / Adapter */
	private ArrayAdapter<Secteur> adapterSecteurs; // Adapter des secteurs

	private List<Spinner> spinners;

	public MoyenListCodisAdapter(Context context,List<Moyen> moyens) {
		super(context, moyens);
		this.spinners = new ArrayList<Spinner>();
		this.datasListMoyen = moyens;
		this.datasListSecteur = new ArrayList<Secteur>();
		this.context = context;
		this.mSecteur = new SecteurDao(new SecteurViewReceiver());
		this.mSecteur.findAll();
		this.setAdapterSecteurs(new ArrayAdapter<Secteur>(context,
				android.R.layout.simple_spinner_item, this.datasListSecteur));
	}

	public void updateDatasSpinners(List<Spinner> spinners) {
		
		for(Spinner spinner : this.spinners) {
			this.setAdapterSecteurs(new ArrayAdapter<Secteur>(context,
					R.layout.item_secteur_tableau_moyen, this.datasListSecteur));
			spinner.setAdapter(getAdapterSecteurs());
			spinner.setEnabled(false);
			// FIXME spinner.getChildAt(0).setBackgroundColor(Color.parseColor(((Secteur)spinner.getSelectedItem()).getColor()));
		}
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Moyen current;

		if (datasListMoyen == null) {
			current = null;
		} else {
			current = datasListMoyen.get(position);
		}

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
		holder.buttonDemand.setVisibility(Button.VISIBLE);
		holder.name.setText("");
        
		holder.logo.setImageResource(current.getRepresentationOK().getDrawable());

        if(!AMoyenListAdapter.isNullOrBlank(current.getLibelle()))
        {
        	holder.name.setText(current.getLibelle());
        }
        
        if (!AMoyenListAdapter.isNullOrBlank(current.getHDemande()))
        {
        	holder.hourDemand.setText(current.getHDemande());  
        	holder.hourDemand.setVisibility(TextView.VISIBLE);
        	holder.buttonDemand.setOnClickListener(new ListenerEngage(current,this));
        }
        
		if (!AMoyenListAdapter.isNullOrBlank(current.getHEngagement()))
		{
        	holder.hourEngage.setText(current.getHEngagement()); 
        	holder.hourEngage.setVisibility(TextView.VISIBLE);
        	holder.buttonDemand.setVisibility(Button.GONE);
        	holder.name.setText(current.getLibelle());
        }
        
		if (!AMoyenListAdapter.isNullOrBlank(current.getHArrival()))
        {
        	holder.hourArrived.setText(current.getHArrival());
        }
        
		if (!AMoyenListAdapter.isNullOrBlank(current.getHFree()))
        {
        	holder.hourFree.setText(current.getHFree());
        }
        
		if (!this.spinners.contains(holder.spinnerChoixSecteurs)) 
		{
			this.spinners.add(holder.spinnerChoixSecteurs);
		}
		
		this.updateDatasSpinners(this.spinners);
		convertView.setTag(holder);
        
        return convertView;
	}
	
	/**
	 * Méthode qui affiche un toast suite à la réception d'un message
	 * 
	 * @param message
	 */
	public void onMessageReveive(String message) {
		try {
			Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
		}
	}
	
	public void setEngage(Moyen item, Date dateEngage) {
		item.setHEngagement(dateEngage);
		this.notifyDataSetChanged();
	}
	
	public class SecteurViewReceiver implements IViewReceiver<Secteur> {
		@Override
		public void notifyResponseSuccess(List<Secteur> secteurs) {
			Log.d("Antho", String.valueOf(secteurs.size()));
			datasListSecteur = secteurs;
			getAdapterSecteurs().notifyDataSetChanged();
			onMessageReveive("Récupération réussie des secteurs");
			if (spinners != null) {
				updateDatasSpinners(spinners);
			}

		}

		@Override
		public void notifyResponseFail(VolleyError error) {
			onMessageReveive("Impossible de récupérer les secteurs");
		}

	}
	/**
	 * @return the mSecteur
	 */
	public SecteurDao getmSecteur() {
		return mSecteur;
	}

	/**
	 * @param mSecteur the mSecteur to set
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
	 * @param adapterSecteurs the adapterSecteurs to set
	 */
	public void setAdapterSecteurs(ArrayAdapter<Secteur> adapterSecteurs) {
		this.adapterSecteurs = adapterSecteurs;
	}
}
