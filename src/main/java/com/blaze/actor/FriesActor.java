package com.blaze.actor;

import java.util.Date;
import java.util.List;

import com.blaze.model.Order;
import com.blaze.model.Product;
import com.blaze.util.Food;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FriesActor extends AbstractActor{

	@Override
	protected void receiveMessage(Object object) {
            	if(object instanceof Order) {
			Order  order = (Order)object;
			List<Product> productList = order.getProductList();
			productList.forEach(product ->prepareFood(product));
		}else{
                    log.info("message not recognized");
                }
		
	}
	
	private void prepareFood(Product product) {
		Food food = product.getFood();
		switch(food){
		case FRIES:
			log.info("preparing "+food);
			long starting= new Date().getTime();
			long current = new Date().getTime();
			while(-starting+current<10000) {
				if((-starting+current)%1000==0) {
					log.info(-starting+current+"...");
				}
				current = new Date().getTime();	
			}
                        log.info(food + "ready");
			break;
			default:
				log.info(food +"not from this kitchen");
		}
		
	}

	@Override
	protected void emitMessage(Object object) {
		// TODO Auto-generated method stub
		
	}

}
