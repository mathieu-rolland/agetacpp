package com.istic.agetac.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.istic.agetac.R;
import com.istic.agetac.api.model.IMessage;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.agetac.api.view.ItemView;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controler.adapter.ItemListAdapter;
import com.istic.agetac.controllers.messages.OnCancelMessage;
import com.istic.agetac.controllers.messages.OnNextMessagePart;
import com.istic.agetac.controllers.messages.OnPreviousMessagePart;
import com.istic.agetac.controllers.messages.OnSendMessage;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Message;
import com.istic.agetac.model.MessageWorkflow;
import com.istic.agetac.pattern.observer.Observer;
import com.istic.agetac.pattern.observer.Subject;
import com.istic.agetac.sync.message.MessageBroadcastReceiver;
import com.istic.agetac.sync.message.MessageServiceSynchronisation;
import com.istic.agetac.view.item.MessageItem;
import com.istic.sit.framework.application.FrameworkApplication;
import com.istic.sit.framework.sync.PoolSynchronisation;

public class MessageFragment extends Fragment implements Observer {

	private ItemListAdapter<IMessage> messageAdapter;
	private ListView messagesList;
	private IMessage.Message_part currentPart;
	
	private EditText message;
	private IMessage currentMessage;
	
	private MessageBroadcastReceiver receiver;
	private MessageServiceSynchronisation serviceSync;
	
	//Gestion des boutons :
	private Button buttonValidate;
	private Button buttonNext;
	private Button buttonCancel;
	private Button buttonPrevious;
	
	private boolean isWaitingForSave = false;
	
	private boolean isMessageModify;
	
	private Intervention intervention;
	
	public static Fragment newInstance() {
		MessageFragment fragment = new MessageFragment();
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/** Chargement du layout */
		View view = inflater.inflate(R.layout.fragment_messages_list, container, false);
		
		intervention = AgetacppApplication.getIntervention();
		
		messageAdapter = new ItemListAdapter<IMessage>(getActivity());
		messagesList = (ListView) view.findViewById(R.id.fragment_messages_list_list);
		messagesList.setAdapter(messageAdapter);
		
		message = (EditText) view.findViewById(R.id.fragment_messages_list_message_text);

		//R�cup�ration des boutons :
		buttonCancel = (Button) view.findViewById(R.id.fragment_messages_list_message_cancel);
		buttonNext = (Button) view.findViewById(R.id.fragment_messages_list_message_next);
		buttonValidate = (Button) view.findViewById(R.id.fragment_messages_list_message_validate);
		buttonPrevious = (Button) view.findViewById(R.id.fragment_messages_list_message_previous);
		
		//Add listeners :
		buttonCancel.setOnClickListener( new OnCancelMessage(this) );
		buttonNext.setOnClickListener(new OnNextMessagePart(this));
		buttonPrevious.setOnClickListener(new OnPreviousMessagePart(this));
		buttonValidate.setOnClickListener(new OnSendMessage(this));
		
		//Init message with new message :
		isMessageModify = false;
		initWithMessage( new Message( intervention ) );
		
		
		//Start sync :
		receiver = new MessageBroadcastReceiver(this);
		serviceSync = new MessageServiceSynchronisation("Service message sync");
		
		PoolSynchronisation synchro = FrameworkApplication.getPoolSynchronisation();
		synchro.registerServiceSync(MessageServiceSynchronisation.FILTER_MESSAGE_RECEIVER, serviceSync, receiver);
		
		return view;
	}
	
	public void message_next(View v){
		
		IMessage.Message_part newCurrent = MessageWorkflow.messageNext(currentPart);
		IMessage.Message_part newNext = MessageWorkflow.messageNext(newCurrent);
		IMessage.Message_part newPrevious = currentPart;

		String text;
		
		//On est pass� à l'�tape suivante :
		if( newCurrent != currentPart ){
			
			//Test que le text n'est pas vide :
			text = message.getText().toString();
			if( text.trim().equals("") ){
				Toast.makeText(getActivity().getApplicationContext(), "Vous devez saisir un message",
						Toast.LENGTH_LONG).show();
				return;
			}
			displayNewState(newPrevious, newCurrent, newNext);
			displayInWorkflow(newCurrent);
		}
		
		if( MessageWorkflow.isLastState(newCurrent) ){
			Toast.makeText(getActivity().getApplicationContext(), 
					"Aucune étape suivant " + 
				    MessageWorkflow.getWording(getActivity().getApplicationContext(), currentPart),
					Toast.LENGTH_LONG).show();
		}
	}
	
