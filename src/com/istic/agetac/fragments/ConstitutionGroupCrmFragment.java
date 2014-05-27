package com.istic.agetac.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.istic.agetac.R;
import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controler.adapter.ConstitutionGroupCrmListAdapter;
import com.istic.agetac.controler.adapter.ConstitutionGroupCrmListGroupAdapter;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.Secteur;
import com.istic.agetac.model.TypeMoyen;

/**
 * Classe DemandeDeMoyensFragment : affiche la fenétre de constitution des
 * groupes avec les moyen présents au CRM.
 * 
 * @author Anthony LE MEE - 10003134
 */
public class ConstitutionGroupCrmFragment extends Fragment
{

    /** Instances des modéles à utiliser */
    private List<IMoyen> mListMoyen;

    private List<IMoyen> mListGroup;

    private Intervention intervention;

    private ListView listViewMoyensAtCrm;

    private ListView listViewMoyensGroup;

    private ConstitutionGroupCrmListAdapter adapterMoyen;

    private ConstitutionGroupCrmListGroupAdapter adapterGroup;

    /**
     * Méthode qui affiche un toast suite é la réception d'un message
     * 
     * @param message
     */
    public void onMessageReveive( String message )
    {
        try
        {
            Toast.makeText( getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT ).show();
        }
        catch ( Exception e )
        {
        }
    }

    /**
     * Methode qui créé une nouvelle instance du fragment
     * 
     * @return ConstitutionGroupCrmFragment
     */
    public static ConstitutionGroupCrmFragment newInstance()
    {
        ConstitutionGroupCrmFragment fragment = new ConstitutionGroupCrmFragment();
        return fragment;
    }

    /** Méthode onCreate */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {

        super.onCreate( savedInstanceState );

    }

    /** Méthode onCreateView */
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {

        /** Chargement du layout */
        View view = inflater.inflate( R.layout.fragment_consitution_groupe_crm, container, false );

        /** Instanciations des contrôlers */

        /** Instanciations des modéles */
        this.setIntervention( AgetacppApplication.getIntervention() );
        this.setmListMoyen( new ArrayList<IMoyen>() );
        this.setmListGroup( new ArrayList<IMoyen>() );

        /** Instanciation des adapters */
        this.setAdapterMoyen( new ConstitutionGroupCrmListAdapter( this, android.R.layout.simple_dropdown_item_1line, this.mListMoyen ) );

        this.setAdapterGroup( new ConstitutionGroupCrmListGroupAdapter( this, android.R.layout.simple_dropdown_item_1line, this.mListGroup ) );

        /** LOG */
        Log.d( "Antho", "Instanciations faites" );

        return view;

    }

    /** Méthode onActivityCreated */
    @Override
    public void onActivityCreated( Bundle savedInstanceState )
    {

        super.onCreate( savedInstanceState );

        // Récupération des éléments
        this.listViewMoyensAtCrm = (ListView) getActivity().findViewById( R.id.consitution_group_crm_listviewMoyensArrived );
        this.listViewMoyensAtCrm.setAdapter( this.adapterMoyen );
        this.listViewMoyensGroup = (ListView) getActivity().findViewById( R.id.consitution_group_crm_listGroup );
        this.listViewMoyensGroup.setAdapter( this.adapterGroup );

        Button newGroup = (Button) getActivity().findViewById( R.id.consitution_group_crm_button_addGroup );
        newGroup.setOnClickListener( new OnClickListener()
        {

            @Override
            public void onClick( View v )
            {

                // Création de l'AlertDialog
                AlertDialog.Builder adb = new AlertDialog.Builder( getView().getContext() );
                final AlertDialog alert = adb.create();
                alert.show();
                alert.setContentView( R.layout.dialog_constitution_group_crm_add );

                // On set la valeur du titre de la boite de Dialog
                ( (TextView) alert.findViewById( R.id.constitution_group_crm_DialogAddGroup_TextView_Title ) ).setText( "Choisissez le nom du groupe que vous souhaitez créer" );

                alert.getWindow().clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM );

                // On pose le listener d'annulation de suppression
                alert.findViewById( R.id.constitution_group_crm_DialogAddGroup_Button_Cancel ).setOnClickListener( new OnClickListener()
                {

                    public void onClick( View v )
                    {

                        // On ferme la boite de dialogue
                        alert.cancel();

                    }
                } );

                // On pose le listener de confirmation de suppression
                alert.findViewById( R.id.constitution_group_crm_DialogAddGroup_Button_Add ).setOnClickListener( new OnClickListener()
                {

                    public void onClick( View v )
                    {

                        String tmp = ( (EditText) alert.findViewById( R.id.constitution_group_crm_DialogAddGroup_TextEdit_Name ) ).getText().toString();

                        IMoyen groupe = new Moyen( new ArrayList<IMoyen>(), AgetacppApplication.getIntervention() );
                        groupe.setLibelle(tmp);
                        
                        // Lancement de la mise à jour
                        getmListGroup().add( groupe );

                        groupe.save();

                        getAdapterGroup().notifyDataSetChanged();

                        // On ferme la boite de dialogue
                        alert.cancel();

                    }
                } );
            }
        } );

