package com.blaze.actor;

import org.apache.log4j.Logger;

import com.blaze.model.Order;

public class PaymentActor extends AbstractActor {

	private Logger log = super.log;

	public PaymentActor() {
		FriesActor fries = new FriesActor();
		HamburguerActor hamburguer = new HamburguerActor();
		InventoryActor inventory = new InventoryActor();
		addActor(fries);
		addActor(hamburguer);
		addActor(inventory);
	}

	@Override
	protected void receiveMessage(Object object) {
		log.info(object.getClass());
		if (object instanceof Order) {
			sendMessage(object);
		}else {
			log.info("not recognized");
		}

	}

	@Override
	protected void emitMessage(Object object) {
		// TODO Auto-generated method stub

	}

}
