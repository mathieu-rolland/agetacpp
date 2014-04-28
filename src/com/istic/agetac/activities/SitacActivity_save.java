package com.istic.agetac.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.widget.GridView;

import com.istic.agetac.R;
import com.istic.sit.framework.api.model.IEntity;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.model.Entity;
import com.istic.sit.framework.model.Representation;
import com.istic.sit.framework.view.MainActivity;

public class SitacActivity_save extends MainActivity {

	public static void launchActivity(Context context) {
		Intent intent = new Intent(context, SitacActivity_save.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DataBaseCommunication.BASE_URL = "http://148.60.11.236:5984/sitac/";
		initializeBackground(TypeBackgroundEnum.Map, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		//loadEntities();
		//startServiceSynchronisation();
	}

	@Override
	public void onItemMenuClicked(int position, View view) {

	}

	@Override
	public void onCreateSlideMenu() {
		IEntity entityDynamic = new Entity();
		entityDynamic.setLibelle("Camion");
		entityDynamic
				.setRepresentation(new Representation(R.drawable.ic_launcher));

		IEntity entityVirtuel = new Entity();
		entityVirtuel.setLibelle("Bouche incendie");
		entityVirtuel.setRepresentation(new Representation(
				R.drawable.ic_launcher));

		IEntity entityStatic = new Entity();
		entityStatic.setLibelle("Agetac power");
		entityStatic.setRepresentation(new Representation(
				R.drawable.ic_launcher));

		this.addItemMenu(entityDynamic);
		this.addItemMenu(entityVirtuel);
		this.addItemMenu(entityStatic);
	}

	@Override
	public void onItemMenuLongClicked(int position, View view) {
		IEntity entity = (IEntity) this.entityAdapter.getItem(position);

		DragShadowBuilder entityShadow = new DragShadowBuilder(view);

		view.startDrag(null, // ClipData
				entityShadow, // View.DragShadowBuilder
				entity, // Object myLocalState
				0);

//		this.mDrawerLayout.closeDrawer(listMenu);
	}

	@Override
	public void onCreateMapMenu(GridView menu) {
		IEntity environment_water = new Entity();
		environment_water.setLibelle("Point d'eau");
		environment_water.setRepresentation(new Representation(R.drawable.environment_water));
		
		this.addItemEntityGridMenu(environment_water);
		this.addItemEntityGridMenu(environment_water);
		
//		EnvironmentButton environment_water = new EnvironmentButton("Point d'eau", view.getContext(), R.drawable.environment_water);
		
		
	}

}
