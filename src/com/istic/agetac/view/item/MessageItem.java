package com.istic.agetac.view.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.api.model.IMessage;
import com.istic.agetac.api.model.IUser;
import com.istic.agetac.api.view.ItemView;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controllers.messages.OnModifyMessage;
import com.istic.agetac.controllers.messages.OnValidateMessage;
import com.istic.agetac.fragments.MessageFragment;

public class MessageItem implements ItemView<IMessage> {

	private IMessage message;
	private MessageFragment activity;
	
	public MessageItem( IMessage message , MessageFragment activity )
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
		
		ImageView imgValidate = (ImageView) view.findViewById(R.id.item_message_list_image_validated);
		TextView labelValidate = (TextView) view.findViewById(R.id.item_message_list_label_validated);
		
		Button validateButton = (Button) view.findViewById(R.id.item_message_list_button_je_valide);
		Button modifyButton = (Button) view.findViewById(R.id.item_message_list_button_message_modify);
		
		if( AgetacppApplication.getUser().getRole() == IUser.Role.intervenant ){
			validateButton.setOnClickListener(new OnValidateMessage(message));
			modifyButton.setOnClickListener(new OnModifyMessage(message, activity));
		}
		
		//Si le message est valide, on ne peut pas le revalider :
		if( modifyButton != null 
				&& validateButton != null
				&& message.isValidate() ){
			modifyButton.setVisibility( View.GONE );
			validateButton.setVisibility( View.GONE );
		}else{
			imgValidate.setVisibility(View.GONE);
			modifyButton.setVisibility( View.VISIBLE );
			validateButton.setVisibility( View.VISIBLE );
			labelValidate.setText(R.string.fragment_messages_list_image_not_validate);
		}
		
		if( message.isValidate() ){
			imgValidate.setVisibility(View.VISIBLE);
			validateButton.setVisibility( View.GONE );
			labelValidate.setText(R.string.fragment_messages_list_image_validate);
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