	private void displayNewState( IMessage.Message_part previous, IMessage.Message_part current, IMessage.Message_part next )
	{
		//save text before any changes :
		currentMessage.setText(currentPart, message.getText().toString());
		
		String wordingPreviousStep = MessageWorkflow.getWording(getActivity().getApplicationContext(), previous);
		String currentWording = MessageWorkflow.getWording(getActivity().getApplicationContext(), current);
		String wordingNext = MessageWorkflow.getWording(getActivity().getApplicationContext(), next);
		
		this.currentPart = current;
		
		this.buttonNext.setText( getString(R.string.fragment_message_list_button_next) + " ("+ wordingNext +")" );
		
		if( !MessageWorkflow.isFirst(currentPart) ){
			this.buttonPrevious.setEnabled(true);
			this.buttonPrevious.setText( getString(R.string.fragment_message_list_button_previous) + " ("+ wordingPreviousStep +")");
		}else{
			this.buttonPrevious.setEnabled(false);
			this.buttonPrevious.setText( getString(R.string.fragment_message_list_button_previous));
		}
		
		if( MessageWorkflow.isLastState(currentPart) ){
			this.buttonNext.setEnabled(false);
			this.buttonNext.setText( getString(R.string.fragment_message_list_no_next) );
			buttonValidate.setEnabled(true);
		}else{
			if( !currentMessage.isComplet() ){
				buttonValidate.setEnabled(false);
			}else{
				buttonValidate.setEnabled(true);
			}
			this.buttonNext.setEnabled(true);
		}
		
		message.setHint(currentWording);
		String text = currentMessage.getText(currentPart); 
		if( text != null ){
			message.setText(text);
		}else{
			message.setText("");
		}
	}
	
	public void message_previous( View v )
	{
		IMessage.Message_part newCurrent = MessageWorkflow.messagePrevious(currentPart);
		IMessage.Message_part newNext = currentPart;
		IMessage.Message_part newPrevious = MessageWorkflow.messagePrevious(newCurrent);

		hideInWorkflow(currentPart);
		displayNewState(newPrevious, newCurrent, newNext);
	}
	
	public void message_validate( View v )
	{
		Log.d("MESSAGE","Validate click");
		String text = message.getText().toString();
		if( !text.trim().equals("") ){
			currentMessage.setText( currentPart , text);
		}
		if( currentMessage.isComplet() ){
			currentMessage.registerObserver(this);
			isWaitingForSave = true;
			currentMessage.save();
			isWaitingForSave = false;
			if( !isMessageModify ){
				ItemView<IMessage> view = new MessageItem(currentMessage, this);
				messageAdapter.addLast(view);
				currentMessage.unregisterObserver(this);
				messagesList.setSelection( messageAdapter.getCount() );
			}
			isMessageModify = false;
			initWithMessage(new Message( AgetacppApplication.getIntervention() ));
		}
	}
	
	public void message_cancel( View v )
	{
		isMessageModify = false;
		initWithMessage(new Message( AgetacppApplication.getIntervention() ));
	}
	
	private void initWithMessage( IMessage message ){
		
		//R�initialisation de la vue :
		hideInWorkflow(IMessage.Message_part.JE_VOIS);
		hideInWorkflow(IMessage.Message_part.JE_PREVOIS);
		hideInWorkflow(IMessage.Message_part.JE_FAIS);
		hideInWorkflow(IMessage.Message_part.JE_DEMANDE);
		
		currentPart = MessageWorkflow.firstState();
		IMessage.Message_part previousPart = MessageWorkflow.messagePrevious(currentPart);
		IMessage.Message_part nextPart = MessageWorkflow.messageNext(currentPart);
		
		//Initialisation des valeurs stoqu�es :
		currentMessage = message;
		this.message.setText( message.getText(currentPart) );
		
		displayNewState( previousPart , currentPart , nextPart );
		displayInWorkflow( currentPart );
	}
	
	private void displayInWorkflow( IMessage.Message_part current )
	{
		TextView label = null;
		TextView background = null;
		if( current == IMessage.Message_part.JE_SUIS ){
			label = (TextView) getActivity().findViewById(R.id.fragment_message_list_label_je_suis);
			background = (TextView) getActivity().findViewById(R.id.fragment_message_list_background_je_suis);
		}
		if( current == IMessage.Message_part.JE_VOIS ){
			label = (TextView) getActivity().findViewById(R.id.fragment_message_list_label_je_vois);
			background = (TextView) getActivity().findViewById(R.id.fragment_message_list_background_je_vois);
		}
		if( current == IMessage.Message_part.JE_PREVOIS ){
			label = (TextView) getActivity().findViewById(R.id.fragment_message_list_label_je_prevois);
			background = (TextView) getActivity().findViewById(R.id.fragment_message_list_background_je_prevois);
		}
		if( current == IMessage.Message_part.JE_FAIS ){
			label = (TextView) getActivity().findViewById(R.id.fragment_message_list_label_je_fais);
			background = (TextView) getActivity().findViewById(R.id.fragment_message_list_background_je_fais);
		}
		if( current == IMessage.Message_part.JE_DEMANDE ){
			label = (TextView) getActivity().findViewById(R.id.fragment_message_list_label_je_demande);
			background = (TextView) getActivity().findViewById(R.id.fragment_message_list_background_je_demande);
		}
		if( label != null && background != null ){
			//label.setTextColor(Color.parseColor("#992f2f"));
			//background.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.button_style_blue));
			background.setBackgroundColor(Color.parseColor("#992f2f"));
		}
	}
	
