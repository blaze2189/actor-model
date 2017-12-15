/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blaze.actor;

import java.util.ArrayList;
import java.util.List;

import com.blaze.model.DeliverProduct;
import com.blaze.model.Order;
import com.blaze.model.Product;
import com.blaze.model.Shipper;
import com.blaze.ui.MainWindow;
import com.blaze.util.Status;

/**
 *
 * @author Jorge
 */
public class ShipperActor extends AbstractActor {

	ActorContainer container = ActorContainer.getSystem();
	ActorRef mainWindow = container.retrieveActorRef(MainWindow.class);

	@Override
	protected void receiveMessage(Object object) {
		if (object instanceof DeliverProduct) {
			DeliverProduct deliverProduct = (DeliverProduct) object;
			List<Product> productList = Shipper.mapOrder.get(deliverProduct.getOrderId());
			log.info(deliverProduct.getOrderId() + "\n" + productList);

			Boolean notDelivered = productList.stream().anyMatch(product -> product.getStatus() != Status.DELIVERED);
			if (notDelivered) {
				log.info("the " + deliverProduct.getProduct().getFood() + " for the order "
						+ deliverProduct.getOrderId());
			} else {
				log.info("**************************************************time to deliver order "
						+ deliverProduct.getOrderId() + "\n" + deliverProduct.getProduct());
				Shipper.mapOrder.remove(deliverProduct.getOrderId());
				emitMessage(deliverProduct);
			}
		} else if (object instanceof Order) {
			Order order = (Order) object;
			String orderId = order.getOrderId();
			log.info("added: \n" + orderId + order.getProductList());
			Shipper.mapOrder.put(orderId, order.getProductList());
			Shipper.mapDeliverOrder.put(orderId, new ArrayList<Product>());
		} else {
			log.info("Message not recognized " + object.getClass());
		}
	}

	@Override
	protected void receiveResponse(Object object) {
	}

}
