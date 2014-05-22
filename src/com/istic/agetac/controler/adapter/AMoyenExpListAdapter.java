package com.istic.agetac.controler.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.model.Groupe;
import com.istic.agetac.model.IMoyen;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.Secteur;
import com.istic.agetac.widget.SpinnerWithTextInit;

public abstract class AMoyenExpListAdapter extends BaseExpandableListAdapter
{
    protected Context mContext;

    protected HashMap<String, Secteur> mSector;

    protected List<String> mSectorString;

    protected LayoutInflater mInflater;

    protected List<IMoyen> mMoyens;

    protected static String mWaitingText = "En attente";

    public AMoyenExpListAdapter( Context context )
    {
        this.mContext = context;
        this.mMoyens = new ArrayList<IMoyen>();
        mSector = new HashMap<String, Secteur>();
        mSectorString = new ArrayList<String>();
        mInflater = LayoutInflater.from( context );
    }

    @Override
    public int getGroupCount()
    {
        return mMoyens.size();
    }

    @Override
    public int getChildrenCount( int groupPosition )
    {
        if ( mMoyens.get( groupPosition ).isGroup() )
        {
            return ( (Groupe) mMoyens.get( groupPosition ) ).getMoyens().size();
        }
        else
        {
            return 0;
        }
    }

    @Override
    public Object getGroup( int groupPosition )
    {
        return mMoyens.get( groupPosition );
    }

    @Override
    public Object getChild( int groupPosition, int childPosition )
    {
        if ( mMoyens.get( groupPosition ).isGroup() )
        {
            return ( (Groupe) mMoyens.get( groupPosition ) ).getMoyens().get( childPosition );
        }
        else
        {
            return null;
        }
    }

    @Override
    public long getGroupId( int groupPosition )
    {
        return groupPosition;
    }

    @Override
    public long getChildId( int groupPosition, int childPosition )
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    @Override
    public boolean isChildSelectable( int groupPosition, int childPosition )
    {
        return true;
    }

    protected class ViewHolder
    {
        ImageView logo;

        TextView hourDemand;

        Button buttonDemand;

        TextView hourEngage;

        TextView hourArrived;

        TextView hourFree;

        Button buttonFree;

        SpinnerWithTextInit spinner;

        TextView sector;

        TextView name;
    }

    public static boolean isNullOrBlank( String param )
    {
        if ( isNull( param ) || param.trim().length() == 0 || param.equals( mWaitingText ) )
        {
            return true;
        }
        return false;
    }

    public static boolean isNull( String str )
    {
        return str == null ? true : false;
    }

    public void setSectorAvailable( List<Secteur> sectors )
    {
        for ( Secteur secteur : sectors )
        {
            mSector.put( secteur.getName(), secteur );
        }

        mSectorString = new ArrayList<String>( mSector.keySet() );
    }

    @Override
    public View getGroupView( int groupPosition, boolean isExpanded, View convertView, ViewGroup parent )
    {
        if ( mMoyens.get( groupPosition ).isGroup() )
        {
            Groupe groupe = (Groupe) mMoyens.get( groupPosition );
            // if ( convertView == null )
            // {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = infalInflater.inflate( R.layout.item_group_moyen, null );
            // }

            TextView name = (TextView) convertView.findViewById( R.id.item_group_name );
            name.setText( groupe.getNom() );
        }
        else
        {
            
            convertView = getViewChildren( groupPosition, -1, false, convertView, parent );
        }

        return convertView;
    }

    protected abstract View getViewChildren( int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent );

    @Override
    public View getChildView( int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent )
    {
        return getChildVieww( groupPosition, childPosition, isLastChild, convertView, parent );
    }

    protected abstract View getChildVieww( int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent );

    public void addAll( List<IMoyen> mListMoyen )
    {
        List<Moyen> areInAGroup = new ArrayList<Moyen>();

        for ( IMoyen moyen : mListMoyen ) // ajout des groupes
        {
            if ( moyen.isGroup() )
            {
                if ( !mMoyens.contains( moyen ) )
                {
                    mMoyens.add( moyen );
                    areInAGroup.addAll( ( (Groupe) moyen ).getMoyens() );
                }
            }
        }

        for ( IMoyen moyen : mListMoyen ) // ajout des groupes
        {
            if ( !moyen.isGroup() && !areInAGroup.contains( moyen ) )
            {
                mMoyens.add( 0, moyen );
            }
        }

    }

}