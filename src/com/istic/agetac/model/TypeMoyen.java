package com.istic.agetac.model;

public enum TypeMoyen {
	VSAV("VSAV");

	private String mType;

	TypeMoyen(String type) {
		this.mType = type;
	}

	@Override
	public String toString() {
		return mType;
	}
}
