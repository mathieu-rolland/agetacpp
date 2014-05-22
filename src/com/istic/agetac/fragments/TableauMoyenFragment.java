package com.istic.agetac.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.istic.agetac.R;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controler.adapter.AMoyenExpListAdapter;
import com.istic.agetac.controler.adapter.MoyenListExpCodisAdapter;
import com.istic.agetac.controler.adapter.MoyenListExpIntervenantAdapter;
import com.istic.agetac.controllers.dao.MoyensDao;
import com.istic.agetac.model.Groupe;
import com.istic.agetac.model.IMoyen;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.Secteur;
import com.istic.agetac.model.TypeMoyen;
import com.istic.agetac.sync.tableaumoyens.TableauDesMoyensReceiver;
import com.istic.agetac.sync.tableaumoyens.TableauDesMoyensSync;
import com.istic.sit.framework.couch.APersitantRecuperator;
import com.istic.sit.framework.couch.CouchDBUtils;
import com.istic.sit.framework.model.Representation;
import com.istic.sit.framework.sync.PoolSynchronisation;

public class TableauMoyenFragment extends Fragment
{

    public static TableauMoyenFragment newInstance( boolean isCreating )
    {
        TableauMoyenFragment fragment = new TableauMoyenFragment();
        fragment.setmIsCreating( isCreating );
        return fragment;
    }

    private List<IMoyen> mListMoyen;

    /* Instances des modï¿½les ï¿½ utiliser */
    private MoyensDao mMoyen; // Modï¿½le Moyen

    /* ï¿½lï¿½ments graphiques */
    private ExpandableListView mListViewMoyen; // ListView des moyens

    private AMoyenExpListAdapter mAdapterMoyens; // Adapter des moyens

    private TableauDesMoyensReceiver receiver;

    private boolean mIsCreating;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        View view = inflater.inflate( R.layout.fragment_tableau_moyen, container, false );

        mListViewMoyen = (ExpandableListView) view.findViewById( R.id.fragment_tableau_moyen_expandable_list );

        /* Rï¿½cupï¿½rations des donnï¿½es via les modï¿½les */

        if ( !mIsCreating )
        {
            // CouchDBUtils.getFromCouch( new MoyenRecuperator(
            // AgetacppApplication.getIntervention().getId() ) );
            CouchDBUtils.getFromCouch( new SectorRecuperator( AgetacppApplication.getIntervention().getId() ) );
        }

        if ( AgetacppApplication.getUser().getRole() == Role.codis )
        {
            mAdapterMoyens = new MoyenListExpCodisAdapter( getActivity(), mIsCreating );
        }
        else
        {
            mAdapterMoyens = new MoyenListExpIntervenantAdapter( getActivity() );
        }

        mListViewMoyen.setAdapter( mAdapterMoyens );

        if ( mListMoyen == null )
        {
            mListMoyen = new ArrayList<IMoyen>();
        }
        else
        {
            mAdapterMoyens.addAll( mListMoyen );
        }

        List<IMoyen> moyens = new ArrayList<IMoyen>();
        Moyen m = new Moyen( TypeMoyen.VSAV );
        m.setHDemande( new Date( 2014, 01, 01, 10, 00 ) );
        m.setRepresentationOK( new Representation( R.drawable.fpt_ok ) );
        m.setRepresentationKO( new Representation( R.drawable.fpt_ko ) );
        moyens.add( m );
        Moyen m2 = new Moyen( TypeMoyen.VSAV );
        m2.setHDemande( new Date( 2014, 02, 02, 10, 00 ) );
        m2.setHEngagement( new Date( 2014, 02, 02, 10, 30 ) );
        m2.setLibelle( "moyen2" );
        m2.setRepresentationOK( new Representation( R.drawable.fpt_ok ) );
        m2.setRepresentationKO( new Representation( R.drawable.fpt_ko ) );
        moyens.add( m2 );
        Moyen m3 = new Moyen( TypeMoyen.VSAV );
        m3.setHDemande( new Date( 2014, 02, 02, 14, 00 ) );
        m3.setHEngagement( new Date( 2014, 02, 02, 14, 30 ) );
        m3.setLibelle( "moyen2" );
        m3.setSecteur( "CRM" );
        m3.setHArrival( new Date( 2014, 02, 02, 15, 00 ) );
        m3.setRepresentationOK( new Representation( R.drawable.fpt_ok ) );
        m3.setRepresentationKO( new Representation( R.drawable.fpt_ko ) );
        moyens.add( m3 );

        Moyen m4 = new Moyen( TypeMoyen.VSAV );
        m4.setHDemande( new Date( 2014, 02, 02, 14, 00 ) );
        m4.setHEngagement( new Date( 2014, 02, 02, 14, 30 ) );
        m4.setLibelle( "moyen2" );
        m4.setSecteur( "SLL" );
        m4.setHArrival( new Date( 2014, 02, 02, 15, 00 ) );
        m4.setHFree( new Date() );
        m4.setRepresentationOK( new Representation( R.drawable.ic_fpt_alim ) );
        m4.setRepresentationKO( new Representation( R.drawable.ic_fpt_1_alim ) );
        moyens.add( m4 );

        Moyen m5 = new Moyen( TypeMoyen.VSAV );
        m5.setHDemande( new Date( 2014, 01, 01, 10, 00 ) );
        m5.setRepresentationOK( new Representation( R.drawable.fpt_ok ) );
        m5.setRepresentationKO( new Representation( R.drawable.fpt_ko ) );
        moyens.add( m5 );

