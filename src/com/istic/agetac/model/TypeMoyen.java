package com.istic.agetac.model;

import com.istic.agetac.R;

public enum TypeMoyen {
	VSAV("VSAV", R.drawable.vsav, R.drawable.vsav);

	private String mType;
	private int drawableKo;
	private int drawableOk;

	TypeMoyen(String type, int drawableOk, int drawableko) {
		this.mType = type;
		this.drawableOk = drawableOk;
		this.drawableKo = drawableKo;
	}
	
	public String getType() {
		return this.mType;
	}

	@Override
	public String toString() {
		return mType;
	}
}
