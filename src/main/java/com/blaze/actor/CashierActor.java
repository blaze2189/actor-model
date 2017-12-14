package com.blaze.actor;

import com.blaze.model.Order;

public class CashierActor extends AbstractActor{

	private ActorContainer container = ActorContainer.getSystem();
	private ActorRef paymentActor = container.createActorRef(PaymentActor.class,this);
	
	public CashierActor() {
//		addActor(new PaymentActor());
	}
	
	@Override
	protected void receiveMessage(Object object) {
		if(object instanceof Order) {
			paymentActor.ask(object);
			emitMessage(object);
		}else {
			log.info("message not recognized");
		}		
	}

	@Override
	protected void receiveResponse(Object object) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	protected void emitMessage(Object object) {
//
//	}

}
