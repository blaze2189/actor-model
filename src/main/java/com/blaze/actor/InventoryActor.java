package com.blaze.actor;

import com.blaze.model.Order;
import com.blaze.model.Product;
import java.util.List;
import org.apache.log4j.Logger;

public class InventoryActor extends AbstractActor {

    private Logger log = Logger.getLogger(InventoryActor.class);

    public InventoryActor() {
        log.info("new instance of " + this.getClass());
    }

    @Override
    protected void receiveMessage(Object message) {
        if (message instanceof Order) {
            List<Product> listProduct = ((Order) message).getProductList();
            listProduct.forEach(product -> {
                log.info("Reduce inventory for " + product.getFood());
            });
        } else {
            log.info("not recognized message");
        }

    }

    @Override
    protected void answerMessage(Object message) {
    }

}
