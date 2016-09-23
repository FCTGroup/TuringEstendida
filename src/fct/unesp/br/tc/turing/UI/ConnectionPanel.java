package fct.unesp.br.tc.turing.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class ConnectionPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1218238596339252869L;
	
	private GroupLayout GP;
	private Group horizontalGroup;
	private Group verticalGroup;
	private JLabel labelNome;
	private JButton removeLine;
	private JCheckBox boxInicial;
	private JCheckBox boxFinal;
	private JScrollPane tablePane;
        
	public ConnectionPanel(){
		super();
		init();
		initComponents();
		setComponents();
		setVisible(true);
	}
	
	public void init(){
		GP = new GroupLayout(this);
		setBackground(new Color(240,240,240));
		setLayout(GP);
	}
	
	public void initComponents(){
		labelNome = new JLabel("Select a Node");
	}
	
	public void setComponents(){
		horizontalGroup = GP.createSequentialGroup();
		verticalGroup = GP.createSequentialGroup();
		
		horizontalGroup
			.addGap(5)
			.addComponent(labelNome)
			.addGap(5);
		
		verticalGroup
			.addGap(5)
			.addComponent(labelNome)
			.addGap(5);
		
		GP.setHorizontalGroup(horizontalGroup);
		GP.setVerticalGroup(verticalGroup);
	}	
	
	public synchronized void loadNode(Node node, ArrayList<Connection> nodeConnections){
		horizontalGroup = GP.createSequentialGroup();
		verticalGroup = GP.createSequentialGroup();
		Group tableVerticalGroup = GP.createSequentialGroup();
		Group tableHorizontalGroup = GP.createParallelGroup();
		
		labelNome.setText("Node "+node.getText());
		boxFinal = new JCheckBox("Final Node");
		boxInicial = new JCheckBox("Initial Node");
		ActionListener nodeListener = new nodeListener(node);
		boxInicial.addActionListener(nodeListener);
		boxFinal.addActionListener(nodeListener);
		
		boxInicial.setSelected(node.isInicial());
		boxFinal.setSelected(node.isFinal());
		
                
                String[] colums = new String[((Frame)getTopLevelAncestor()).getNumFitas()*3];
                for(int i = 0; i < ((Frame)getTopLevelAncestor()).getNumFitas(); i++){
                    colums[3*i] = "Read "+(i+1);
                    colums[3*i+1] = "Written "+(i+1);
                    colums[3*i+2] = "Movement "+(i+1);
                }
		JLabel labelNomeConnect = null;
                
                ArrayList<JComboBox<Character>> JCBMovementArray = new ArrayList<JComboBox<Character>>();
                                    
                for(int i = 0; i < ((Frame)getTopLevelAncestor()).getNumFitas(); i++){
                    JCBMovementArray.add(new JComboBox<Character>(new Character[]{'R','L','S'}));
                }
                
		DefaultTableModel DTModel;

		for(Connection connection:nodeConnections){
			
			labelNomeConnect = new JLabel(connection.getDest().getText()+":");
			JTable table = new JTable();
                        
			DTModel = ((DefaultTableModel)table.getModel());
			table.setModel(DTModel);
			DTModel.setColumnIdentifiers(colums);
			DTModel.addTableModelListener(new tableListener(connection));
                        for(int i = 0; i < ((Frame)getTopLevelAncestor()).getNumFitas(); i++){
                            table.getColumnModel().getColumn(3*i).setMinWidth(75);
                            table.getColumnModel().getColumn(3*i+1).setMinWidth(75);
                            table.getColumnModel().getColumn(3*i+2).setMinWidth(100);
                            table.getColumnModel().getColumn(3*i+2).setCellEditor(new DefaultCellEditor(JCBMovementArray.get(i)));
                        }
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			//table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(JCBMovement));
			removeLine = new JButton("Remove Row");
			removeLine.addActionListener(new buttonListener(table, connection));
			for(Action action:connection.getActions()){
                            Object[] items  = new Object[((Frame)getTopLevelAncestor()).getNumFitas()*3];
                            for(int i = 0; i < ((Frame)getTopLevelAncestor()).getNumFitas(); i++){
                                items[3*i] = (action.getModifier(i)==Action.EQUALS?"":"!")+(action.getRead(i) == ' '?"□":action.getRead(i));
                                items[3*i+1] = action.getWriten(i)== ' '?"□":action.getWriten(i);
                                items[3*i+2] = action.getMovement(i);
                            }
                            DTModel.addRow(items);
			}
			JScrollPane tablePane = new JScrollPane(table);
                        
			tableVerticalGroup
				.addGap(5)
				.addGroup(GP.createParallelGroup()
						.addComponent(labelNomeConnect)
						.addComponent(removeLine,25,25,25)
					)
				.addGap(2)
				.addComponent(tablePane, 0, 100, Integer.MAX_VALUE);
			
			tableHorizontalGroup
				.addGroup(GP.createSequentialGroup()
					.addComponent(labelNomeConnect)
					.addGap(5, 5, 5)
					.addComponent(removeLine,150,150,150)
					.addGap(3)
					)
				.addComponent(tablePane,100*((Frame)getTopLevelAncestor()).getNumFitas(),250*((Frame)getTopLevelAncestor()).getNumFitas(),Integer.MAX_VALUE);
		}
		
		horizontalGroup
			.addGap(5)
			.addGroup(GP.createParallelGroup()
					.addGroup(GP.createSequentialGroup()
							.addComponent(labelNome)
							.addGap(5, 5, 5)
							.addComponent(boxInicial)
							.addGap(5, 5, 5)
							.addComponent(boxFinal)
							.addGap(5, 5, 5)
							)
					.addGroup(tableHorizontalGroup)
					)
			.addGap(5);
		verticalGroup
			.addGap(5)
			.addGroup(GP.createParallelGroup()
					.addComponent(labelNome, 25, 25, 25)
					.addComponent(boxInicial, 25, 25, 25)
					.addComponent(boxFinal, 25, 25, 25)
					)
			.addGroup(tableVerticalGroup)
			.addGap(5);
		
		removeAll();
		
		GP.setHorizontalGroup(horizontalGroup);
		GP.setVerticalGroup(verticalGroup);
		
	}

	
	class nodeListener implements ActionListener{

		private Node node;
		public nodeListener(Node node){
			this.node = node;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(boxInicial)){
				node.setInitial(boxInicial.isSelected());
				if(boxInicial.isSelected()){
					for(Node node:((Frame)getTopLevelAncestor()).getNodes()){
						if(node.isInicial() && !node.equals(this.node))
							node.setInitial(false);
					}
				}
			}
			else{
				node.setFinal(boxFinal.isSelected());
			}
		}
		
	}
	
	class buttonListener implements ActionListener{
	
		private JTable table;
		private Connection connection;
		public buttonListener(JTable table, Connection connection){
			this.table = table;
			this.connection = connection;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				connection.getActions().remove(table.getSelectedRow());
				((DefaultTableModel)table.getModel()).removeRow(table.getSelectedRow());
				if(connection.getActions().size() == 0)
					((Frame)getTopLevelAncestor()).removeConnection(connection);
				
				((Frame)getTopLevelAncestor()).loadNode();
			}catch(ArrayIndexOutOfBoundsException ex){
				
			}
			
		}
	}
	
	class tableListener implements TableModelListener{

		private Connection connection;
		public tableListener(Connection connection){
			this.connection = connection;
		}
		
		@Override
		public synchronized void tableChanged(TableModelEvent e) {
			if(e.getType() != TableModelEvent.UPDATE)
				return;
			 Action a = connection.getActions().get(e.getFirstRow());
			switch(e.getColumn()%3){
				case 0:
					char c = ((String)((DefaultTableModel)e.getSource()).getValueAt(e.getFirstRow(), e.getColumn()))
					.toCharArray()[0];
					if(c == '!'){
						a.setRead((
								(String)((DefaultTableModel)e.getSource()).getValueAt(e.getFirstRow(), e.getColumn()))
								.toCharArray()[1],e.getColumn()/3);
						a.setModifier(Action.NOTEQUALS,e.getColumn()/3);
						
						if((((String)((DefaultTableModel)e.getSource()).getValueAt(e.getFirstRow(), e.getColumn()))
								.toCharArray()[1]) == ' ')
							((DefaultTableModel)e.getSource()).setValueAt('□', e.getFirstRow(), e.getColumn());
                                                
					}
					else{
						a.setRead(c,e.getColumn()/3);
						a.setModifier(Action.EQUALS,e.getColumn()/3);
						if(c == ' ')
							((DefaultTableModel)e.getSource()).setValueAt("□", e.getFirstRow(), e.getColumn());
					}
					break;
				case 1:
					a.setWriten((
							(String)((DefaultTableModel)e.getSource()).getValueAt(e.getFirstRow(), e.getColumn()))
							.toCharArray()[0],e.getColumn()/3);
					if((((String)((DefaultTableModel)e.getSource()).getValueAt(e.getFirstRow(), e.getColumn()))
							.toCharArray()[0]) == ' ')
						((DefaultTableModel)e.getSource()).setValueAt("□", e.getFirstRow(), e.getColumn());
					break;
				case 2:
					a.setMovement(
							(char)((DefaultTableModel)e.getSource()).getValueAt(e.getFirstRow(), e.getColumn()),e.getColumn()/3);
					break;
			}
		}
		
	}
}
