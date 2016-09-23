package fct.unesp.br.tc.turing.UI;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7458158923087183493L;
	private EditPanel editorPanel;
	private Thread threadEditorPanel;
	private JScrollPane JSPeditorPanel;
	private JScrollPane JSPconnectionPanel;
	private ToolsPanel toolsPanel;
	private ConnectionPanel connectionPanel;
	private GroupLayout GP;
        private int numeroDeFitas;
	
	public MainPanel(int numeroDeFitas){
		super();
		init();
		initComponents();
		setComponents();
		setVisible(true);
                this.numeroDeFitas = numeroDeFitas;
	}
	
	public void init(){
		GP = new GroupLayout(this);
		setLayout(GP);
		setBackground(Color.BLACK);
	}
	
	public void initComponents(){
		editorPanel = new EditPanel();
		threadEditorPanel = new Thread(editorPanel);
		JSPeditorPanel = new JScrollPane(editorPanel);
		toolsPanel = new ToolsPanel();
		connectionPanel = new ConnectionPanel();
		JSPconnectionPanel = new JScrollPane(connectionPanel);
		
		JSPeditorPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		JSPeditorPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JSPconnectionPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		JSPconnectionPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		threadEditorPanel.start();
	}
	
	public void setComponents(){
		Group horizontalGroup = GP.createParallelGroup();
		Group verticalGroup = GP.createSequentialGroup();
		
		horizontalGroup
			.addGroup(GP.createSequentialGroup()
					.addGap(2)
					.addComponent(toolsPanel, 400, 400, Integer.MAX_VALUE)
					.addGap(2)
				)
			.addGroup(GP.createSequentialGroup()
					.addGap(2)
					.addComponent(JSPeditorPanel, 300, 300, Integer.MAX_VALUE)
					.addGap(2)
					.addComponent(JSPconnectionPanel, 192*numeroDeFitas, 192*numeroDeFitas, Integer.MAX_VALUE)
					.addGap(2)
				);
		
		verticalGroup
			.addGap(2)
			.addComponent(toolsPanel, 35, 35,35)
			.addGap(2)
			.addGroup(GP.createParallelGroup()
					.addComponent(JSPeditorPanel, 300, 300, Integer.MAX_VALUE)
					.addComponent(JSPconnectionPanel, 300, 300, Integer.MAX_VALUE)
					)
			.addGap(2);
		
		GP.setHorizontalGroup(horizontalGroup);
		GP.setVerticalGroup(verticalGroup);
	}

	public void setSelectState() {
		editorPanel.editState(EditPanel.SELECT_STATE);
		
	}

	public void setAddNodeState() {
		editorPanel.editState(EditPanel.ADD_STATE);
		
	}

	public void setAddConnectionState() {
		editorPanel.editState(EditPanel.CONNECTION_STATE);
		
	}

	public void setRemoveNodeState() {
		editorPanel.editState(EditPanel.REMOVE_STATE);
		
	}

	public void loadNode(Node focusedNode, ArrayList<Connection> focusedNodeConnections) {
		connectionPanel.loadNode(focusedNode, focusedNodeConnections);
		
	}

	public void removeConnection(Connection connection) {
		editorPanel.removeConnection(connection);
		
	}

	public Node getFocusedNode() {
		return editorPanel.getFocusedNode();
	}

	public ArrayList<Connection> getFocusedNodeConnections() {
		return editorPanel.getFocusedNodeConnections();
	}
	
	public ArrayList<Node> getNodes(){
		return editorPanel.getNodes();
	}

	public void executeStep() {
		editorPanel.executeStep();
	}

	public void executeMultiples() {
		editorPanel.executeMultiples();
		
	}

        public int getNumFitas() {
            return numeroDeFitas;
        }
}
