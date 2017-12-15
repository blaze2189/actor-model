package com.blaze.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.blaze.actor.AbstractActor;
import com.blaze.actor.ActorContainer;
import com.blaze.actor.ActorRef;
import com.blaze.actor.PaymentActor;
import com.blaze.model.Order;
import com.blaze.model.Product;
import com.blaze.util.Food;
import com.blaze.util.Status;

public class CashierUi extends AbstractActor {

	private JLabel productLable;
	private JLabel amountLabel;
	private JButton createButton;
	private JTextField amountTextField;
	private JComboBox<Food> foodCombo;
	private JTable orderTable;
	private JScrollPane orderPane;
	private JButton sendButton;

	private ActorContainer container = ActorContainer.getSystem();
	private ActorRef paymentActor = container.createActorRef(PaymentActor.class, 2);
	private ActorRef mainWindow = container.retrieveActorRef(MainWindow.class);

	private void createAndShowView() {

		JFrame frame = new JFrame("Cashier View");
		JPanel panel = new JPanel();
		LayoutManager gridLayout = new GridBagLayout();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		productLable = new JLabel("Product:");
		amountLabel = new JLabel("Amount: ");

		amountTextField = new JTextField();

		createButton = new JButton("Add to Order");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
				Integer totalAmount = Integer.parseInt(amountTextField.getText());
				for (int amount = 0; amount < totalAmount; amount++) {
					model.addRow(new Object[] { foodCombo.getSelectedItem() });
				}
			}
		});

		sendButton = new JButton("Send Order");
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Order order = new Order();
				List<Product> listProduct = new ArrayList<>();
				for (int row = 0; row < orderTable.getRowCount(); row++) {
					Product product = new Product();
					Food productName = (Food) orderTable.getValueAt(row, 0);
					switch (productName) {
					case FRIES:
						product.setFood(Food.FRIES);
						product.setStatus(Status.PREPARATION);
						break;
					case HAMBURGUER:
						product.setFood(Food.HAMBURGUER);
						product.setStatus(Status.PREPARATION);
						break;
					case NUGGET:
						product.setFood(Food.NUGGET);
						product.setStatus(Status.PREPARATION);
						break;
					case SODA:
						product.setFood(Food.SODA);
						product.setStatus(Status.PREPARATION);
						break;
					}
					listProduct.add(product);
				}
				Double orderId = Math.random() * 10000;
				String orderIdString = String.valueOf(Math.round(orderId));
				log.info("new order " +orderIdString);
				order.setOrderId(orderIdString);
				order.setProductList(listProduct);
				paymentActor.ask(order);
				mainWindow.ask(order);
				DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
				Integer totalRows = model.getRowCount();
				while (model.getRowCount() > 0) {
					model.removeRow(model.getRowCount() - 1);
				}
			}
		});

		foodCombo = new JComboBox<>();
		foodCombo.addItem(Food.FRIES);
		foodCombo.addItem(Food.HAMBURGUER);
		foodCombo.addItem(Food.NUGGET);
		foodCombo.addItem(Food.SODA);

		DefaultTableModel model = new DefaultTableModel(new Object[] { "Product", "Price", "Status" }, 0);

		orderTable = new JTable(model);
		orderPane = new JScrollPane(orderTable);

		panel.setLayout(gridLayout);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;

		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.insets = new Insets(5, 5, 0, 0);
		panel.add(productLable, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		panel.add(foodCombo, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		panel.add(amountLabel, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.insets = new Insets(5, 5, 5, 5);
		panel.add(amountTextField, constraints);

		constraints.gridx = 3;
		constraints.gridy = 1;
		panel.add(createButton, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridheight = 5;
		constraints.gridwidth = 5;
		panel.add(orderPane, constraints);

		constraints.gridx = 4;
		constraints.gridy = 9;
		constraints.gridheight = 0;
		constraints.gridwidth = 0;
		panel.add(sendButton, constraints);

		frame.setContentPane(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	@Override
	protected void receiveMessage(Object object) {
		if (object instanceof String) {
			String stringMessage = String.valueOf(object);
			if (stringMessage.equals("START")) {
				createAndShowView();
			}
		}
	}

	@Override
	protected void receiveResponse(Object object) {
	}

}
