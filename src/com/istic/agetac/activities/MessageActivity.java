package com.istic.agetac.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.istic.agetac.R;
import com.istic.agetac.api.model.IMessage;
import com.istic.agetac.api.view.ItemView;
import com.istic.agetac.controler.adapter.ItemListAdapter;
import com.istic.agetac.model.Message;
import com.istic.agetac.model.MessageWorkflow;
import com.istic.agetac.pattern.observer.Observer;
import com.istic.agetac.pattern.observer.Subject;
import com.istic.agetac.sync.MessageBroadcastReceiver;
import com.istic.agetac.sync.MessageServiceSynchronisation;
import com.istic.agetac.view.item.MessageItem;
import com.istic.sit.framework.application.FrameworkApplication;
import com.istic.sit.framework.sync.PoolSynchronisation;

public class MessageActivity extends Activity implements Observer {

	private ItemListAdapter messageAdapter;
	private IMessage.Message_part currentPart;
	
	private Button validate;
	private Button previous;
	private Button next;
	
	private EditText message;
	private IMessage currentMessage;
	
	private MessageBroadcastReceiver receiver;
	private MessageServiceSynchronisation serviceSync;
	
	private boolean isWaitingForSave = false;
	
	public static void launchActivity(Context context) {
		Intent intent = new Intent(context, MessageActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_messages_list);
		
//		currentPart = MessageWorkflow.firstState();
//		currentMessage = new Message();
		
		messageAdapter = new ItemListAdapter(this);
		ListView messagesList = (ListView) findViewById(R.id.fragment_messages_list_list);
		messagesList.setAdapter(messageAdapter);
		
		message = (EditText) findViewById(R.id.fragment_messages_list_message_text);
		next = (Button) findViewById(R.id.fragment_messages_list_message_next);
		
		String currentWording = MessageWorkflow.getWording(getApplicationContext(), currentPart);
		message.setHint(currentWording);
		previous = (Button) findViewById(R.id.fragment_messages_list_message_previous);
		validate = (Button) findViewById(R.id.fragment_messages_list_message_validate);

//		IMessage.Message_part current = currentPart;
//		IMessage.Message_part next = MessageWorkflow.messageNext(currentPart);
//		IMessage.Message_part previous = MessageWorkflow.messagePrevious(current);
//		
//		displayNewState(previous, current, next);
//		displayInWorkflow(current);
		initWithMessage( new Message() );
		
		//Start sync :
		receiver = new MessageBroadcastReceiver(this);
		serviceSync = new MessageServiceSynchronisation("Service message sync");
		
		PoolSynchronisation synchro = FrameworkApplication.getPoolSynchronisation();
		synchro.registerServiceSync(MessageServiceSynchronisation.FILTER_MESSAGE_RECEIVER, serviceSync, receiver);
	
	}
	
	public void message_next(View v){
		
		IMessage.Message_part newCurrent = MessageWorkflow.messageNext(currentPart);
		IMessage.Message_part newNext = MessageWorkflow.messageNext(newCurrent);
		IMessage.Message_part newPrevious = currentPart;

		String text;
		
		//On est passé à l'étape suivante :
		if( newCurrent != currentPart ){
			
			//Test que le text n'est pas vide :
			text = message.getText().toString();
			if( text.trim().equals("") ){
				Toast.makeText(getApplicationContext(), "Vous devez saisir un message",
						Toast.LENGTH_LONG).show();
				return;
			}
			displayNewState(newPrevious, newCurrent, newNext);
			displayInWorkflow(newCurrent);
		}
		
		if( MessageWorkflow.isLastState(newCurrent) ){
			Toast.makeText(getApplicationContext(), 
					"Aucune étape suivant " + 
				    MessageWorkflow.getWording(getApplicationContext(), currentPart),
					Toast.LENGTH_LONG).show();
		}
	}
	
