/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blaze.actor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
	List<Mailbox> mailBox = new ArrayList<>();

	protected ActorRef(Class<? extends AbstractActor> abstractActorClass) {
		this(abstractActorClass, 1);
	}

	protected ActorRef(Class<? extends AbstractActor> abstractACtorClass, Integer poolNumber) {
		this.abstractActorClass = abstractACtorClass;
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
//			log.mvn info(abstractActorClass.toString());
			// AbstractActor actor = abstractActorClass.newInstance();

			// AbstractActor actor = abstractActorClass.getConstructor().newInstance();
			//
			// Runnable task = () -> {
			// actor.receiveMessage(objectMessage);
			// };

			
			ExecutorService executor = Executors.newSingleThreadExecutor();

			

			mailBox.forEach(m->log.info("status "+abstractActorClass.getName()+" "+m.getActorState()));
			Mailbox task = mailBox.stream().filter(m -> m.getActorState() == ActorState.IDLE).findAny().orElse(null);

			if (task != null) {
				task.setMessage(objectMessage);
				Future<?> future = executor.submit(task);
				executor.shutdown();
				executor.awaitTermination(5, TimeUnit.SECONDS);
			}else {
				log.info("----------THERE IS NO "+abstractActorClass.getName()+" AVAILABLE----------");
			}
			// Object result = future.get(10,TimeUnit.SECONDS);
			// } catch
			// (InvocationTargetException|NoSuchMethodException|TimeoutException|ExecutionException|InterruptedException|InstantiationException
			// | IllegalAccessException ex) {
//		} catch (InvocationTargetException | NoSuchMethodException | InterruptedException | InstantiationException
//				| IllegalAccessException ex) {
//			log.error(ex);
		} catch (InterruptedException ex) {
			log.error(ex);
		}
	}

	@Setter
	@Getter
	private class Mailbox implements Runnable {

		private Object message;
		private ActorState actorState = ActorState.IDLE;

		@Override
		public void run() {
			AbstractActor actor;
			try {
				this.actorState = ActorState.ACTIVE;
				actor = abstractActorClass.getConstructor().newInstance();
				actor.receiveMessage(message);
				if(actor instanceof CashierActor)
				log.debug("A "+abstractActorClass.getSimpleName()+" HAS been FREEDOM+++++++++++++++++");
				this.actorState = ActorState.IDLE;
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				log.info(e);
			}
		}
	}

}
