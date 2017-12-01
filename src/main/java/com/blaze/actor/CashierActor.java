package com.blaze.actor;

import org.apache.log4j.Logger;

import com.blaze.model.Product;

public class CashierActor extends AbstractActor {

	private Logger log = Logger.getLogger(CashierActor.class);

	

	public CashierActor() {
		log.info("CashierActor instnace");
	}

	@Override
	protected void receiveMessage(Object object) {
		if (object instanceof Product) {
			ActorContainer container = ActorContainer.getSystem();
			log.info("sending message to InventoryActor");
			if (container != null) {
				container.addToMailBox(InventoryActor.class, object);
				log.info("sending message to KitchenActor");
				container.addToMailBox(KitchenActor.class, object);
			}else {
				log.info("container null");
			}
		}
	}

}
