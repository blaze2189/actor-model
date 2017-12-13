package com.blaze;

import com.blaze.actor.ActorContainer;
import com.blaze.actor.ActorRef;
import com.blaze.actor.CashierActor;
import com.blaze.ui.MainWindow;

public class App {

	public static void main(String[] args) {
		ActorContainer actorContainer = ActorContainer.getSystem();
//		ActorRef customerActor = actorContainer.createActorRef(CustomerActor.class,1);
////		ActorRef cashierActor = actorContainer.createActorRef(CashierActor.class,2);
//		actorContainer.addActorRefToMap(CashierActor.class, 2);
//		ActorRef cashierActor = actorContainer.retrieveActorRef(CashierActor.class);
//		customerActor.ask("CREATE");
//		customerActor.ask("CREATE");
//		Product product = new Product();
//		List<Product>listProduct = new ArrayList<>();
//        product.setFood(Food.SODA);
//        listProduct.add(product);
//        product = new Product();
//        product.setFood(Food.HAMBURGUER);
//        listProduct.add(product);
//        product = new Product();
//        product.setFood(Food.FRIES);
//        listProduct.add(product);
//        Order order = new Order();
//        order.setOrderId("00001");
//        order.setProductList(listProduct);
//        order.setTotal(100.00);
//        cashierActor.ask(order);
        
		actorContainer.addActorRefToMap(CashierActor.class, 2);
//        String message = "";
//        MainWindow.createAndShowGUI();
        
        ActorRef mainWindow = actorContainer.createActorRef(MainWindow.class);
        mainWindow.ask("START");
//        while(message!=null && !message.toUpperCase().equals("FINISH")) {
//        	message=javax.swing.JOptionPane.showInputDialog("Message");
//        	ActorRef customerActor = actorContainer.createActorRef(CustomerActor.class);
//        	customerActor.ask(message.toUpperCase());
//        }
        
        
	}
}
