package com.blaze.actor;

import com.blaze.model.Order;

public class CashierActor extends AbstractActor{

	private ActorContainer container = ActorContainer.getSystem();
	private ActorRef paymentActor = container.createActorRef(PaymentActor.class);
	
	public CashierActor() {
//		addActor(new PaymentActor());
	}
	
	@Override
	protected void receiveMessage(Object object) {
		System.out.println("received message "+object.getClass());
		if(object instanceof Order) {
//			sendMessage(object);
			paymentActor.ask(object);
		}else {
			log.info("message not recognized");
		}
		
	}

//	@Override
//	protected void emitMessage(Object object) {
//
//	}

}
