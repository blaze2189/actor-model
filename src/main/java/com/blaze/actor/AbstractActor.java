package com.blaze.actor;

import java.util.ArrayDeque;
import java.util.Deque;

import com.blaze.util.ActorStates;

public abstract class AbstractActor implements Actor {

	protected ActorStates actorState = ActorStates.IDLE;
	protected static Deque<Object> mailBox;
	protected AbstractActor senderActor;
	protected static ActorContainer container = ActorContainer.getSystem();

	{
		mailBox = new ArrayDeque<>();
	}

	protected ActorStates getActorState() {
		return this.actorState;
	}

	protected abstract void receiveMessage(Object object);

	protected void answerMessage(Object object) {
		// TODO Auto-generated method stub
	}

	protected void changeState(ActorStates state) {
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
