package com.istic.agetac.view.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.istic.agetac.api.model.IMessage;
import com.istic.agetac.api.view.ItemView;
import com.istic.agetac.R;

public class MessageItem implements ItemView {

	private IMessage message;
	
	public MessageItem( IMessage message )
	{
		this.message = message;
	}
	
	public View getView(Context context, View view, ViewGroup root){
		
		if( view == null ){
			view = LayoutInflater.from(context).inflate(getLayout(), null);
		}
		
		TextView txt = (TextView) view.findViewById(R.id.item_message_list_je_suis_message);
		txt.setText( message.getText(  IMessage.Message_part.JE_SUIS ) );
		
		txt = (TextView) view.findViewById(R.id.item_message_list_je_vois_message);
		txt.setText( message.getText(  IMessage.Message_part.JE_VOIS ) );
		
		txt = (TextView) view.findViewById(R.id.item_message_list_je_prevois_message);
		txt.setText( message.getText(  IMessage.Message_part.JE_PREVOIS ) );
		
		txt = (TextView) view.findViewById(R.id.item_message_list_je_fais_message);
		txt.setText( message.getText(  IMessage.Message_part.JE_FAIS ) );
		
		txt = (TextView) view.findViewById(R.id.item_message_list_je_demande_message);
		txt.setText( message.getText(  IMessage.Message_part.JE_DEMANDE ) );
		
		return view;
	}

	@Override
	public int getLayout() {
		return R.layout.item_message_view;
	}

	@Override
	public long getId() {
		return 0;
	}
	
}
