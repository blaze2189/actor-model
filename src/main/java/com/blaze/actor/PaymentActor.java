package com.blaze.actor;

import org.apache.log4j.Logger;

import com.blaze.model.Order;

public class PaymentActor extends AbstractActor {

	private Logger log = super.log;
	
	private ActorContainer container = ActorContainer.getSystem();
	private ActorRef friesActor = container.createActorRef(FriesActor.class,10);
	private ActorRef hamburguerActor = container.createActorRef(HamburguerActor.class,this,50);
	private ActorRef inventoryActor = container.createActorRef(InventoryActor.class,this,3);
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
			Order order = (Order)object;
			log.info("processing order: "+order.getOrderId());
			shipperActor.ask(object);
			log.info(order.getOrderId()+" sent to shipper ");
			friesActor.ask(object);
			log.info(order.getOrderId()+" sent to fries");
			hamburguerActor.ask(object);
			log.info(order.getOrderId()+" sent to hamburguer");
			inventoryActor.ask(object);
			log.info(order.getOrderId()+" sent to inventory");
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
