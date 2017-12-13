package com.blaze.ui;

import com.blaze.actor.AbstractActor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.blaze.actor.ActorContainer;
import com.blaze.actor.ActorRef;
import com.blaze.actor.CustomerActor;
import com.blaze.model.Order;

public class MainWindow extends AbstractActor {

    JTable table;
    @Override
    protected void receiveMessage(Object object) {
        if (object instanceof String) {
            String message = String.valueOf(object);
            switch (message) {
                case "START":
                    createAndShowGUI();
                    break;
            }
        } else if(object instanceof Order){
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            Order order =(Order)object;
            String[] data = new String[]{order.getOrderId(),order.getOrderId()};
            model.addRow(data);
        }else {
            log.info("other message received");
        }
    }

    private void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("Actor Model");
        JPanel pane = new JPanel();
        LayoutManager gridLayout = new GridBagLayout();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel customerActionLabel = new JLabel("Customer Action", SwingConstants.CENTER);
        JButton buttonCreateOrder = new JButton("Create");
        AbstractActor abstractActor = this;
        buttonCreateOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ActorContainer container = ActorContainer.getSystem();
                ActorRef customerActor = container.createActorRef(CustomerActor.class, abstractActor);
//                container.createActorRef(null, dispatcher)
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

        constraints.weightx = 0.5;
        constraints.gridx = 1;
        constraints.gridy = 0;
        pane.add(customerActionLabel, constraints);

//		PersonalAbstractTableModel tableModel = new PersonalAbstractTableModel();
        // JTable table = new JTable(tableModel);
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Column1", "Column2", "Column 3"}, 0);

         table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        JScrollPane scrollPane = new JScrollPane(table);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        constraints.gridheight = 2;
        pane.add(scrollPane, constraints);

        JButton button = new JButton("other button");
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                log.info("adding message");
                // ((PersonalTableModel)tableModel).addRow(new String[]{"",""});
                // ((PersonalTableModel)tableModel).fireTableDataChanged();
                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
//				((PersonalAbstractTableModel) tableModel).addRow(new String[] { "new", "value" });
//				((PersonalAbstractTableModel) tableModel).fireTableDataChanged();;
                tableModel.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
                // defaultTableModel.addRow(new Object[] {"asdfa"});

            }
        });
        pane.add(button, constraints);

        frame.setContentPane(pane);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

}
