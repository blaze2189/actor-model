package com.blaze.actor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.blaze.actor.AbstractActor;

public class ActorContainer {

	private Logger log = Logger.getLogger(getClass());

	private Map<Class<? extends AbstractActor>, ActorRef> actors = new HashMap<>();

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

	public ActorRef retrieveActorRef(Class<? extends AbstractActor> clazz) {
		return actors.get(clazz);
	}

	public ActorRef createOrRetrieve(Class<? extends AbstractActor> clazz) {
		if (actors.get(clazz) == null) {
			actors.put(clazz, createActorRef(clazz));
		}
		return actors.get(clazz);
	}

	public ActorRef createOrRetrieve(Class<? extends AbstractActor> clazz,Integer poolNumber) {
		if (actors.get(clazz) == null) {
			actors.put(clazz, createActorRef(clazz,poolNumber));
		}
		return actors.get(clazz);
	}

	public void addActorRefToMap(Class<? extends AbstractActor> clazz,Integer poolNumber) {
		if(actors.get(clazz)==null) {
			actors.put(clazz,createActorRef(clazz, poolNumber));
		}
	}
	
	public void addActorRefToMap(Class<? extends AbstractActor> clazz) {
		addActorRefToMap(clazz,1);
	}

	private ActorContainer() {
	}

}
