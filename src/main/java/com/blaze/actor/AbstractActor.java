package com.blaze.actor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.blaze.util.ActorState;

public abstract class AbstractActor implements Actor {

	Logger log = Logger.getLogger(this.getClass());

	protected ActorState actorState = ActorState.IDLE;
	protected volatile static Deque<Object> mailBox;
	protected AbstractActor senderActor;
	protected static ActorContainer container;

	{
		mailBox = new ArrayDeque<>();
                container = ActorContainer.getSystem();		
		Runnable task = () ->{
			log.info("starting "+this.getClass());
			for(;;) {
//                            log.info("mailBox.size:  "+mailBox.size());
				if(mailBox.size()>0) {
					
					Object  message =mailBox.pollFirst();
					log.info("I have a message in mailBox ");
					receiveMessage(message);
				}else {
//					log.info("i dont have a message in mailbox");
				}
			}
			
		};
		
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(task);

	}

	protected ActorState getActorState() {
		return this.actorState;
	}

	protected abstract void receiveMessage(Object object);

	protected void answerMessage(Object object) {
		// TODO Auto-generated method stub
	}

	protected void changeState(ActorState state) {
		this.actorState = state;
	}

	protected void setSenderActor(AbstractActor senderActor) {
		this.senderActor = senderActor;
	};

	public void addToMailBox(Object message) {
		if (message != null) {
			mailBox.push(message);
		}
	}

}