        Moyen m6 = new Moyen( TypeMoyen.VSAV );
        m6.setHDemande( new Date( 2014, 02, 02, 10, 00 ) );
        m6.setHEngagement( new Date( 2014, 02, 02, 10, 30 ) );
        m6.setLibelle( "moyen6" );
        m6.setRepresentationOK( new Representation( R.drawable.fpt_ok ) );
        m6.setRepresentationKO( new Representation( R.drawable.fpt_ko ) );
        moyens.add( m6 );

        Moyen m7 = new Moyen( TypeMoyen.VSAV );
        m7.setHDemande( new Date( 2014, 02, 02, 10, 00 ) );
        m7.setHEngagement( new Date( 2014, 02, 02, 10, 30 ) );
        m7.setLibelle( "moyen7" );
        m7.setRepresentationOK( new Representation( R.drawable.fpt_ok ) );
        m7.setRepresentationKO( new Representation( R.drawable.fpt_ko ) );
        moyens.add( m7 );

        Groupe g = new Groupe( "Groupe 1" );
        g.addMoyen( m );
        g.addMoyen( m2 );

        Groupe g2 = new Groupe( "Groupe 2" );
        g2.addMoyen( m3 );
        g2.addMoyen( m4 );

        moyens.add( g );
        moyens.add( g2 );

        mAdapterMoyens.addAll( moyens );
        mListViewMoyen.setAdapter( mAdapterMoyens );
        mAdapterMoyens.notifyDataSetChanged();

        // CreationBase.createMoyen();

        return view;
    }

    /**
     * @return the mMoyen
     */
    public MoyensDao getmMoyen()
    {
        return mMoyen;
    }

    /**
     * @param mMoyen
     * the mMoyen to set
     */
    public void setmMoyen( MoyensDao mMoyen )
    {
        this.mMoyen = mMoyen;
    }

    public void updateTableauDesMoyen( List<Moyen> moyens )
    {
        // // TODO implï¿½menter la rï¿½ception de la synchro.
        // Log.d( "Synch", " Recieve sync for tableau des moyens : " + moyens ==
        // null ? "Moyen is null" : "Size : " + moyens.size() );
        // List<Moyen> moyensWaiting = new ArrayList<Moyen>();
        // for ( Moyen moyenServer : moyens )
        // {
        //
        // boolean found = false;
        // for ( Moyen localMoyen : mListMoyen )
        // {
        // if ( localMoyen.getId().equals( moyenServer.getId() ) )
        // {
        // found = true;
        // localMoyen = moyenServer;
        // break;
        // }
        // }
        // if ( !found )
        // {
        // moyensWaiting.add( moyenServer );
        // }
        // }
        // for ( Moyen moyen : moyensWaiting )
        // {
        // mListMoyen.add( moyen );
        // }
        // this.mAdapterMoyens.notifyDataSetChanged();
    }

    private void stopSynchronisation()
    {
        AlarmManager alarm = (AlarmManager) getActivity().getSystemService( Context.ALARM_SERVICE );
        PendingIntent pi = receiver.getPendingIntent();
        alarm.cancel( pi );
    }

    @Override
    public void onStop()
    {
        stopSynchronisation();
        super.onStop();
    }

    @Override
    public void onResume()
    {

        PoolSynchronisation pool = AgetacppApplication.getPoolSynchronisation();

        receiver = new TableauDesMoyensReceiver( this );
        TableauDesMoyensSync sync = new TableauDesMoyensSync();

        pool.registerServiceSync( TableauDesMoyensSync.FILTER_MESSAGE_RECEIVER, sync, receiver );
        super.onResume();
    }

    public boolean ismIsCreating()
    {
        return mIsCreating;
    }

    public void setmIsCreating( boolean mIsCreating )
    {
        this.mIsCreating = mIsCreating;
    }

    public class MoyenRecuperator extends APersitantRecuperator<Moyen>
    {

        public MoyenRecuperator( String idIntervention )
        {
            super( Moyen.class, "agetacpp", "get_moyens_by_intervention", idIntervention );
        }

        @Override
        public void onErrorResponse( VolleyError error )
        {
            Toast.makeText( getActivity(), "Impossible de rÃ©cupÃ©rer les moyens", Toast.LENGTH_SHORT ).show();
        }

        @Override
        public void onResponse( List<Moyen> moyens )
        {
            // mAdapterMoyens.addAll( moyens );
            // Log.e( "Vincent", "Moyen recu de la bdd" );
            // mAdapterMoyens.notifyDataSetChanged();
            // mListViewMoyen.setAdapter( mAdapterMoyens );
        }
    }

    public class SectorRecuperator extends APersitantRecuperator<Secteur>
    {

        public SectorRecuperator( String idIntervention )
        {
            super( Secteur.class );
        }

        @Override
        public void onErrorResponse( VolleyError error )
        {
            Toast.makeText( getActivity(), "Impossible de récupérer les moyens", Toast.LENGTH_SHORT ).show();
        }

        @Override
        public void onResponse( List<Secteur> secteurs )
        {
            mAdapterMoyens.setSectorAvailable( secteurs );
            mAdapterMoyens.notifyDataSetChanged();
        }
    }

}
