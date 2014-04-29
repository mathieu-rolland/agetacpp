package com.istic.agetac.sync.tableaumoyens;

import java.util.List;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.istic.agetac.fragments.TableauMoyenFragment;
import com.istic.sit.framework.sync.ASyncReceiver;
import com.istic.sit.framework.sync.ASynchornisationService;

public class TableauDesMoyensReceiver extends ASyncReceiver{

	private TableauMoyenFragment fragment;
	
	public TableauDesMoyensReceiver( TableauMoyenFragment fragment ){
		this.fragment = fragment;
	}
	
	@Override
	public void setPendingIntent(PendingIntent peding) {
		super.pendingIntent = peding;
	}

	@Override
	public void onReceive(Context arg0, Intent intent) {
		fragment.updateTableauDesMoyen((List)intent.getExtras()
				.getParcelableArrayList(ASynchornisationService.SYNC_SERVICE_EXTRA));
	}
	
}
