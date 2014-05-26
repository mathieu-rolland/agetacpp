package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.pattern.observer.Observer;
import com.istic.sit.framework.couch.IRecordable;
import com.istic.sit.framework.couch.JsonSerializer;

public class OCT implements IRecordable, Parcelable {

	static String codis = "CODIS";
	static String cos = "COS";
	private String secteur1;
	private String secteur2;
	private String secteur3;
	private String secteur4;
	
	private int frequenceCodis;
	private int frequenceCosAsc;
	private int frequenceCosDesc;
	private int frequenceS1Asc;
	private int frequenceS1Desc;
	private int frequenceS2Asc;
	private int frequenceS2Desc;
	private int frequenceS3Asc;
	private int frequenceS3Desc;
	private int frequenceS4Asc;
	private int frequenceS4Desc;
	
	private String _id;
	private transient Intervention intervention;
	private transient List<Observer> observers;
	
	public OCT(String s1, String s2, String s3, String s4, int freqCodis, int freqCosA, int freqCosD,
			int freqS1A, int freqS1D,int freqS2A, int freqS2D, int freqS3A, int freqS3D, int freqS4A, int freqS4D){
		this._id = UUID.randomUUID().toString();
		
		this.secteur1 = s1;
		this.secteur2 = s2;
		this.secteur3 = s3;
		this.secteur4 = s4;
		
		this.frequenceCodis = freqCodis;
		this.frequenceCosAsc = freqCosA;
		this.frequenceCosDesc = freqCosD;
		this.frequenceS1Asc = freqS1A;
		this.frequenceS1Desc = freqS1D;
		this.frequenceS2Asc = freqS2A;
		this.frequenceS2Desc = freqS2D;
		this.frequenceS3Asc = freqS3A;
		this.frequenceS3Desc = freqS3D;
		this.frequenceS4Asc = freqS4A;
		this.frequenceS4Desc = freqS4D;
	}

	public OCT(){
		this._id = UUID.randomUUID().toString();
	}
	
	
	public OCT(Parcel source) {
		String serializedJson = source.readString();
		observers = new ArrayList<Observer>();
		this._id = UUID.randomUUID().toString();
		try {
			OCT oct = (OCT) JsonSerializer.deserialize(OCT.class, new JSONObject(serializedJson));
		
			this.intervention = oct.intervention;
			this.secteur1 = oct.secteur1;
			this.secteur2 = oct.secteur2;
			this.secteur3 = oct.secteur3;
			this.secteur4 = oct.secteur4;
			
			this.frequenceCodis = oct.frequenceCodis;
			this.frequenceCosAsc = oct.frequenceCosAsc;
			this.frequenceCosDesc = oct.frequenceCosDesc;
			this.frequenceS1Asc = oct.frequenceS1Asc;
			this.frequenceS1Desc = oct.frequenceS1Desc;
			this.frequenceS2Asc = oct.frequenceS2Asc;
			this.frequenceS2Desc = oct.frequenceS2Desc;
			this.frequenceS3Asc = oct.frequenceS3Asc;
			this.frequenceS3Desc = oct.frequenceS3Desc;
			this.frequenceS4Asc = oct.frequenceS4Asc;
			this.frequenceS4Desc = oct.frequenceS4Desc;
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @return the intervention
	 */
	public Intervention getIntervention() {
		return intervention;
	}

	/**
	 * @param intervention the intervention to set
	 */
	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	public static String getCodis() {
		return codis;
	}

	public static void setCodis(String codis) {
		OCT.codis = codis;
	}

	public static String getCos() {
		return cos;
	}

	public static void setCos(String cos) {
		OCT.cos = cos;
	}

	public String getSecteur1() {
		return secteur1;
	}

	public void setSecteur1(String secteur1) {
		this.secteur1 = secteur1;
	}

	public String getSecteur2() {
		return secteur2;
	}

	public void setSecteur2(String secteur2) {
		this.secteur2 = secteur2;
	}

	public String getSecteur3() {
		return secteur3;
	}

	public void setSecteur3(String secteur3) {
		this.secteur3 = secteur3;
	}

	public String getSecteur4() {
		return secteur4;
	}

	public void setSecteur4(String secteur4) {
		this.secteur4 = secteur4;
	}

	public int getFrequenceCodis() {
		return frequenceCodis;
	}

	public void setFrequenceCodis(int frequenceCodis) {
		this.frequenceCodis = frequenceCodis;
	}

	public int getFrequenceCosAsc() {
		return frequenceCosAsc;
	}

	public void setFrequenceCosAsc(int frequenceCosAsc) {
		this.frequenceCosAsc = frequenceCosAsc;
	}

	public int getFrequenceCosDesc() {
		return frequenceCosDesc;
	}

	public void setFrequenceCosDesc(int frequenceCosDesc) {
		this.frequenceCosDesc = frequenceCosDesc;
	}

	public int getFrequenceS1Asc() {
		return frequenceS1Asc;
	}

	public void setFrequenceS1Asc(int frequenceS1Asc) {
		this.frequenceS1Asc = frequenceS1Asc;
	}

	public int getFrequenceS1Desc() {
		return frequenceS1Desc;
	}

	public void setFrequenceS1Desc(int frequenceS1Desc) {
		this.frequenceS1Desc = frequenceS1Desc;
	}

	public int getFrequenceS2Asc() {
		return frequenceS2Asc;
	}

	public void setFrequenceS2Asc(int frequenceS2Asc) {
		this.frequenceS2Asc = frequenceS2Asc;
	}

	public int getFrequenceS2Desc() {
		return frequenceS2Desc;
	}

	public void setFrequenceS2Desc(int frequenceS2Desc) {
		this.frequenceS2Desc = frequenceS2Desc;
	}

	public int getFrequenceS3Asc() {
		return frequenceS3Asc;
	}

	public void setFrequenceS3Asc(int frequenceS3Asc) {
		this.frequenceS3Asc = frequenceS3Asc;
	}

	public int getFrequenceS3Desc() {
		return frequenceS3Desc;
	}

	public void setFrequenceS3Desc(int frequenceS3Desc) {
		this.frequenceS3Desc = frequenceS3Desc;
	}

	public int getFrequenceS4Asc() {
		return frequenceS4Asc;
	}

	public void setFrequenceS4Asc(int frequenceS4Asc) {
		this.frequenceS4Asc = frequenceS4Asc;
	}

	public int getFrequenceS4Desc() {
		return frequenceS4Desc;
	}

	public void setFrequenceS4Desc(int frequenceS4Desc) {
		this.frequenceS4Desc = frequenceS4Desc;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String oct = gson.toJson(this).toString();
		dest.writeString(oct);
		
	}

	@Override
	public String getId() {
		return this._id;
	}

	@Override
	public void setId(String id) {
		this._id = id;
		
	}

	@Override
	public void save() {
		AgetacppApplication.getIntervention().save();
		
	}

	@Override
	public void delete() {
		AgetacppApplication.getIntervention().setOct(null);
		AgetacppApplication.getIntervention().save();
	}
	
}
