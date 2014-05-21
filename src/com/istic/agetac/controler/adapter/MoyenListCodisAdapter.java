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
import com.istic.agetac.controllers.listeners.tableauMoyen.ListenerEngage;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.Secteur;
import com.istic.agetac.widget.SpinnerWithTextInit;

public class MoyenListCodisAdapter extends AMoyenListAdapter
{
    /* Context */
    private Context context;
    private boolean mIsCreating;

    public MoyenListCodisAdapter( Context context, boolean isCreating )
    {
        super( context );
        this.context = context;
        this.mIsCreating = isCreating;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        StateMoyen state=StateMoyen.demand; 
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
        holder.sector = (TextView) convertView.findViewById( R.id.list_moyen_sector );
        holder.spinner = (SpinnerWithTextInit) convertView.findViewById( R.id.list_moyen_button_sector );
        holder.buttonDemand = (Button) convertView.findViewById( R.id.list_moyen_button_engage );
        holder.name = (TextView) convertView.findViewById( R.id.list_moyen_name );

        holder.buttonFree.setVisibility( Button.GONE );
        holder.buttonDemand.setVisibility( Button.VISIBLE );
        holder.name.setText( "" );

        holder.logo.setImageResource( current.getRepresentationOK().getDrawable() );

        holder.spinner.setVisibility( View.INVISIBLE );

        if ( !AMoyenListAdapter.isNullOrBlank( current.getLibelle() ) )
        {
            holder.name.setText( current.getLibelle() );
        }

        if ( !AMoyenListAdapter.isNullOrBlank( current.getHDemande() ) )
        {
            holder.hourDemand.setText( current.getHDemande() );
            holder.hourDemand.setVisibility( TextView.VISIBLE );
            holder.buttonDemand.setOnClickListener( new ListenerEngage( current, this ) );
            state = StateMoyen.demand;
        }
        
        if ( !AMoyenListAdapter.isNullOrBlank( current.getHEngagement() ) )
        {
            holder.hourEngage.setText( current.getHEngagement() );
            holder.hourEngage.setVisibility( TextView.VISIBLE );
            holder.buttonDemand.setVisibility( Button.GONE );
            holder.name.setText( current.getLibelle() );
            state = StateMoyen.engage;
        }

        if ( !AMoyenListAdapter.isNullOrBlank( current.getHArrival() ) )
        {
            holder.hourArrived.setText( current.getHArrival() );
            state = StateMoyen.arrived;
        }

        if ( !AMoyenListAdapter.isNullOrBlank( current.getHFree() ) )
        {
            holder.hourFree.setText( current.getHFree() );
            holder.hourFree.setVisibility( View.VISIBLE );
            state = StateMoyen.free;
        }

        if ( !AMoyenListAdapter.isNullOrBlank( current.getSecteur() ) )
        {
            holder.sector.setVisibility( View.VISIBLE );
            holder.sector.setText( current.getSecteur() );
            holder.hourArrived.setText( current.getHArrival() );
            
            Secteur sector = mSector.get( current.getSecteur() );

            if ( sector != null )
            {
                String color = mSector.get( current.getSecteur() ).getColor();
                holder.sector.setTextColor( Color.parseColor( color ) );
            }
        }

        if ( mIsCreating )
        {
            holder.buttonDemand.setVisibility( Button.GONE );
        }
        
        switch ( state )
        {
            case engage:
                holder.sector.setVisibility( View.VISIBLE );
                holder.sector.setText( mWaitingText );
                break;
            case arrived:
                holder.hourFree.setVisibility( View.VISIBLE );
                holder.hourFree.setText( mWaitingText );
                break;
                
        default:
            break;
        }

        convertView.setTag( holder );

        return convertView;
    }

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

    public void setEngage( Moyen item, Date dateEngage )
    {
        item.setHEngagement( dateEngage );
        this.notifyDataSetChanged();
    }
    
    public enum StateMoyen
    {
        demand,
        engage,
        arrived,
        free
    }

}