	private void displayNewState( IMessage.Message_part previous, IMessage.Message_part current, IMessage.Message_part next )
	{
		//save text before any changes :
		currentMessage.setText(currentPart, message.getText().toString());
		
		String wordingPreviousStep = MessageWorkflow.getWording(getApplicationContext(), previous);
		String currentWording = MessageWorkflow.getWording(getApplicationContext(), current);
		String wordingNext = MessageWorkflow.getWording(getApplicationContext(), next);
		
		this.currentPart = current;
		
		this.next.setText( getString(R.string.fragment_message_list_button_next) + " ("+ wordingNext +")" );
		
		if( !MessageWorkflow.isFirst(currentPart) ){
			this.previous.setEnabled(true);
			this.previous.setText( getString(R.string.fragment_message_list_button_previous) + " ("+ wordingPreviousStep +")");
		}else{
			this.previous.setEnabled(false);
			this.previous.setText( getString(R.string.fragment_message_list_button_previous));
		}
		
		if( MessageWorkflow.isLastState(currentPart) ){
			this.next.setEnabled(false);
			validate.setEnabled(true);
		}else{
			this.next.setEnabled(true);
			validate.setEnabled(false);
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
		String text = message.getText().toString();
		if( !text.trim().equals("") ){
			currentMessage.setText( currentPart , text);
		}
		if( currentMessage.isComplet() ){
			currentMessage.registerObserver(this);
			isWaitingForSave = true;
			currentMessage.save();
		}
	}
	
	private void initWithMessage( IMessage message ){
		
		//Réinitialisation de la vue :
		hideInWorkflow(IMessage.Message_part.JE_VOIS);
		hideInWorkflow(IMessage.Message_part.JE_PREVOIS);
		hideInWorkflow(IMessage.Message_part.JE_FAIS);
		hideInWorkflow(IMessage.Message_part.JE_DEMANDE);
		
		currentPart = MessageWorkflow.firstState();
		IMessage.Message_part previousPart = MessageWorkflow.messagePrevious(currentPart);
		IMessage.Message_part nextPart = MessageWorkflow.messageNext(currentPart);
		
		//Initialisation des valeurs stoquées :
		currentMessage = message;
		this.message.setText( message.getText(currentPart) );
		
		displayNewState( previousPart , currentPart , nextPart );
		displayInWorkflow(currentPart);
	}
	
	private void displayInWorkflow( IMessage.Message_part current )
	{
		TextView label = null;
		TextView background = null;
		if( current == IMessage.Message_part.JE_SUIS ){
			label = (TextView) findViewById(R.id.fragment_message_list_label_je_suis);
			background = (TextView) findViewById(R.id.fragment_message_list_background_je_suis);
		}
		if( current == IMessage.Message_part.JE_VOIS ){
			label = (TextView) findViewById(R.id.fragment_message_list_label_je_vois);
			background = (TextView) findViewById(R.id.fragment_message_list_background_je_vois);
		}
		if( current == IMessage.Message_part.JE_PREVOIS ){
			label = (TextView) findViewById(R.id.fragment_message_list_label_je_prevois);
			background = (TextView) findViewById(R.id.fragment_message_list_background_je_prevois);
		}
		if( current == IMessage.Message_part.JE_FAIS ){
			label = (TextView) findViewById(R.id.fragment_message_list_label_je_fais);
			background = (TextView) findViewById(R.id.fragment_message_list_background_je_fais);
		}
		if( current == IMessage.Message_part.JE_DEMANDE ){
			label = (TextView) findViewById(R.id.fragment_message_list_label_je_demande);
			background = (TextView) findViewById(R.id.fragment_message_list_background_je_demande);
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
			label = (TextView) findViewById(R.id.fragment_message_list_label_je_suis);
			background = (TextView) findViewById(R.id.fragment_message_list_background_je_suis);
		}
		if( part == IMessage.Message_part.JE_VOIS ){
			label = (TextView) findViewById(R.id.fragment_message_list_label_je_vois);
			background = (TextView) findViewById(R.id.fragment_message_list_background_je_vois);
		}
		if( part == IMessage.Message_part.JE_PREVOIS ){
			label = (TextView) findViewById(R.id.fragment_message_list_label_je_prevois);
			background = (TextView) findViewById(R.id.fragment_message_list_background_je_prevois);
		}
		if( part == IMessage.Message_part.JE_FAIS ){
			label = (TextView) findViewById(R.id.fragment_message_list_label_je_fais);
			background = (TextView) findViewById(R.id.fragment_message_list_background_je_fais);
		}
		if( part == IMessage.Message_part.JE_DEMANDE ){
			label = (TextView) findViewById(R.id.fragment_message_list_label_je_demande);
			background = (TextView) findViewById(R.id.fragment_message_list_background_je_demande);
		}
		if( label != null && background != null ){
			label.setTextColor(Color.parseColor("#efefef"));
			background.setBackgroundColor(Color.TRANSPARENT);
		}
	}
	
	public void update(List<IMessage> newMessageState)
	{	
		//Attente de la reception du message;
		if( isWaitingForSave ) return;
		
		ArrayList<IMessage> waitingMessage = new ArrayList<IMessage>();
		
		for( IMessage serverMsg : newMessageState )
		{
			boolean found = false;
			//merge des deux listes :
			for( ItemView<IMessage> msgView : messageAdapter.getItems() ){
				IMessage msg = msgView.getObject();
//				Log.d("Compare" , msg.getId() + " == " + serverMsg.getId() 
//						+" ? " +msg.getId().equals( serverMsg.getId() ) );
				if( msg.getId().equals( serverMsg.getId() )){
					found = true;
					if( !msg.isLock() ){
						msgView.setObject(msg);
					}
					break;
				}
			}
			if( !found ){ waitingMessage.add(serverMsg); }
		}
		
		//Ajout des message non trouvé :
		for( IMessage msg : waitingMessage ){
			ItemView<IMessage> view = new MessageItem(msg, this);
			messageAdapter.addLast(view);
		}

		//Refresh view :
		messageAdapter.notifyDataSetChanged();
	}
	
	private void stopSynchronisation(){
		AlarmManager alarm = (AlarmManager) getSystemService( Context.ALARM_SERVICE );
		PendingIntent pi = receiver.getPendingIntent();
		alarm.cancel(pi);
	}
	
	@Override
	public void finish() {
		stopSynchronisation();
		super.finish();
	}

	@Override
	public void update(Subject subject) {
		IMessage msg = (IMessage) subject;
		if( msg.getId().trim().equals("") ){
			Toast.makeText(this, "Erreur d'enregistrement du message.", Toast.LENGTH_LONG).show();
			initWithMessage(currentMessage);
		}else{
			isWaitingForSave = false;
			ItemView<IMessage> view = new MessageItem(currentMessage, this);
			messageAdapter.addLast(view);
			currentMessage.unregisterObserver(this);
			initWithMessage(new Message());
		}
	}
	
	public void setCurrentMessage(final IMessage message){
		
		if( currentMessage.isLock()  ){
			Toast.makeText(this, "Un message est actuellement en cours de sauvegarde. Veuillez patienter"
					, Toast.LENGTH_LONG).show();
			return;
		}
		if( !currentMessage.isComplet() ){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setMessage(R.string.fragment_messages_list_alert_modification)
	               .setPositiveButton(R.string.fragment_messages_list_alert_modification_yes,
	            		   new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   initWithMessage( message );
	                   }
	               })
	               .setNegativeButton(R.string.fragment_messages_list_alert_modification_no, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                   }
	               });
	        builder.create();
	        return;
		}
		
	}
	
}
