package com.blaze;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.blaze.actor.ActorContainer;
import com.blaze.actor.CashierActor;
import com.blaze.model.Order;
import com.blaze.model.Product;
import com.blaze.util.Food;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

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

        Runnable task = () -> {
            for (;;) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Food food = Food.FRIES;
                Double orderId = Math.random() * 100000;
                String orderIdString = String.valueOf(Math.round(orderId));
                List<Product> listProduct = new ArrayList<>();
                Long toatalProducts = Math.round(Math.random() * 10);
                for (int i = 0; i < toatalProducts; i++) {
                    Product product = new Product();
                    Double random = Math.random();
                    if (random < 0.25) {
                        food = Food.FRIES;
                    } else if (random >= 0.25 && random < 0.5) {
                        food = Food.HAMBURGUER;
                    } else if (random >= 0.5 && random < 0.75) {
                        food = Food.NUGGET;
                    } else if (random >= 0.75 && random < 1.0) {
                        food = Food.SODA;
                    }
                    product.setFood(food);
                    listProduct.add(product);
                }
                Order order = new Order();
                order.setOrderId(orderIdString);
                order.setProductList(listProduct);
                order.setTotal(100.00);
                log.info("new message " + order.getOrderId());
                actorContainer.addToMailBox(CashierActor.class, order);
            }
        };
        executor.execute(task);

    }
}
