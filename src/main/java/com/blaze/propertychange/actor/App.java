package com.blaze.propertychange.actor;

import com.blaze.model.Product;

public class App {

	public static void main(String[] args) {
		AbstractActor initialActor = new AbstractActor() {
			
			@Override
			protected void receiveMessage(Object object) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected void emitMessage(Object object) {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		
		AbstractActor customer = new CustomerActor();
//		AbstractActor cashier = new CashierActor();
		
		initialActor.addActor(customer);
		initialActor.sendMessage("CREATE");
//		customer.addActor(cashier);
//		customer.sendMessage("CREATE");
		System.out.println("fin");
	}
	
}
