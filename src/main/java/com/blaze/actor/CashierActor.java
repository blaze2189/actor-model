package com.blaze.actor;

import org.apache.log4j.Logger;

import com.blaze.model.Product;

public class CashierActor extends AbstractActor {

    private Logger log = Logger.getLogger(CashierActor.class);

    public CashierActor() {
        log.info("CashierActor instance");
    }

    @Override
    protected void receiveMessage(Object object) {
        log.info("receive message in "+getClass()+" instance of "+object.getClass());
        if (object instanceof Product) {
            if (super.container != null) {
                log.info("sending message to InventoryActor " + object.getClass());
                super.container.addToMailBox(InventoryActor.class, object);
                log.info("sending message to KitchenActor");
                super.container.addToMailBox(KitchenActor.class, object);
            } else {
                log.info("container null");
            }
        }
    }

}
