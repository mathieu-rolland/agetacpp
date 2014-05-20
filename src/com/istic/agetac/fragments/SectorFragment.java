package com.istic.agetac.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.istic.agetac.R;
import com.istic.agetac.controler.adapter.SecteurAdapter;
import com.istic.agetac.controllers.listeners.secteurs.ListenerSecteur;
import com.istic.agetac.model.Secteur;

public class SectorFragment extends Fragment implements OnDragListener {

	public static Fragment newInstance() {
		SectorFragment fragment = new SectorFragment();
		return fragment;
	}
	
	private Button createSector;
	private Button cancelSector;
	private ImageButton deleteSecteur;
	
	private EditText libelleEdit;
	private LinearLayout colorEdit;
	
	private SecteurAdapter adapter;
	private ListView secteurList;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_create_sector, container, false);
		
		createSector = ( Button ) rootView.findViewById(R.id.fragment_create_sector_validate);
		cancelSector = ( Button ) rootView.findViewById(R.id.fragment_create_sector_cancel);
		secteurList = (ListView) rootView.findViewById(R.id.fragment_create_sector_created);
		deleteSecteur = (ImageButton) rootView.findViewById(R.id.fragment_create_sector_sector_delete);
		
		adapter = new SecteurAdapter( getActivity() );
		secteurList.setAdapter(adapter);
		
		libelleEdit = (EditText) rootView.findViewById(R.id.fragment_create_sector_libelle);
		colorEdit = (LinearLayout) rootView.findViewById(R.id.fragment_create_sector_color_edit);
		
		loadSecteurs();
		
		createSector.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Secteur s = new Secteur();
				s.setName( libelleEdit.getText().toString() );
				int color = ((ColorDrawable)colorEdit.getBackground()).getColor();
				String strColor = String.format("#%06X", 0xFFFFFF & color);
				s.setColor(strColor);
				adapter.addSecteur(s);
				adapter.notifyDataSetChanged();
			}
		});
		
		cancelSector.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				libelleEdit.setText("");
				colorEdit.setBackgroundColor( 0x000000 );
			}
		});
		
		secteurList.setOnDragListener( this );
		secteurList.setOnItemLongClickListener(new ListenerSecteur(adapter, getActivity()));
		deleteSecteur.setOnDragListener(this);
		
		return rootView;
	}
	
	private void loadSecteurs(){
		//TODO Implémenter la récupération des secteurs.
		if( adapter.isEmpty() ){
			preconfigure();
		}
		
	}
	
	private void preconfigure(){
		Secteur secteur = new Secteur();
		secteur.setName("INC");
		secteur.setColor("#66CCFF");
		adapter.addSecteur(secteur);
		
		secteur = new Secteur();
		secteur.setName("SAP");
		secteur.setColor("#FF1919");
		adapter.addSecteur(secteur);
		
		secteur = new Secteur();
		secteur.setName("ALIM");
		secteur.setColor("#0000FF");
		adapter.addSecteur(secteur);
		
		secteur = new Secteur();
		secteur.setName("SLL");
		secteur.setColor("#CCCCCC");
		adapter.addSecteur(secteur);
		
		adapter.notifyDataSetChanged();
		Log.d("Secteur" , "Préconfiguration des secteurs => " + adapter.getCount() + " secteurs");
	}

	@Override
	public boolean onDrag(View view, DragEvent event) {
		
		switch ( event.getAction() ) {
		case DragEvent.ACTION_DRAG_STARTED:
			return true;
		case DragEvent.ACTION_DRAG_ENTERED:
			return true;
		case DragEvent.ACTION_DRAG_LOCATION:
			return true;
		case DragEvent.ACTION_DRAG_EXITED:
			return true;
		case DragEvent.ACTION_DROP:
			{
				Secteur s = (Secteur) event.getLocalState();
				Log.d("Drag", "Drag : ACTION-DROP " + view.getId());
				if( view.equals(deleteSecteur) ){
					Log.d("Drag", "Drag : Return TRUE GROS TOTO");
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
//				Secteur s = (Secteur) event.getLocalState();
//				Log.d("Drag", "Drag : ACTION_DRAG_ENDED "  + view.getId() );
//				if( view == deleteSecteur ){
//					return true;
//				}else{
//					adapter.addSecteur(s);
//					adapter.notifyDataSetChanged();
//				}
				break;
			}
		default:
			break;
		}
		return true;
	}
	
}
