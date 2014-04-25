package com.istic.agetac.api.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public interface ItemView<T> {

	public long getId();
	public View getView( Context context, View view, ViewGroup root);
	public int getLayout();
	public T getObject();
	public void setObject(T object);
}
