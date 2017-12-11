package com.blaze.actor;

public class InventoryActor extends AbstractActor{

	@Override
	protected void receiveMessage(Object object) {
		super.log.info("reduce inventory");
		
	}

	@Override
	protected void emitMessage(Object object) {
		// TODO Auto-generated method stub
		
	}

}
