package com.blaze;

import org.apache.log4j.Logger;

import com.blaze.actor.ActorContainer;
import com.blaze.actor.CashierActor;
import com.blaze.model.Product;
import com.blaze.util.Food;

/**
 * Hello world!
 *
 */
public class App {
	private static Logger log = Logger.getLogger(App.class);

	public static void main(String[] args) {
		// Product product = new Product();
		ActorContainer actorContainer = ActorContainer.getSystem();
		for (int i = 0; i < 8; i++) {
			log.info(i + 1);
			Product product = new Product();
			product.setFood(Food.HAMBURGUER);
			actorContainer.addToMailBox(CashierActor.class, product);
		}
	}
}
