package com.istic.agetac.controler.adapter;

import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.istic.agetac.R;
import com.istic.agetac.controllers.listeners.tableauMoyen.ListenerFree;
import com.istic.agetac.controllers.listeners.tableauMoyen.ListenerSpinner;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.Secteur;
import com.istic.agetac.widget.SpinnerWithTextInit;

public class MoyenListIntervenantAdapter extends AMoyenListAdapter
{
    /* Context */
    private Context context;

    public MoyenListIntervenantAdapter( Context context )
    {
        super( context );
        this.context = context;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        Moyen current;
        current = mList.get( position );
        ViewHolder holder;

        if ( convertView == null )
        {
            convertView = super.mInflater.inflate( R.layout.item_moyen, null );
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder = new ViewHolder();
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
            int positionSpinner = dataAdapter.getPosition( current.getSecteur() );

            if ( positionSpinner >= 0 )
            {
                if ( current.getSecteur() != "" )
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
                holder.hourDemand.setText(Moyen.FORMATER.format( current.getHDemande()) );
                if ( current.getHEngagement() == null )
                {
                    holder.hourEngage.setText( "En attente du codis" );
                    holder.hourEngage.setVisibility( View.VISIBLE );
                }
            }

            if ( current.getHEngagement() != null )
            {
                holder.hourEngage.setText( Moyen.FORMATER.format(current.getHEngagement()) );
                holder.hourEngage.setVisibility( View.VISIBLE );
                holder.name.setText( current.getLibelle() );
                holder.buttonDemand.setVisibility( View.GONE );
                holder.spinner.setVisibility( View.VISIBLE );
            }

            if ( current.getHArrival() != null )
            {
                holder.hourArrived.setText(Moyen.FORMATER.format( current.getHArrival() ));
                holder.hourArrived.setVisibility( View.VISIBLE );
                holder.buttonFree.setVisibility( View.VISIBLE );
            }

            if ( current.getHFree() != null )
            {
                holder.hourFree.setVisibility( View.VISIBLE );
                holder.buttonFree.setVisibility( View.GONE );
                holder.hourFree.setText( Moyen.FORMATER.format(current.getHFree()) );
                holder.spinner.setVisibility( View.INVISIBLE );
                holder.sector.setVisibility( View.VISIBLE );
                holder.sector.setText( current.getSecteur() );

                Secteur sector = mSector.get( current.getSecteur() );

                if ( sector != null )
                {
                    String color = mSector.get( current.getSecteur() ).getColor();
                    holder.sector.setTextColor( Color.parseColor( color ) );
                }
               
            }
        }

        // this.updateDatasSpinners(this.spinners);
        convertView.setTag( holder );

        return convertView;
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
            Toast.makeText( this.context, message, Toast.LENGTH_SHORT ).show();
        }
        catch ( Exception e )
        {
        }
    }

    public void setFree( Moyen mItemMoyen, Date date )
    {
        mItemMoyen.setHFree( date );
        this.notifyDataSetChanged();
    }
}
