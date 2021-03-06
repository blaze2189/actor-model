package com.blaze.actor;

import java.util.Date;
import java.util.List;

import com.blaze.model.DeliverProduct;
import com.blaze.model.Order;
import com.blaze.model.Product;
import com.blaze.util.Food;
import com.blaze.util.Status;

public class HamburguerActor extends AbstractActor {

	ActorContainer actorContainer = ActorContainer.getSystem();
	ActorRef shipperActor = actorContainer.retrieveActorRef(ShipperActor.class, this);

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
				if (product.getStatus() == Status.DELIVERED) {
					shipperActor.ask(deliverProduct);
				}
			});
		}

	}

	private void prepareFood(Product product) {
		Food food = product.getFood();
		switch (food) {
		case HAMBURGUER:
		case NUGGET:
			log.info("+++++++++++++++++++++++++++++++++++++++++++++preparing " + food);
			long starting = new Date().getTime();
			long current = new Date().getTime();
			while (-starting + current < 15000) {
				if ((-starting + current) % 1000 == 0) {
				}
				current = new Date().getTime();
			}
			log.info(food + " ready");
			product.setStatus(Status.DELIVERED);
			break;
		default:
			log.info(food + "not from this kitchen");
		}

	}

	@Override
	protected void receiveResponse(Object object) {
	}

}
