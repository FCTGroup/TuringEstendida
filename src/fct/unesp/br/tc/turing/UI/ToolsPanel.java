package fct.unesp.br.tc.turing.UI;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JPanel;

public class ToolsPanel extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = 7458158923087183493L;
	private toolsButton selectNode;
	private toolsButton addNode;
	private toolsButton removeNode;
	private toolsButton addConnection;
	private toolsButton executeStep;
	private toolsButton executeMultiples;
	private GroupLayout GP;
	
	public ToolsPanel(){
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
		selectNode = new toolsButton("Select Node");
		addNode = new toolsButton("Add Node");
		removeNode = new toolsButton("Remove Node");
		addConnection = new toolsButton("Add Connection");
		executeStep = new toolsButton("Step by Step");
		executeMultiples = new toolsButton("Multiples");
		
		selectNode.addMouseListener(this);
		addNode.addMouseListener(this);
		removeNode.addMouseListener(this);
		addConnection.addMouseListener(this);
		executeStep.addMouseListener(this);
		executeMultiples.addMouseListener(this);
	}
	
	public void setComponents(){
		Group horizontalGroup = GP.createSequentialGroup();
		Group verticalGroup = GP.createSequentialGroup();
		
		horizontalGroup
			.addGap(5)
			.addComponent(selectNode, 80,80, Integer.MAX_VALUE)
			.addComponent(addNode, 80,80, Integer.MAX_VALUE)
			.addComponent(removeNode, 80,80, Integer.MAX_VALUE)
			.addComponent(addConnection, 80,80, Integer.MAX_VALUE)
			.addComponent(executeStep, 80,80, Integer.MAX_VALUE)
			.addComponent(executeMultiples, 80,80, Integer.MAX_VALUE)
			.addGap(5);
		
		verticalGroup
			.addGap(5)
			.addGroup(GP.createParallelGroup()
					.addComponent(selectNode, 25, 25, 25)
					.addComponent(addNode, 25, 25, 25)
					.addComponent(removeNode, 25, 25, 25)
					.addComponent(addConnection, 25, 25, 25)
					.addComponent(executeStep, 25, 25, 25)
					.addComponent(executeMultiples, 25,25,25)
				)
			.addGap(5);
		
		GP.setHorizontalGroup(horizontalGroup);
		GP.setVerticalGroup(verticalGroup);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(selectNode))
			((Frame)getTopLevelAncestor()).setSelectState();
		
		else if(e.getSource().equals(addNode))
			((Frame)getTopLevelAncestor()).setAddNodeState();
		
		else if(e.getSource().equals(removeNode))
			((Frame)getTopLevelAncestor()).setRemoveNodeState();
		
		else if(e.getSource().equals(addConnection))
			((Frame)getTopLevelAncestor()).setAddConnectionState();
		
		else if(e.getSource().equals(executeStep))
			((Frame)getTopLevelAncestor()).executeStep();
		
		else if(e.getSource().equals(executeMultiples))
			((Frame)getTopLevelAncestor()).executeMultiples();
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
