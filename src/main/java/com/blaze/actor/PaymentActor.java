package com.blaze.actor;

import org.apache.log4j.Logger;

import com.blaze.model.Order;

public class PaymentActor extends AbstractActor {

	private Logger log = super.log;
	
	private ActorContainer container = ActorContainer.getSystem();
	private ActorRef friesActor = container.createActorRef(FriesActor.class);
	private ActorRef hamburguerActor = container.createActorRef(HamburguerActor.class,this);
	private ActorRef inventoryActor = container.createActorRef(InventoryActor.class,this);
	private ActorRef shipperActor = container.createActorRef(ShipperActor.class,this);

	public PaymentActor() {
//		FriesActor fries = new FriesActor();
//		HamburguerActor hamburger = new HamburguerActor();
//		InventoryActor inventory = new InventoryActor();
//		addActor(fries);
//		addActor(hamburger);
//		addActor(inventory);
	}

	@Override
	protected void receiveMessage(Object object) {
		log.info(object.getClass());
		if (object instanceof Order) {
			shipperActor.ask(object);
			friesActor.ask(object);
			hamburguerActor.ask(object);
			inventoryActor.ask(object);
		}else {
			log.info("not recognized");
		}

	}

	@Override
	protected void receiveResponse(Object object) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	protected void emitMessage(Object object) {
//		// TODO Auto-generated method stub
//
//	}

}
