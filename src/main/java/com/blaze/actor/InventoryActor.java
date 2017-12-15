package com.blaze.actor;

import com.blaze.model.Order;

public class InventoryActor extends AbstractActor {

	@Override
	protected void receiveMessage(Object object) {
		if (object instanceof Order) {
			log.info("reduce inventory");
		} else {
			log.info("Message not recognized " + object.getClass());
		}
	}

	@Override
	protected void receiveResponse(Object object) {
	}

}
