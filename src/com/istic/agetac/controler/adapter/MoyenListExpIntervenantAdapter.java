package com.istic.agetac.controler.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.istic.agetac.R;
import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.controllers.listeners.tableauMoyen.ListenerFree;
import com.istic.agetac.controllers.listeners.tableauMoyen.ListenerSpinner;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.Secteur;
import com.istic.agetac.widget.SpinnerWithTextInit;

public class MoyenListExpIntervenantAdapter extends AMoyenExpListAdapter
{

    public MoyenListExpIntervenantAdapter( Context context )
    {
        super( context, new ArrayList<IMoyen>() );
    }

    @Override
    protected View getChildVieww( int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent )
    {
        return getViewChildren( groupPosition, childPosition, isLastChild, convertView, parent );
    }

    /**
     * M�thode qui affiche un toast suite � la r�ception d'un message
     * 
     * @param message
     */
    public void onMessageReveive( String message )
    {
        try
        {
            Toast.makeText( this.mContext, message, Toast.LENGTH_SHORT ).show();
        }
        catch ( Exception e )
        {
        }
    }

    public void setFree( IMoyen mItemMoyen, Date date )
    {
        mItemMoyen.setHFree( date );
        if(mItemMoyen.isInGroup())
        {
            List<IMoyen> groupes = ((Moyen)mItemMoyen).getIntervention().getGroupes();
            int i = 0;
            IMoyen groupe = null;

            while(i<groupes.size() && groupe == null)
            {
                if(groupes.get( i ).getListMoyen().contains( mItemMoyen ))
                {
                    groupe = groupes.get( i );
                }                    
                    i++;
            }
            
            if(groupe != null)
            {
                boolean allFree = true;
                int y =0;
                while(allFree && y<groupe.getListMoyen().size())
                {
                    Date hourFree = groupe.getListMoyen().get( y ).getHFree();
                    allFree = !(hourFree==null || hourFree.equals( "" ));
                    y++;
                }             
                
                if(allFree)
                {
                    groupe.setHFree( new Date() );
                    Log.d( "Free", "Le groupe a tout compris ! " );
                }
                else
                {
                    Log.d( "Free", "Le groupe n'a PAS tout compris ! " );
                }
            }
            else
            {
                Log.e("MoyenListExpIntervenantAdapter", "BUG : le moyen ne trouve pas son groupe !");
            }
        }
        this.notifyDataSetChanged();
    }

    public View getViewChildren( int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent )
    {
        IMoyen current;

        if ( childPosition == -1 )
        {
            current = mMoyens.get( groupPosition );
        }
        else
        {
            current = mMoyens.get( groupPosition ).getListMoyen().get( childPosition );
        }

        convertView = super.mInflater.inflate( R.layout.item_moyen, null ); // pas
                                                                            // de
                                                                            // if(convertView==null)
                                                                            // car
                                                                            // ils
                                                                            // faut
                                                                            // reconstruire
                                                                            // la
                                                                            // vue
                                                                            // sinon
                                                                            // bug
                                                                            // ...
        ViewHolder holder = new ViewHolder();

        holder.logo = (ImageView) convertView.findViewById( R.id.list_moyen_logo );
        holder.hourDemand = (TextView) convertView.findViewById( R.id.list_moyen_hour_demand );
        holder.hourEngage = (TextView) convertView.findViewById( R.id.list_moyen_hour_engage );
        holder.hourArrived = (TextView) convertView.findViewById( R.id.list_moyen_hour_arrive );
        holder.hourFree = (TextView) convertView.findViewById( R.id.list_moyen_hour_free );
        holder.buttonFree = (Button) convertView.findViewById( R.id.list_moyen_button_free );
        holder.spinner = (SpinnerWithTextInit) convertView.findViewById( R.id.list_moyen_button_sector );
        holder.sector = (TextView) convertView.findViewById( R.id.list_moyen_sector );
        holder.buttonDemand = (Button) convertView.findViewById( R.id.list_moyen_button_engage );
        holder.name = (TextView) convertView.findViewById( R.id.list_moyen_name );

        holder.buttonFree.setVisibility( Button.GONE );
        holder.buttonDemand.setVisibility( Button.GONE );
        holder.name.setText( "" );

        if ( mSector.size() > 0 )
        {
            SpinnerSectorAdapter dataAdapter = new SpinnerSectorAdapter( convertView.getContext(), R.layout.item_secteur_tableau_moyen, mSector, super.mInflater );
            dataAdapter.setDropDownViewResource( R.layout.item_secteur_tableau_moyen );
            holder.spinner.setAdapter( dataAdapter );

            holder.spinner.setOnItemSelectedListener( new ListenerSpinner( this, dataAdapter, mSector, current ) );

            int positionSpinner = -1;
            if (current.getSecteur() != null )
            {
                positionSpinner = dataAdapter.getPosition( current.getSecteur().getLibelle() );
            }

            if ( positionSpinner >= 0 )
            {
                if ( current.getSecteur().getLibelle() != "" )
                {
                    holder.spinner.setSelection( positionSpinner );
                }
            }
        }

        if ( current != null )
        {
            holder.logo.setImageResource( current.getRepresentationOK().getDrawable() );
            holder.buttonFree.setOnClickListener( new ListenerFree( current, this ) );

            if ( current.getHDemande() != null )
            {
                holder.hourDemand.setText( Moyen.FORMATER.format( current.getHDemande() ) );
                if ( current.getHEngagement() == null )
                {
                    holder.hourEngage.setText( "En attente du codis" );
                    holder.hourEngage.setVisibility( View.VISIBLE );
                }
            }

            if ( current.getHEngagement() != null )
            {
                holder.hourEngage.setText( Moyen.FORMATER.format( current.getHEngagement() ) );
                holder.hourEngage.setVisibility( View.VISIBLE );
                holder.name.setText( current.getLibelle() );
                holder.buttonDemand.setVisibility( View.GONE );
                holder.spinner.setVisibility( View.VISIBLE );
            }

            if ( current.getHArrival() != null )
            {
                Log.e( "VINCENT", "Heure d'arrivee : " + current.getHArrival() );
                holder.hourArrived.setText( Moyen.FORMATER.format( current.getHArrival() ) );
                holder.hourArrived.setVisibility( View.VISIBLE );
                holder.buttonFree.setVisibility( View.VISIBLE );
            }

            if ( current.getHFree() != null )
            {
                holder.hourFree.setVisibility( View.VISIBLE );
                holder.buttonFree.setVisibility( View.GONE );
                holder.hourFree.setText( Moyen.FORMATER.format( current.getHFree() ) );
                holder.spinner.setVisibility( View.INVISIBLE );
                holder.sector.setVisibility( View.VISIBLE );
                holder.sector.setText( current.getSecteur().getLibelle() );

                Secteur sector = mSector.get( current.getSecteur() );

                if ( sector != null )
                {
                    int color = mSector.get( current.getSecteur() ).getColor();
                    holder.sector.setTextColor( color );
                }

            }
        }

        // this.updateDatasSpinners(this.spinners);
        convertView.setTag( holder );

        return convertView;
    }

}
