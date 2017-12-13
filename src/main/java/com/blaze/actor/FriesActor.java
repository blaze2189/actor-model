package com.blaze.actor;

import java.util.Date;
import java.util.List;

import com.blaze.model.DeliverProduct;
import com.blaze.model.Order;
import com.blaze.model.Product;
import com.blaze.util.Food;
import com.blaze.util.Status;

public class FriesActor extends AbstractActor {

	ActorContainer actorContainer = ActorContainer.getSystem();
//	ActorRef shipperActor = actorContainer.createActorRef(ShipperActor.class);
	ActorRef shipperActor = actorContainer.retrieveActorRef(ShipperActor.class,this);
	
	@Override
	protected void receiveMessage(Object object) {
		if (object instanceof Order) {
			Order order = (Order) object;
			List<Product> productList = order.getProductList();
			productList.forEach(product -> {
				prepareFood(product);
				DeliverProduct deliverProduct = new DeliverProduct();
				deliverProduct.setOrderId(order.getOrderId());
				deliverProduct.setProduct(product);
				shipperActor.ask(deliverProduct);
				});
		} else {
			log.info("message not recognized");
		}

	}

	private void prepareFood(Product product) {
		Food food = product.getFood();
		product.setStatus(Status.PREPARATION);
		switch (food) {
		case FRIES:
		case SODA:
			log.info("-------------------------------------preparing " + food);
			long starting = new Date().getTime();
			long current = new Date().getTime();
			while (-starting + current < 5000) {
				if ((-starting + current) % 1000 == 0) {
//					log.info(-starting + current + "...");
				}
				current = new Date().getTime();
			}
			log.info(food + " ready");
			product.setStatus(Status.DELIVERED);
//			shipperActor.ask(food);
			break;
		default:
			log.info(food + " not from this kitchen");
		}

	}

//	@Override
//	protected void emitMessage(Object object) {
//		// TODO Auto-generated method stub
//
//	}

}
