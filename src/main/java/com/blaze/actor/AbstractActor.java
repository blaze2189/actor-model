package com.blaze.actor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.blaze.util.ActorState;

public abstract class AbstractActor {

	protected Logger log = Logger.getLogger(getClass());

	protected ActorState actorState = ActorState.IDLE;
	protected AbstractActor dispatcher;
	protected Deque<Object> mailBox;

	protected AbstractActor senderActor;

	protected AbstractActor() {
	}

	protected abstract void receiveMessage(Object object);

	protected abstract void receiveResponse(Object object);

	protected void setDispatcher(AbstractActor dispatcher) {
		this.dispatcher = dispatcher;
	}

	// protected AbstractActor getDispatcher() {
	// return this.dispatcher;
	// }

	// public final void sendMessage(Object object) {
	// log.info("fire message");
	// // propertyChangeSupport.firePropertyChange("message", null, object);
	// log.info("kataplum");
	// }

	protected void emitMessage(Object object) {
		log.info("sending answer");
		if (dispatcher != null) {
			dispatcher.receiveResponse(object);
		} else {
			log.info("There is no Dispatcher");
		}
	}

	// private void addToMailBox(Object message) {
	// // mailBox.add(message);
	// // propertyChangeSupport.firePropertyChange("message", null, message);
	// }

	// private void addListener(PropertyChangeListener listener) {
	// propertyChangeSupport.addPropertyChangeListener(listener);
	// }

	// public PropertyChangeListener getUnknownName() {
	// return this.unknownName;
	// }

	// public void addActor(AbstractActor actor) {
	// addListener(actor.getUnknownName());
	// }

}
