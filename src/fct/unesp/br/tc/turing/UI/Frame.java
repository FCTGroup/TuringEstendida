package fct.unesp.br.tc.turing.UI;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Frame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3991416132677927029L;

	private MainPanel mainPanel;
	
	public Frame(){
		super();
		init();
		initComponents();
		setComponents();
		setVisible(true);
	}
	
	private void init(){
		Dimension size = new Dimension(500, 400);
		setSize(size);
		setResizable(true);
		setMinimumSize(size);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Turing Machine v1.0");
	}
	
	private void initComponents(){
            int numeroDeFitas = Integer.parseInt(JOptionPane.showInputDialog("Digite o numero de Fitas"));
            mainPanel = new MainPanel(numeroDeFitas);
	}
	
	private void setComponents(){		
		add(mainPanel);
	}	

	protected void setSelectState(){
		mainPanel.setSelectState();
	}
	
	public void setAddNodeState() {
		mainPanel.setAddNodeState();
		
	}

	public void setAddConnectionState() {
		mainPanel.setAddConnectionState();
		
	}

	public void setRemoveNodeState() {
		mainPanel.setRemoveNodeState();
		
	}

	public void loadNode(Node focusedNode, ArrayList<Connection> focusedNodeConnections) {
		mainPanel.loadNode(focusedNode, focusedNodeConnections);
		
	}

	public void removeConnection(Connection connection) {
		mainPanel.removeConnection(connection);
	}

	public void loadNode() {
		if(mainPanel.getFocusedNode() != null)
			mainPanel.loadNode(mainPanel.getFocusedNode(), mainPanel.getFocusedNodeConnections());
	}
	
	public ArrayList<Node> getNodes(){
		return mainPanel.getNodes();
	}

	public void executeStep() {
		mainPanel.executeStep();
	}
	
	public static void main(String args[]){
		new Frame();
	}

	public void executeMultiples() {
		mainPanel.executeMultiples();
		
	}
        
        public int getNumFitas(){
            return mainPanel.getNumFitas();
        }

}