	private void hideInWorkflow( IMessage.Message_part part ){
		
		TextView label = null;
		TextView background = null;
		if( part == IMessage.Message_part.JE_SUIS ){
			label = (TextView) getActivity().findViewById(R.id.fragment_message_list_label_je_suis);
			background = (TextView) getActivity().findViewById(R.id.fragment_message_list_background_je_suis);
		}
		if( part == IMessage.Message_part.JE_VOIS ){
			label = (TextView) getActivity().findViewById(R.id.fragment_message_list_label_je_vois);
			background = (TextView) getActivity().findViewById(R.id.fragment_message_list_background_je_vois);
		}
		if( part == IMessage.Message_part.JE_PREVOIS ){
			label = (TextView) getActivity().findViewById(R.id.fragment_message_list_label_je_prevois);
			background = (TextView) getActivity().findViewById(R.id.fragment_message_list_background_je_prevois);
		}
		if( part == IMessage.Message_part.JE_FAIS ){
			label = (TextView) getActivity().findViewById(R.id.fragment_message_list_label_je_fais);
			background = (TextView) getActivity().findViewById(R.id.fragment_message_list_background_je_fais);
		}
		if( part == IMessage.Message_part.JE_DEMANDE ){
			label = (TextView) getActivity().findViewById(R.id.fragment_message_list_label_je_demande);
			background = (TextView) getActivity().findViewById(R.id.fragment_message_list_background_je_demande);
		}
		if( label != null && background != null ){
			label.setTextColor(Color.parseColor("#efefef"));
			background.setBackgroundColor(Color.TRANSPARENT);
		}
	}
	
	public void update(List<IMessage> newMessageState)
	{
		Log.d("MESSAGE","Get into activity");
		
		//Attente de la reception du message;
		if( isWaitingForSave ) return;
		
		ArrayList<IMessage> waitingMessage = new ArrayList<IMessage>();
		
		for( IMessage serverMsg : newMessageState )
		{
			boolean found = false;
			//merge des deux listes :
			for( Object row : messageAdapter.getItems() ){
				@SuppressWarnings("unchecked")
				ItemView<IMessage> msgView = (ItemView<IMessage>) row;
				IMessage msg = msgView.getObject();
				if( msg.getId().equals( serverMsg.getId() )){
					found = true;
					if( !msg.isLock() ){
						msgView.setObject(serverMsg);
					}
					break;
				}
			}
			if( !found ){
				if( AgetacppApplication.getRole() == Role.codis ){
					if( serverMsg.isValidate()) waitingMessage.add(serverMsg);
				}else{
					waitingMessage.add(serverMsg);
				}
			}
		}
		
		//Ajout des message non trouv� :
		for( IMessage msg : waitingMessage ){
			ItemView<IMessage> view = new MessageItem(msg, this);
			messageAdapter.addLast(view);
		}

		//Refresh view :
		messageAdapter.notifyDataSetChanged();
	}
	
	private void stopSynchronisation(){
		AlarmManager alarm = (AlarmManager) getActivity().getSystemService( Context.ALARM_SERVICE );
		PendingIntent pi = receiver.getPendingIntent();
		alarm.cancel(pi);
	}
	
	@Override
	public void onStop() {
		stopSynchronisation();
		super.onStop();
	}

	@Override
	public void update(Subject subject) {
		IMessage msg = (IMessage) subject;
		if( msg.getId().trim().equals("") ){
			Toast.makeText(getActivity(), "Erreur d'enregistrement du message.", Toast.LENGTH_LONG).show();
			initWithMessage(currentMessage);
		}else{
			isWaitingForSave = false;
			if( !isMessageModify ){
				ItemView<IMessage> view = new MessageItem(currentMessage, this);
				messageAdapter.addLast(view);
				currentMessage.unregisterObserver(this);
				messagesList.setSelection( messageAdapter.getCount() );
			}
			isMessageModify = false;
			initWithMessage(new Message( AgetacppApplication.getIntervention() ));
		}
	}
	
	public void setCurrentMessage(final IMessage message){
		
		if( currentMessage.isLock()  ){
			Toast.makeText(getActivity(), 
					"Un message est actuellement en cours de sauvegarde. Veuillez patienter", 
					Toast.LENGTH_LONG).show();
			return;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.fragment_messages_list_alert_modification)
               .setPositiveButton(R.string.fragment_messages_list_alert_modification_yes,
            		   new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   initWithMessage( message );
                	   isMessageModify = true;
                   }
               })
               .setNegativeButton(R.string.fragment_messages_list_alert_modification_no, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                   }
               });
        builder.create();
        builder.show();
        return;
	}

	public void resetEditor(  ){
		isMessageModify = false;
	}
	
}
