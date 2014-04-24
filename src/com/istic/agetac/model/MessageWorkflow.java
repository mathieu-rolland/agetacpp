package com.istic.agetac.model;

import android.content.Context;

import com.istic.agetac.R;
import com.istic.agetac.api.model.IMessage;
import com.istic.agetac.api.model.IMessage.Message_part;

public class MessageWorkflow {
	
	public static Message_part messageNext( Message_part part ){
		switch (part) {
		case JE_SUIS:
			return Message_part.JE_VOIS;
		case JE_VOIS:
			return Message_part.JE_PREVOIS;
		case JE_PREVOIS:
			return Message_part.JE_FAIS;
		case JE_FAIS:
			return Message_part.JE_DEMANDE;
		default:
			return Message_part.JE_DEMANDE;
		}
	}
	
	public static Message_part messagePrevious( Message_part part ){
		switch (part) {
		case JE_VOIS:
			return Message_part.JE_SUIS;
		case JE_PREVOIS:
			return Message_part.JE_VOIS;
		case JE_FAIS:
			return Message_part.JE_PREVOIS;
		case JE_DEMANDE:
			return Message_part.JE_FAIS;
		default:
			return Message_part.JE_SUIS;
		}
	}
	
	public static Message_part firstState(){
		return Message_part.JE_SUIS;
	}
	
	public static Message_part lastState(){
		return Message_part.JE_DEMANDE;
	}
	
	public static boolean isFirst( Message_part part ){
		return part == firstState();
	}
	
	public static boolean isLastState( Message_part part )
	{
		return part == lastState();
	}
	
	public static String getWording(Context context, IMessage.Message_part part){
		if( part == IMessage.Message_part.JE_SUIS ) return context.getResources().getString( R.string.prefixes_rendre_compte_je_suis );
		if( part == IMessage.Message_part.JE_VOIS ) return context.getResources().getString( R.string.prefixes_rendre_compte_je_vois );
		if( part == IMessage.Message_part.JE_PREVOIS ) return context.getResources().getString( R.string.prefixes_rendre_compte_je_prevois );
		if( part == IMessage.Message_part.JE_FAIS ) return context.getResources().getString( R.string.prefixes_rendre_compte_je_fais );
		if( part == IMessage.Message_part.JE_DEMANDE ) return context.getResources().getString( R.string.prefixes_rendre_compte_je_demande );
		return null;
	}
}
