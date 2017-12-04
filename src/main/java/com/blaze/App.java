package com.blaze;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		Runnable task = ()->{
			for (;;) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Product product = new Product();
				product.setFood(Food.HAMBURGUER);
				log.info("new message "+product.getFood());
				actorContainer.addToMailBox(CashierActor.class, product);
			}
		};
		executor.execute(task);
		
		
	}
}
