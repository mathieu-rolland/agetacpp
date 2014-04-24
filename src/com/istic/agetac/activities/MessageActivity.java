package com.istic.agetac.activities;

import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.istic.agetac.sync.MessageBroadcastReceiver;
import com.istic.agetac.sync.MessageServiceSynchronisation;
import com.istic.agetac.view.item.MessageItem;
import com.istic.sit.framework.application.FrameworkApplication;
import com.istic.sit.framework.sync.PoolSynchronisation;

public class MessageActivity extends Activity {

	private ItemListAdapter messageAdapter;
	private IMessage.Message_part currentPart;
	
	private Button validate;
	private Button previous;
	private Button next;
	
	private EditText message;
	private IMessage currentMessage;
	
	private MessageBroadcastReceiver receiver;
	private MessageServiceSynchronisation serviceSync;
	
	private PendingIntent pendingIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_messages_list);
		
		currentPart = MessageWorkflow.firstState();
		currentMessage = new Message();
		
		messageAdapter = new ItemListAdapter(this);
		ListView messagesList = (ListView) findViewById(R.id.fragment_messages_list_list);
		messagesList.setAdapter(messageAdapter);
		
		message = (EditText) findViewById(R.id.fragment_messages_list_message_text);
		next = (Button) findViewById(R.id.fragment_messages_list_message_next);
		
		String currentWording = MessageWorkflow.getWording(getApplicationContext(), currentPart);
		message.setHint(currentWording);
		previous = (Button) findViewById(R.id.fragment_messages_list_message_previous);
		validate = (Button) findViewById(R.id.fragment_messages_list_message_validate);

		IMessage.Message_part current = currentPart;
		IMessage.Message_part next = MessageWorkflow.messageNext(currentPart);
		IMessage.Message_part previous = MessageWorkflow.messagePrevious(current);
		
		displayNewState(previous, current, next);
		displayInWorkflow(current);
		
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
			ItemView view = new MessageItem(currentMessage);
			messageAdapter.addLast(view);
			currentMessage.save();
			currentMessage = new Message();
		}
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
			label.setTextColor(Color.BLUE);
			background.setBackgroundColor(Color.BLUE);
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
			label.setTextColor(Color.BLACK);
			background.setBackgroundColor(Color.TRANSPARENT);
		}
	}
	
	public void update(List<IMessage> newMessageState)
	{
		Log.d("Service message sync","Receive data from synchronisation");
	}
	
	private void stopSynchronisation(){
		AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		PendingIntent pi = receiver.getPendingIntent();
		alarm.cancel(pi);
	}
	
	@Override
	public void finish() {
		stopSynchronisation();
		super.finish();
	}
	
}
