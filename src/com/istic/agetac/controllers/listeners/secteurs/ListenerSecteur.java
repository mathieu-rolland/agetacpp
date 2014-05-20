package com.istic.agetac.controllers.listeners.secteurs;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.controler.adapter.SecteurAdapter;
import com.istic.agetac.model.Secteur;

public class ListenerSecteur implements OnItemLongClickListener {

	private SecteurAdapter secteurs;
	private Context context;
	
	public ListenerSecteur( SecteurAdapter adapter , Context context){
		this.secteurs = adapter;
		this.context = context;
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		
		Secteur selectedSecteur = (Secteur) secteurs.getItem(position);

		DragShadowBuilder productShadow = new MyDragShadowBuilder(view);
		
		view.startDrag(null,	//ClipData
                productShadow,		//View.DragShadowBuilder 
                selectedSecteur,		//Object myLocalState
                0);	
		
		secteurs.remove(selectedSecteur);
		secteurs.notifyDataSetChanged();
		return true;
	}

	public class MyDragShadowBuilder extends View.DragShadowBuilder {
		 
        public MyDragShadowBuilder(View v) {
            super(v);
 
        }
    @Override
    public void onDrawShadow(Canvas canvas) {
            super.onDrawShadow(canvas);
 
            int strokeWidth = 4;
 
            Paint circlePaint = new Paint();
            circlePaint.setAntiAlias(true);
            circlePaint.setAlpha(45);
            circlePaint.setColor(Color.GRAY);
            circlePaint.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.INNER));
            
            canvas.drawCircle(50, 50, 50, circlePaint);
             
        }
        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point touchPoint) {
            shadowSize.set(50,50);
        }
}
	
}
