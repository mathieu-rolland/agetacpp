package com.istic.agetac.sync.moyen;

import java.util.List;

import com.istic.agetac.fragments.SitacFragment;
import com.istic.agetac.fragments.TableauMoyenFragment;
import com.istic.sit.framework.sync.ASynchornisationService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MoyenBroadcast extends BroadcastReceiver{

	private SitacFragment fragment;
	private TableauMoyenFragment fragmentTdm;
	
	public MoyenBroadcast( SitacFragment fragment ){
		super();
		this.fragment = fragment;
	}
	
	public MoyenBroadcast( TableauMoyenFragment tdmf ){
		this.fragmentTdm = tdmf;
	}
	
	@Override
	public void onReceive(Context arg0, Intent intent) {
		Log.d("Moyen","Receive");
		if( fragment != null ){
			fragment.updateEntities((List)intent.getExtras()
					.getParcelableArrayList( MoyenIntentService.CHANNEL ));
			fragment.updateGraphics((List) intent.getExtras().getParcelableArrayList(MoyenIntentService.CHANNEL_LIST));
		}else{
			fragmentTdm.updateTableauDesMoyen((List)intent.getExtras()
					.getParcelableArrayList( MoyenIntentService.CHANNEL ));
		}
	}

}
