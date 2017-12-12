package com.blaze.actor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.blaze.actor.AbstractActor;

public class ActorContainer {

	private Logger log = Logger.getLogger(getClass());

	private Map<Class<? extends AbstractActor>, List<ActorRef>> actors = new HashMap<>();

	private static ActorContainer actorContainer = new ActorContainer();

	public static ActorContainer getSystem() {
		return actorContainer;
	}

	public ActorRef createActorRef(Class<? extends AbstractActor> clazz) {
		return new ActorRef(clazz);
	}

	public ActorRef createActorRef(Class<? extends AbstractActor> clazz, Integer poolNumber) {
		return new ActorRef(clazz, poolNumber);
	}

	public List<ActorRef> retrieveActorRef(Class<? extends AbstractActor> clazz) {
		return actors.get(clazz);
	}

	private ActorContainer() {
	}

	
	
}
