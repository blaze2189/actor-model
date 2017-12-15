package com.blaze.actor;

import com.blaze.model.Order;

public class CashierActor extends AbstractActor {

	private ActorContainer container = ActorContainer.getSystem();
	private ActorRef paymentActor = container.createActorRef(PaymentActor.class, this);

	@Override
	protected void receiveMessage(Object object) {
		if (object instanceof Order) {
			paymentActor.ask(object);
			emitMessage(object);
		} else {
			log.info("Message not recognized " + object.getClass());
		}
	}

	@Override
	protected void receiveResponse(Object object) {
	}

}
