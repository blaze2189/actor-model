package com.blaze.actor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class OtherClass {

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	private String order;
	
	public void setOrder(String order) {
		String oldOrder=this.order;
		this.order=order;
		pcs.firePropertyChange("some",oldOrder,this.order);
	}
	
	public void addListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
}
