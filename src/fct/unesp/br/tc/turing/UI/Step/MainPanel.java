package fct.unesp.br.tc.turing.UI.Step;

import java.awt.Point;
import java.util.Hashtable;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fct.unesp.br.tc.turing.Turing;
import java.util.ArrayList;

public class MainPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7458158923087183493L;
	private NodesPanel nodesPanel;
	private ArrayList<TapePanel> tapePanel;
	private ArrayList<Thread> threadTapePanel;
	private Thread threadNodesPanel;
	private JScrollPane JSPnodesPanel;
	private GroupLayout GP;
        private int numeroDeFitas = 0;
	
	public MainPanel(Hashtable<Integer, Point> positions, String fita, int numeroDeFitas){
		super();
		init();
                
                this.numeroDeFitas = numeroDeFitas;
                tapePanel = new ArrayList<TapePanel>();
                for(int i = 0; i < numeroDeFitas; i++)
                    tapePanel.add(new TapePanel(i==0?fita:"",i==0));
                
                threadTapePanel = new ArrayList<Thread>();
                for(int i = 0; i < numeroDeFitas; i++)
                    threadTapePanel.add(new Thread(tapePanel.get(i)));
                
                
		initComponents(positions, fita);
		setComponents();
		setVisible(true);
                for(int i = 0; i < numeroDeFitas; i++){
                    threadTapePanel.get(i).start();
                }
		threadNodesPanel.start();
	}
	
	public void init(){
		GP = new GroupLayout(this);
		setLayout(GP);
	}
	
	public void initComponents(Hashtable<Integer, Point> positions, String fita){
		nodesPanel = new NodesPanel(positions);
		JSPnodesPanel = new JScrollPane(nodesPanel);
		
		JSPnodesPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		JSPnodesPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		threadNodesPanel = new Thread(nodesPanel);
	}
	
	public void setComponents(){
		Group horizontalGroup = GP.createParallelGroup();
		Group verticalGroup = GP.createSequentialGroup();
		
		horizontalGroup
			.addGap(2)
			.addComponent(JSPnodesPanel, 500, 500, Integer.MAX_VALUE);
                
                verticalGroup
			.addGap(2)
                        .addComponent(JSPnodesPanel, 300, 300, Integer.MAX_VALUE);
                
                for(int i = 0; i < numeroDeFitas; i++){
                    horizontalGroup
			.addComponent(tapePanel.get(i), 500, 500, Integer.MAX_VALUE)
			.addGap(2);
                    
                    verticalGroup
                        .addComponent(tapePanel.get(i), 30, 30, 30)
			.addGap(2);
                }
                    
		
			
		
		GP.setHorizontalGroup(horizontalGroup);
		GP.setVerticalGroup(verticalGroup);
	}

	public void draw(Turing turing) {
		nodesPanel.draw(turing.getFuncaoPrograma().getListaTransicoes(), turing.getEstadoAtual(), turing.getFuncaoPrograma().getEstadoInicial(), turing.getFuncaoPrograma().getEstadosFinais());
	}
	
	public void moveTape(int movement[]){
            for(int i = 0; i < numeroDeFitas; i++)
		tapePanel.get(i).move(movement[i]);
	}

	public void error(){
            for(int i = 0; i < numeroDeFitas; i++)
		tapePanel.get(i).error();
		nodesPanel.error();
	}
	
	public void success(){
            for(int i = 0; i < numeroDeFitas; i++)
		tapePanel.get(i).success();
		nodesPanel.success();
	}
}
