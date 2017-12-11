package com.blaze.actor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.blaze.util.ActorState;

public abstract class AbstractActor {

	protected Logger log = Logger.getLogger(getClass());
	
	protected ActorState actorState = ActorState.IDLE;
	protected Deque<Object> mailBox;
	protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	protected PropertyChangeListener unknownName = new PropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			Object message = event.getNewValue();
			mailBox.push(message);
			Runnable r = ()->{
				receiveMessage(message);
			};
			ExecutorService e = Executors.newFixedThreadPool(1);
			e.execute(r);
		}
		
	};
	protected AbstractActor senderActor;
	
	protected AbstractActor() {
		mailBox = new ArrayDeque<>();
		log.info("new instance "+getClass());
	}
	
	protected ActorState getActorState() {
		return this.actorState;
	}
	
	protected abstract void receiveMessage(Object object);
	
	public final void sendMessage(Object object) {
		log.info("fire message");
		propertyChangeSupport.firePropertyChange("message", null, object);
		log.info("kataplum");
	}
	
	protected abstract void emitMessage(Object object);
	
	private void addToMailBox(Object message) {
//		mailBox.add(message);
//		propertyChangeSupport.firePropertyChange("message", null, message);
	}
	
	private void addListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public PropertyChangeListener getUnknownName() {
		return this.unknownName;
	}
	
	public void addActor(AbstractActor actor) {
		addListener(actor.getUnknownName());
	}
	
}
