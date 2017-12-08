package com.blaze.propertychange.actor;

import com.blaze.model.Order;

public class CashierActor extends AbstractActor{

	public CashierActor() {
		addActor(new PaymentActor());
	}
	
	@Override
	protected void receiveMessage(Object object) {
		System.out.println("received message "+object.getClass());
		if(object instanceof Order) {
			sendMessage(object);
		}else {
			log.info("message not recognized");
		}
		
	}

	@Override
	protected void emitMessage(Object object) {

	}

}
