package com.blaze.actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.blaze.util.ActorStates;

public class ActorContainer {

	Logger log = Logger.getLogger(ActorContainer.class);

	private Map<Class<? extends AbstractActor>, List<AbstractActor>> actors = new HashMap<>();

	private static ActorContainer actorContainer =new ActorContainer();

	public static ActorContainer getSystem() {
//		if (actorContainer == null) {
//			new ActorContainer();
//		}
		return actorContainer;
	}

	private ActorContainer() {
	}

	{
		List<AbstractActor> actorList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			actorList.add(new InventoryActor());
		}
		actors.put(InventoryActor.class, actorList);

		actorList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			actorList.add(new CashierActor());
		}
		actors.put(CashierActor.class, actorList);

		actorList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			actorList.add(new KitchenActor());
		}
		actors.put(KitchenActor.class, actorList);
	}

	public List<AbstractActor> getActor(Class<? extends AbstractActor> clazz) {
		return actors.get(clazz);
	}

	public final void addToMailBox(Class<? extends AbstractActor> clazz, final Object message) {
		List<AbstractActor> actorList = actorContainer.getActor(clazz);
		if (actorList != null & actorList.size() > 0) {
			actorList.get(0).addToMailBox(message);
			AbstractActor idleActor = actorList.stream().filter(actor -> actor.getActorState() == ActorStates.IDLE)
					.findFirst().orElse(null);// .get();
			if (idleActor != null) {
				 idleActor.receiveMessage(message);
//				idleActor.addToMailBox(message);
				idleActor.changeState(ActorStates.ACTIVE);
			} else {
				log.info("not actor available");
			}

		}
	}

}