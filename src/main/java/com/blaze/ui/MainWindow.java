package com.blaze.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import com.blaze.actor.ActorContainer;
import com.blaze.actor.ActorRef;
import com.blaze.actor.CustomerActor;

public class MainWindow {

	private static Logger log = Logger.getLogger(MainWindow.class);

	private static class PersonalTableModel implements TableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		List<String[]> listaValues;

		public PersonalTableModel() {
			listaValues = new ArrayList<>();
		}

		@Override
		public void setValueAt(Object value, int row, int column) {
			String[] rowValue = listaValues.get(row);
			rowValue[column] = String.valueOf(value);
		}

		@Override
		public void removeTableModelListener(TableModelListener arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean isCellEditable(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object getValueAt(int row, int column) {
			String[] valueAt = listaValues.get(row);
			return valueAt[column];
		}

		@Override
		public int getRowCount() {
			int count = listaValues != null ? listaValues.size() : 0;
			return count;
		}

		@Override
		public String getColumnName(int arg0) {
			String columnName = null;
			switch (arg0) {
			case 0:
				columnName = "one";
				break;
			case 1:
				columnName = "two";
				break;
			}
			return columnName;
		}

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public Class<?> getColumnClass(int arg0) {
			return String.class;
		}

		@Override
		public void addTableModelListener(TableModelListener arg0) {
			// TODO Auto-generated method stub

		}

		public void addRow(String[] row) {
			listaValues.add(row);
		}
	}

	private static class PersonalAbstractTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private List<String[]> listValues = new ArrayList<>();

		private void initListValues() {
		if(listValues==null)listValues = new ArrayList<>();	
		}
		
		@Override
		public Object getValueAt(int row, int column) {
			initListValues();
//			Object value = listValues.get(row)!=null && listValues.get(row)[column]!=null?listValues.get(row)[column]:"";
			return "";
		}

		@Override
		public int getRowCount() {
			initListValues();
			return 2;
		}
		
		@Override
		public Class getColumnClass(int c) {
	        return String.class;
	    }

		@Override
		public String getColumnName(int col) {
			initListValues();
	        String nameColumn=null;
	        switch(col){
	        	case 0:
	        		nameColumn="one";
	        		break;
	        	case 1:
	        		nameColumn="two";
	        		break;
	        }
	        return nameColumn;
	    }

		@Override
		public int getColumnCount() {
			return 2;
		}

		public void addRow(String[] row) {
			initListValues();
			listValues.add(row);
			fireTableChanged(null);
		}

	}

	
	
	public static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("FrameDemo");
		JPanel pane = new JPanel();
		LayoutManager gridLayout = new GridBagLayout();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel customerActionLabel = new JLabel("Customer Action", SwingConstants.CENTER);
		JButton buttonCreateOrder = new JButton("Create");

		buttonCreateOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ActorContainer container = ActorContainer.getSystem();
				ActorRef customerActor = container.createActorRef(CustomerActor.class);
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

		PersonalAbstractTableModel tableModel = new PersonalAbstractTableModel();

		// JTable table = new JTable(tableModel);
		JTable table = new JTable(tableModel);
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
				TableModel tableModel = table.getModel();
				((PersonalAbstractTableModel) tableModel).addRow(new String[] { "new", "value" });
				((PersonalAbstractTableModel) tableModel).fireTableDataChanged();;
				
				// defaultTableModel.addRow(new Object[] {"asdfa"});
				
			}
		});
		pane.add(button, constraints);

		// JButton button;
		// pane.setLayout(new GridBagLayout());
		// GridBagConstraints c = new GridBagConstraints();
		//
		//
		// button = new JButton("Button 1");
		// c.fill = GridBagConstraints.HORIZONTAL;
		// c.gridx = 0;
		// c.gridy = 0;
		// pane.add(button, c);
		//
		// button = new JButton("Button 2");
		// c.fill = GridBagConstraints.HORIZONTAL;
		// c.weightx = 0.5;
		// c.gridx = 1;
		// c.gridy = 0;
		// pane.add(button, c);
		//
		// button = new JButton("Button 3");
		// c.fill = GridBagConstraints.HORIZONTAL;
		// c.weightx = 0.5;
		// c.gridx = 2;
		// c.gridy = 0;
		// pane.add(button, c);
		//
		// button = new JButton("Long-Named Button 4");
		// c.fill = GridBagConstraints.HORIZONTAL;
		// c.ipady = 40; // make this component tall
		// c.weightx = 0.0;
		// c.gridwidth = 3;
		// c.gridx = 0;
		// c.gridy = 1;
		// pane.add(button, c);
		//
		// button = new JButton("5");
		// c.fill = GridBagConstraints.HORIZONTAL;
		// c.ipady = 0; // reset to default
		// c.weighty = 1.0; // request any extra vertical space
		// c.anchor = GridBagConstraints.PAGE_END; // bottom of space
		// c.insets = new Insets(10, 0, 0, 0); // top padding
		// c.gridx = 1; // aligned with button 2
		// c.gridwidth = 2; // 2 columns wide
		// c.gridy = 2; // third row
		// pane.add(button, c);

		frame.setContentPane(pane);

		frame.pack();
		frame.setLocationRelativeTo(null);
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

}
