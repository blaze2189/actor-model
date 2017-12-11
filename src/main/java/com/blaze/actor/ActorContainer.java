package com.blaze.actor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.blaze.actor.AbstractActor;

public class ActorContainer {

	private Logger log = Logger.getLogger(getClass());

	private Map<Class<? extends AbstractActor>, List<AbstractActor>> actors = new HashMap<>();

	private static ActorContainer actorContainer = new ActorContainer();

	public static ActorContainer getSystem() {
		return actorContainer;
	}

	private ActorContainer() {
	}

}
