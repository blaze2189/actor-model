package com.blaze.actor;

import com.blaze.model.Order;
import com.blaze.model.Product;
import com.blaze.util.Food;
import org.apache.log4j.Logger;

import static com.blaze.util.Food.FRIES;
import static com.blaze.util.Food.HAMBURGUER;
import static com.blaze.util.Food.NUGGET;
import static com.blaze.util.Food.SODA;
import java.util.List;

public class KitchenActor extends AbstractActor {

   

    @Override
    protected void receiveMessage(Object object) {
        if (object instanceof Order) {
            Order order = (Order) object;
            List<Product> listProduct = order.getProductList();
            listProduct.forEach(product -> {
                Food food = product.getFood();
                switch (food) {
                    case HAMBURGUER:
                        log.info("hamburguer will be prepared");
                        break;
                    case NUGGET:
                        log.info("nuggets will be prepared");
                        break;
                    case FRIES:
                        log.info("fries will be prepared");
                        break;
                    case SODA:
                        log.info(food + " will be prepared");
                        break;
                }
            });
        } else {
            log.info("not recognized message");
        }

    }

	@Override
	protected void emit(Object object) {
		// TODO Auto-generated method stub
		
	}

}
