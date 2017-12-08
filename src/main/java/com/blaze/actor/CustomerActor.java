package com.blaze.actor;

import java.util.ArrayList;
import java.util.List;

import com.blaze.model.Order;
import com.blaze.model.Product;
import com.blaze.util.Food;

public class CustomerActor extends AbstractActor{
	
	@Override
	protected void receiveMessage(Object object) {
		if(object instanceof String) {
			String message = String.valueOf(object);
			switch(message) {
			case "CREATE":
				
				Food food = Food.FRIES;
                Double orderId = Math.random() * 100000;
                String orderIdString = String.valueOf(Math.round(orderId));
                List<Product> listProduct = new ArrayList<>();
                Long totalProducts = Math.round(Math.random() * 10);
                for (int i = 0; i < totalProducts; i++) {
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
                super.log.info("new message " + order.getOrderId());
//                super.container.addToMailBox(CashierActor.class, order);
				
				break;
			}
		}else {
			super.log.info("Not recognized Message");
		}
		
	}

	@Override
	protected void emit(Object object) {
		// TODO Auto-generated method stub
		
	}

	
	
}
