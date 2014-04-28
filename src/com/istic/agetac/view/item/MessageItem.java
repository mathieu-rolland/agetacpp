package com.istic.agetac.view.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.activities.MessageActivity;
import com.istic.agetac.api.model.IMessage;
import com.istic.agetac.api.model.IUser;
import com.istic.agetac.api.view.ItemView;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controllers.messages.OnModifyMessage;
import com.istic.agetac.controllers.messages.OnValidateMessage;

public class MessageItem implements ItemView<IMessage> {

	private IMessage message;
	private MessageActivity activity;
	
	public MessageItem( IMessage message , MessageActivity activity )
	{
		this.message = message;
		this.activity = activity;
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
		
		//Ajout du listener sur le bouton de validation dans le cas du codis
		if( AgetacppApplication.getUser().getRole() == IUser.Role.codis ){
			Button validate = (Button) view.findViewById(R.id.fragment_messages_list_message_validate);
			validate.setOnClickListener( new OnValidateMessage(message) );
		}else{
			//Ajout du comportement pour activer la modification par le COS :
			Button modify = (Button) view.findViewById(R.id.item_message_list_button_message_modify);
			
			//Si le message est valide, le COS ne peut plus le modifier :
			if( message.isValidate() ){
				modify.setVisibility( View.GONE );
			}else{
				modify.setOnClickListener( new OnModifyMessage(message, activity) );
			}
		}
		return view;
	}

	@Override
	public int getLayout() {
		if( AgetacppApplication.getUser().getRole() == IUser.Role.intervenant ){
			return R.layout.item_message_view;
		}else{
			return R.layout.item_message_view_cos;
		}
	}

	@Override
	public long getId() {
		return 0;
	}

	@Override
	public IMessage getObject() {
		return message;
	}

	@Override
	public void setObject(IMessage object) {
		this.message = object;
	}
	
}