        /** Récupération des données */
        retrieveDatas();

    } // method

    /********************************************************************************************************/
    /** Receiver methods */
    /********************************************************************************************************/

    public void updateVue()
    {
        this.retrieveDatas();
    } // method

    /**
     * Method which retrieves Moyen in BDD and save them only if their are not in a groupe
     */
    public void retrieveMoyenInBDD()
    {

        this.getmListMoyen().clear();
        List<IMoyen> moyenFiltered = new ArrayList<IMoyen>();
        for ( IMoyen m : this.getIntervention().getMoyens() )
        {
            if ( !m.isInGroup() && m.getSecteur() != null )
            {
                if ( m.getSecteur().getLibelle().equals( "CRM" ) )
                {
                    moyenFiltered.add( m );
                }
            }
        }

        if ( moyenFiltered.size() == 0 )
        {
            this.getActivity().findViewById( R.id.consitution_group_crm_listviewMoyensArrived ).setVisibility( View.GONE );
            this.getActivity().findViewById( R.id.panel_empty_moyen ).setVisibility( View.VISIBLE );
        }
        else
        {
            this.getActivity().findViewById( R.id.consitution_group_crm_listviewMoyensArrived ).setVisibility( View.VISIBLE );
            this.getActivity().findViewById( R.id.panel_empty_moyen ).setVisibility( View.GONE );
        }

        this.setmListMoyen( moyenFiltered );
        this.getAdapterMoyen().notifyDataSetChanged();
        this.listViewMoyensAtCrm.setAdapter( this.getAdapterMoyen() );
        onMessageReveive( "Récupération des données MOYEN réussie !" );

    } // method

    /**
     * Method which retrieves Group and Moyens in BDD
     */
    public void retrieveDatas()
    {

        this.getmListGroup().clear();
        this.getmListMoyen().clear();
        
        ArrayList<IMoyen> listIMoyenRetrieved = new ArrayList<IMoyen>();
        ArrayList<IMoyen> listIGroupRetrieved = new ArrayList<IMoyen>();
        for ( IMoyen g : this.getIntervention().getMoyens() )
        {

        	if (g.isGroup()) {
	            listIGroupRetrieved.add( g );
	            List<IMoyen> listMoyenOfGroup = g.getListMoyen();
	            Log.d( "Antho", g.toString() );
	            if ( listMoyenOfGroup != null )
	            {
	                for ( IMoyen moyen : listMoyenOfGroup )
	                {
	                    if ( moyen.isInGroup() && moyen.getSecteur() != null && moyen.getSecteur().getLibelle().equals( "CRM" ) )
	                    {
	                    	listIGroupRetrieved.add( moyen );
	                        Log.d( "Antho", " -- " + moyen.toString() );
	                    }
	                }
	            }
        	}
        	else
        	{
        		listIMoyenRetrieved.add(g);
        	}
        }

        if ( listIGroupRetrieved.size() == 0 )
        {
            this.getActivity().findViewById( R.id.consitution_group_crm_listGroup ).setVisibility( View.GONE );
            this.getActivity().findViewById( R.id.panel_empty_group ).setVisibility( View.VISIBLE );
        }
        else
        {
            this.getActivity().findViewById( R.id.consitution_group_crm_listGroup ).setVisibility( View.VISIBLE );
            this.getActivity().findViewById( R.id.panel_empty_group ).setVisibility( View.GONE );
        }

        this.getmListGroup().addAll( listIGroupRetrieved );
        this.getAdapterGroup().notifyDataSetChanged();
        this.listViewMoyensGroup.setAdapter( this.getAdapterGroup() );
        onMessageReveive( "Récupération des données GROUPES réussie !" );
        
        if ( listIMoyenRetrieved.size() == 0 )
        {
            this.getActivity().findViewById( R.id.consitution_group_crm_listviewMoyensArrived ).setVisibility( View.GONE );
            this.getActivity().findViewById( R.id.panel_empty_moyen ).setVisibility( View.VISIBLE );
        }
        else
        {
            this.getActivity().findViewById( R.id.consitution_group_crm_listviewMoyensArrived ).setVisibility( View.VISIBLE );
            this.getActivity().findViewById( R.id.panel_empty_moyen ).setVisibility( View.GONE );
        }

        this.getmListMoyen().addAll(listIMoyenRetrieved);
        this.getAdapterMoyen().notifyDataSetChanged();
        this.listViewMoyensAtCrm.setAdapter( this.getAdapterMoyen() );
        onMessageReveive( "Récupération des données MOYENS réussie !" );

    } // method

    /********************************************************************************************************/
    /**
     * GETTEURS ET SETTEURS /
     ********************************************************************************************************/

    /**
     * @return the mListGroup
     */
    public List<IMoyen> getmListGroup()
    {
        return mListGroup;
    }

    /**
     * @param mListGroup
     * the mListGroup to set
     */
    public void setmListGroup( List<IMoyen> mListGroup )
    {
        this.mListGroup = mListGroup;
    }

    /**
     * @return the adapterGroup
     */
    public ConstitutionGroupCrmListGroupAdapter getAdapterGroup()
    {
        return adapterGroup;
    }

    /**
     * @param adapterGroup
     * the adapterGroup to set
     */
    public void setAdapterGroup( ConstitutionGroupCrmListGroupAdapter adapterGroup )
    {
        this.adapterGroup = adapterGroup;
    }

    /**
     * @return the adapterMoyen
     */
    public ConstitutionGroupCrmListAdapter getAdapterMoyen()
    {
        return adapterMoyen;
    }

    /**
     * @param adapterMoyen
     * the adapterMoyen to set
     */
    public void setAdapterMoyen( ConstitutionGroupCrmListAdapter adapterMoyen )
    {
        this.adapterMoyen = adapterMoyen;
    }

    /**
     * @return the mListMoyen
     */
    public List<IMoyen> getmListMoyen()
    {
        return mListMoyen;
    }

    /**
     * @param list
     * the mListMoyen to set
     */
    public void setmListMoyen( List<IMoyen> list )
    {
        this.mListMoyen = list;
    }

    /**
     * @return the intervention
     */
    public Intervention getIntervention()
    {
        return intervention;
    }

    /**
     * @param intervention
     * the intervention to set
     */
    public void setIntervention( Intervention intervention )
    {
        this.intervention = intervention;
    }

}// Class DemandeDeMoyensFragment
