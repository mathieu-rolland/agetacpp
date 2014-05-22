package com.istic.agetac.fragments;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.istic.agetac.R;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.api.model.ISecteur;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controler.adapter.SecteurAdapter;
import com.istic.agetac.controllers.dao.SecteurDao;
import com.istic.agetac.controllers.listeners.secteurs.ListenerAddSecteur;
import com.istic.agetac.controllers.listeners.secteurs.ListenerDragSecteur;
import com.istic.agetac.model.IMoyen;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.Secteur;

public class SectorFragment extends Fragment implements OnDragListener {

	public static Fragment newInstance() {
		SectorFragment fragment = new SectorFragment();
		return fragment;
	}

	private Button createSector;
	private Button cancelSector;
	private Button placerCrm;
	private ImageButton deleteSecteur;

	private EditText libelleEdit;
	private LinearLayout colorEdit;

	private SecteurAdapter adapter;
	private ListView secteurList;

	private Secteur sll;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_create_sector, container, false);

		createSector = ( Button ) rootView.findViewById(R.id.fragment_create_sector_validate);
		cancelSector = ( Button ) rootView.findViewById(R.id.fragment_create_sector_cancel);
		placerCrm = (Button) rootView.findViewById(R.id.fragment_create_sector_place_crm);
		secteurList = (ListView) rootView.findViewById(R.id.fragment_create_sector_created);
		deleteSecteur = (ImageButton) rootView.findViewById(R.id.fragment_create_sector_sector_delete);

		adapter = new SecteurAdapter( getActivity(), AgetacppApplication.getIntervention().getSecteurs() );
		secteurList.setAdapter(adapter);

		libelleEdit = (EditText) rootView.findViewById(R.id.fragment_create_sector_libelle);
		colorEdit = (LinearLayout) rootView.findViewById(R.id.fragment_create_sector_color_edit);

		loadSecteurs();

		createSector.setOnClickListener(new ListenerAddSecteur(adapter, getActivity(), libelleEdit, colorEdit));

		cancelSector.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				libelleEdit.setText("");
				colorEdit.setBackgroundColor( Color.WHITE );

				//Masquer le clavier :
				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
						Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow( SectorFragment.this.libelleEdit.getWindowToken(), 0);
			}
		});

		secteurList.setOnDragListener( this );
		secteurList.setOnItemLongClickListener(new ListenerDragSecteur(adapter, getActivity()));
		deleteSecteur.setOnDragListener(this);

		placerCrm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				placerCrm();
			}
		});
		
		return rootView;
	}

	private void loadSecteurs(){
		SecteurDao sdao = new SecteurDao( new OnSecteurReceived() );
		sdao.findAll();
	}

	//Récupération des secteurs :
	private class OnSecteurReceived implements IViewReceiver<Secteur>
	{
		@Override
		public void notifyResponseSuccess(List<Secteur> objects) {
			if( objects != null ){
				SectorFragment.this.adapter.addAll(objects);
				if( adapter.isEmpty() ){
					preconfigure();
				}else{
					findSecteurSll();
				}
			}
			adapter.notifyDataSetChanged();
		}

		@Override
		public void notifyResponseFail(VolleyError error) {
			Toast.makeText(getActivity(), "Erreur lors du chargement des secteurs"
					, Toast.LENGTH_LONG).show();
		}
	}

	private void findSecteurSll() {
		for( int i = 0 ; i < adapter.getCount() ; i++ ){
			Secteur storedSecteur = (Secteur) adapter.getItem(i);
			if( storedSecteur.getName().toUpperCase( Locale.FRENCH ).equals("SLL") ){
				sll = storedSecteur;
				return;
			}
		}
		sll = new Secteur();
		sll.setName("SLL");
		sll.setColor("#CCCCCC");
		sll.save();
		adapter.addSecteur(sll);
		adapter.notifyDataSetChanged();
	}

	private void preconfigure(){
		Secteur secteur = new Secteur();
		secteur.setName("INC");
		secteur.setColor("#66CCFF");
		secteur.save();
		adapter.addSecteur(secteur);

		secteur = new Secteur();
		secteur.setName("SAP");
		secteur.setColor("#FF1919");
		secteur.save();
		adapter.addSecteur(secteur);

		secteur = new Secteur();
		secteur.setName("ALIM");
		secteur.setColor("#0000FF");
		secteur.save();
		adapter.addSecteur(secteur);

		sll = new Secteur();
		sll.setName("SLL");
		sll.setColor("#CCCCCC");
		sll.save();
		adapter.addSecteur(sll);

		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onDrag(View view, DragEvent event) {

		switch ( event.getAction() ) {
		case DragEvent.ACTION_DRAG_STARTED:
			return true;
		case DragEvent.ACTION_DRAG_ENTERED:
			if( view == deleteSecteur ){
				view.setBackgroundColor( Color.RED );
				view.setAlpha(60);
			}
			return true;
		case DragEvent.ACTION_DRAG_LOCATION:
			return true;
		case DragEvent.ACTION_DRAG_EXITED:
			if( view == deleteSecteur ){
				view.setBackgroundColor( Color.WHITE );
				view.setAlpha(100);

			}
			return true;
		case DragEvent.ACTION_DROP:
		{
			
			deleteSecteur.setBackgroundColor( Color.WHITE );
			
			Secteur dragged = (Secteur) event.getLocalState();
			if( view.equals(deleteSecteur) ){
				dragged.delete();
				reafectMoyens(dragged);
				return true;
			}else{
				return false;
			}
		}
		case DragEvent.ACTION_DRAG_ENDED:
		{
			if( !event.getResult() && view == secteurList  ){
				Secteur s = (Secteur) event.getLocalState();
				adapter.addSecteur(s);
				adapter.notifyDataSetChanged();
				return true;
			}
			break;
		}
		default:
			break;
		}
		return true;
	}

	private void reafectMoyens( ISecteur deletedSecteur ){
		List<Moyen> moyens = deletedSecteur.getMoyens();
		for( Moyen m : moyens ){
			sll.addMoyen(m);
		}
	}

	public void placerCrm(){
		Secteur crm = null;
		
		for( int i = 0 ; i < adapter.getCount() ; i++ )
		{
			Secteur storedSecteur = (Secteur) adapter.getItem(i);
			if( storedSecteur.getName().toUpperCase(Locale.FRENCH).equals("CRM") )
			{
				crm = storedSecteur;
				break;
			}
		}
		
		if( crm == null ){
			Toast.makeText(getActivity(), "Le secteur CRM n'est pas définit"
					, Toast.LENGTH_LONG).show();
			return;
		}
		
	}
	
}
