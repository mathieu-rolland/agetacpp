package com.istic.agetac.controllers.listeners.secteurs;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

import com.istic.agetac.controler.adapter.SecteurAdapter;
import com.istic.agetac.model.Secteur;

public class ListenerDragSecteur implements OnItemLongClickListener {

	private SecteurAdapter secteurs;
	private Context context;
	
	public ListenerDragSecteur( SecteurAdapter adapter , Context context){
		this.secteurs = adapter;
		this.context = context;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {

		Secteur selectedSecteur = (Secteur) secteurs.getItem(position);

		if( selectedSecteur.getName().equals("SLL") ){
			Toast.makeText(context, "Le secteur SLL ne peut pas être supprimé."
					, Toast.LENGTH_LONG).show();
			return false;
		}
		
		DragShadowBuilder productShadow = new MyDragShadowBuilder(view, selectedSecteur);

		view.startDrag(null,	//ClipData
				productShadow,		//View.DragShadowBuilder 
				selectedSecteur,		//Object myLocalState
				0);	

		secteurs.remove(selectedSecteur);
		secteurs.notifyDataSetChanged();
		return true;
	}

	public class MyDragShadowBuilder extends View.DragShadowBuilder {

		private Secteur secteur;
		
		public MyDragShadowBuilder(View v, Secteur secteur) {
			super();
			this.secteur = secteur;
		}
		@Override
		public void onDrawShadow(Canvas canvas) {
			super.onDrawShadow(canvas);

			Paint externCircle = new Paint();
			externCircle.setAntiAlias(true);
			externCircle.setAlpha(55);
			int color = 0;
			try{
				color = Color.parseColor( secteur.getColor() );
			}catch(Exception e){
				color = Color.GRAY;
			}
			
			externCircle.setColor( color );
			externCircle.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.INNER));
			
			canvas.drawCircle(80, 80, 80, externCircle);

			Paint internCircle = new Paint();
			internCircle.setAntiAlias(true);
			internCircle.setAlpha(0);
			internCircle.setColor( Color.BLACK );
			internCircle.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.INNER));
			
			canvas.drawCircle(80, 80, 70, internCircle);
			
			Paint text = new Paint();
			text.setColor(Color.WHITE);
			text.setTextSize( 30 );
			
			String textDisplayed = secteur.getName();
			
			int xPosition = 50;
			
			if( textDisplayed.length() > 4 ){
				textDisplayed = textDisplayed.substring(0, 4) + "...";
				xPosition -= 10;
			}else{

				if( textDisplayed.length() == 4 ){
					xPosition -= 5;
				}
			}
			canvas.drawText( textDisplayed , xPosition, 85, text);
		}

		@Override
		public void onProvideShadowMetrics(Point shadowSize, Point touchPoint) {
			shadowSize.set(160,160);
		}
	}

}
