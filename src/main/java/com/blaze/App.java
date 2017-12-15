package com.blaze;

import com.blaze.actor.ActorContainer;
import com.blaze.actor.ActorRef;
import com.blaze.ui.CashierUi;
import com.blaze.ui.MainWindow;

public class App {

	public static void main(String[] args) {
		ActorContainer actorContainer = ActorContainer.getSystem();
		ActorRef mainWindow = actorContainer.createOrRetrieve(MainWindow.class); // .createActorRef(MainWindow.class);
		ActorRef cashierView = actorContainer.createOrRetrieve(CashierUi.class); // .createActorRef(MainWindow.class);
		mainWindow.ask("START");
		cashierView.ask("START");
	}
}
