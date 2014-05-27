package com.istic.agetac.controler.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.controllers.listeners.constitutionGroupCrm.ListenerRemoveGroup;
import com.istic.agetac.controllers.listeners.constitutionGroupCrm.ListenerRemoveMoyen;
import com.istic.agetac.fragments.ConstitutionGroupCrmFragment;
import com.istic.agetac.model.Moyen;

/**
 * class ConstitutionGroupCrmListGroupAdapter :
 * 
 * @author Anthony LE MEE - 10003134
 */
public class ConstitutionGroupCrmListGroupAdapter extends ArrayAdapter<IMoyen>
{

    /** Attributs */
    private List<IMoyen> listItems; // Liste des moyens au CRM regroupé

    private ConstitutionGroupCrmFragment constitutionGroupCrm;

    /**
     * Constructeur de la classe ConstitutionGroupCrmListAdapter
     * 
     * @param f
     * - context
     * @param textViewResourceId
     * - ressource item
     * @param moyenListItem
     * - liste des item à afficher
     */
    public ConstitutionGroupCrmListGroupAdapter( ConstitutionGroupCrmFragment f, int textViewResourceId, List<IMoyen> moyenListItem )
    {

        super( f.getActivity().getApplicationContext(), textViewResourceId, moyenListItem );
        this.listItems = moyenListItem;
        this.constitutionGroupCrm = f;

    }// méthode

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {

        // ON récupère la vue affichée
        View v = convertView;

        // On récupére les infos que l'on souhaite afficher
        IMoyen itemMoyen = listItems.get( position );

        // Alors on la créer via le layout maquette_item_moyens
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        if ( itemMoyen != null && itemMoyen.isGroup() )
        {
            v = inflater.inflate( R.layout.item_constitution_groupe_crm_listview_group, null );
        }
        else
        {
            v = inflater.inflate( R.layout.item_constitution_groupe_crm_listview_moyen, null );
        }

        // Si on à bien récupèré les infos
        if ( itemMoyen != null )
        {

            if ( itemMoyen.isGroup() )
            {

                // Création de la vue pour un group
                TextView groupName = (TextView) v.findViewById( R.id.consitution_group_crm_item_listViewGroup_Group_TextView_nameMoyen );

                groupName.setText( itemMoyen.getLibelle() );
                // Ajout du listener demande de suppression au click
                Button delete = (Button) v.findViewById( R.id.consitution_group_crm_item_group_image_delete );
                delete.setOnClickListener( new ListenerRemoveGroup( itemMoyen, constitutionGroupCrm ) );

            }
            else
            {
                // Création de la vue pour un group
                TextView groupName = (TextView) v.findViewById( R.id.consitution_group_crm_item_listViewGroup_Moyen_TextView_nameMoyen );
                groupName.setText( ( (Moyen) itemMoyen ).getLibelle() );

                // Ajout du listener demande de suppression au click
                Button delete = (Button) v.findViewById( R.id.consitution_group_crm_item_moyen_image_delete );
                delete.setOnClickListener( new ListenerRemoveMoyen( itemMoyen, constitutionGroupCrm, getLastPreviousGroupe( itemMoyen )));
            }

        }// if

        return v;

    }

    private IMoyen getLastPreviousGroupe( IMoyen itemMoyen )
    {
        for ( IMoyen m : this.listItems )
        {
            if ( m.isGroup() )
            {
                for ( IMoyen moyenOfGroupe : m.getListMoyen() )
                {
                    if ( itemMoyen.getId() == moyenOfGroupe.getId() )
                    {
                        return m;
                    }
                }
            }
        }
        return null;
    }// méthode

}// classe
