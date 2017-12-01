package com.blaze.actor;

import org.apache.log4j.Logger;

import com.blaze.model.Product;
import com.blaze.util.Food;

public class KitchenActor extends AbstractActor {

	private Logger log = Logger.getLogger(KitchenActor.class);

	public KitchenActor() {
		log.info("KitchenActor instance");
	}

	@Override
	protected void receiveMessage(Object object) {
		if (object instanceof Product) {
			Food food = ((Product) object).getFood();

			switch (food) {
			case HAMBURGUER:
				log.info("hamburguer will be prepared");
				break;
			case NUGGET:
				log.info("nuggets will be prepared");
				break;
			}

		}

	}

}
