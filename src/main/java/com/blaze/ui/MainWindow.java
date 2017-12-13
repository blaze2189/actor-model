package com.blaze.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.blaze.actor.AbstractActor;
import com.blaze.actor.ActorContainer;
import com.blaze.actor.ActorRef;
import com.blaze.actor.CashierActor;
import com.blaze.actor.CustomerActor;
import com.blaze.actor.ShipperActor;
import com.blaze.model.DeliverProduct;
import com.blaze.model.Order;
import com.blaze.model.Product;

public class MainWindow extends AbstractActor {

	private ActorContainer container = ActorContainer.getSystem();
	private ActorRef customerActor = container.createActorRef(CustomerActor.class, this);

	{
		container.addActorRefToMap(ShipperActor.class, this, 5);
		container.addActorRefToMap(CashierActor.class, 2);
	}

	private JTable table;

	@Override
	protected void receiveMessage(Object object) {
		log.info("message received " + object.getClass());
		if (object instanceof String) {
			String message = String.valueOf(object);
			switch (message) {
			case "START":
				createAndShowGUI();
				break;
			default:
				log.info("message not found");
			}
		} else if (object instanceof Order) {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			Order order = (Order) object;
			List<Product> listProduct = order.getProductList();
			StringBuffer products = new StringBuffer(25);
			listProduct.forEach(product -> products.append("[").append(product.getFood()).append("]"));
			String[] data = new String[] { order.getOrderId(), products.toString() };
			model.addRow(data);
		} else if (object instanceof DeliverProduct) {
			DeliverProduct product = (DeliverProduct) object;
			String orderId = product.getOrderId();
			try {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				
				AtomicInteger currentRow = new AtomicInteger(0);
				String currentOrderId = "";
//				synchronized (this) {
					while (currentRow.get() < model.getRowCount() & !orderId.equals(currentOrderId)) {
						Integer current = currentRow.getAndIncrement();
						currentOrderId = String.valueOf(model.getValueAt(0, current));
						if (currentOrderId.equals(orderId)) {
//							model.removeRow(current);
							model.setValueAt("delivered", current, 2);
//							model.fireTableDataChanged();
						}
					}
//				}
			} catch (Exception e) {
				log.info("error:" + e+" when trying to proccess "+orderId);
			}
		} else {
			log.info("other message received");
		}
	}

	private void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Actor Model");
		JPanel pane = new JPanel();
		LayoutManager gridLayout = new GridBagLayout();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton buttonCreateOrder = new JButton("Create");
		buttonCreateOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ActorContainer container = ActorContainer.getSystem();
				// container.createActorRef(null, dispatcher)
				customerActor.ask("CREATE");
			}
		});

		pane.setLayout(gridLayout);
		// pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.ipadx = 0;

		constraints.ipady = 0;
		constraints.weighty = 0;
		constraints.gridwidth = 1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(5, 2, 10, 15);
		pane.add(buttonCreateOrder, constraints);

		// PersonalAbstractTableModel tableModel = new PersonalAbstractTableModel();
		// JTable table = new JTable(tableModel);
		DefaultTableModel tableModel = new DefaultTableModel(new Object[] { "Column1", "Column2", "Column 3" }, 0);

		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		JScrollPane scrollPane = new JScrollPane(table);
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 3;
		constraints.gridheight = 2;
		pane.add(scrollPane, constraints);

		frame.setContentPane(pane);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}
	

}
