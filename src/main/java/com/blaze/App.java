package com.blaze;

import java.util.ArrayList;
import java.util.List;

import com.blaze.actor.ActorContainer;
import com.blaze.actor.ActorRef;
import com.blaze.actor.CashierActor;
import com.blaze.actor.CustomerActor;
import com.blaze.model.Order;
import com.blaze.model.Product;
import com.blaze.util.Food;

public class App {

	public static void main(String[] args) {
		ActorContainer actorContainer = ActorContainer.getSystem();
		ActorRef customerActor = actorContainer.createActorRef(CustomerActor.class);
		ActorRef cashierActor = actorContainer.createActorRef(CashierActor.class);
		customerActor.ask("CREATE");
		customerActor.ask("CREATE");
		Product product = new Product();
		List<Product>listProduct = new ArrayList<>();
        product.setFood(Food.SODA);
        listProduct.add(product);
        product = new Product();
        product.setFood(Food.HAMBURGUER);
        listProduct.add(product);
        product = new Product();
        product.setFood(Food.FRIES);
        listProduct.add(product);
        Order order = new Order();
        order.setOrderId("00001");
        order.setProductList(listProduct);
        order.setTotal(100.00);
        cashierActor.ask(order);
	}
}
