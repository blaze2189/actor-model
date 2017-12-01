package com.blaze.actor;

import org.apache.log4j.Logger;

import com.blaze.model.Product;

public class InventoryActor extends AbstractActor {

	private Logger log = Logger.getLogger(InventoryActor.class);

	public InventoryActor() {
		log.info("new instance of " + this.getClass());
	}

	@Override
	protected void receiveMessage(Object message) {
		if (message instanceof Product) {
			log.info("Reduce inventory");
		} else {
			log.info("not recognized message");
		}

	}

	@Override
	protected void answerMessage(Object message) {
	}

}
