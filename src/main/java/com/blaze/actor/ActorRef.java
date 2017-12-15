/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blaze.actor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.blaze.util.ActorState;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Jorge
 */
public class ActorRef {

	private Logger log = Logger.getLogger(getClass());

	private Class<? extends AbstractActor> abstractActorClass;
	private Integer poolNumber;
	private AbstractActor dispatcher;
	List<Mailbox> mailBox = new ArrayList<>();

	protected ActorRef(Class<? extends AbstractActor> abstractActorClass) {
		this(abstractActorClass, null, 1);
	}

	protected ActorRef(Class<? extends AbstractActor> abstractActorClass, AbstractActor dispatcher) {
		this(abstractActorClass, dispatcher, 1);
	}

	protected ActorRef(Class<? extends AbstractActor> abstractActorClass, Integer poolNumber) {
		this(abstractActorClass, null, poolNumber);
	}

	protected ActorRef(Class<? extends AbstractActor> abstractACtorClass, AbstractActor dispatcher,
			Integer poolNumber) {
		this.abstractActorClass = abstractACtorClass;
		this.dispatcher = dispatcher;
		this.poolNumber = poolNumber;
		for (int i = 0; i < poolNumber; i++) {
			mailBox.add(new Mailbox());
		}
	}

	public Class<? extends AbstractActor> getAbstractActorClass() {
		return this.abstractActorClass;
	}

	public void ask(Object objectMessage) {
		try {
			ExecutorService executor = Executors.newSingleThreadExecutor();
			Mailbox task = mailBox.stream().filter(m -> m.getActorState() == ActorState.IDLE).findAny().orElse(null);
			Deque<Object> dequeMailBox = task.getMailboxDeque();
			((LinkedBlockingDeque) dequeMailBox).putLast(objectMessage);
			if (task != null) {
				Future<?> future = executor.submit(task);
				// future.get();
				executor.shutdown();
				executor.awaitTermination(1, TimeUnit.SECONDS);
			} else {
				log.info("----------THERE IS NO " + abstractActorClass.getName() + " AVAILABLE----------");
			}
		} catch (InterruptedException ex) {
			log.error(ex);
		}
	}

	@Setter
	@Getter
	private class Mailbox implements Runnable {

		private ActorState actorState = ActorState.IDLE;
		private Deque<Object> mailboxDeque = new LinkedBlockingDeque<>();
		private AbstractActor actor;

		@Override
		public void run() {

			try {
				this.actorState = ActorState.ACTIVE;
				actor = actor != null ? actor : abstractActorClass.getConstructor().newInstance();
				actor.setDispatcher(dispatcher);
				while (mailboxDeque.size() != 0) {
					log.info("actor instance: " + actor.getClass());
					if (actor instanceof FriesActor) {
						log.info("+-+-++-+-+-+-++-+-+-+-++-+-+-+-++-+-+-+-++-+-+-+-++-+-mailbox size "
								+ mailboxDeque.size());
					}
					Object message;
					try {
						message = ((LinkedBlockingDeque) mailboxDeque).takeFirst();
						actor.receiveMessage(message);
						if (actor instanceof FriesActor) {
							log.info("+-+-++-+-+-+-++-+-+-+-++-+-+-+-++-+-+-+-++-+-+-+-++-+-mailbox size "
									+ mailboxDeque.size());
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				this.actorState = ActorState.IDLE;
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				log.info(e);
			}catch(Exception e) {
				log.info("general exception e "+e);
			}
		}
	}

}
