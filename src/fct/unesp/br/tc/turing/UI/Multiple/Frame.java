package fct.unesp.br.tc.turing.UI.Multiple;

import java.awt.Dimension;

import javax.swing.JFrame;

import fct.unesp.br.tc.turing.Turing;

public class Frame extends JFrame{

	private static final long serialVersionUID = -2937958504469085339L;
	
	private MultiplesEntriesPanel MEP;
	
	public Frame(Turing t){
		super();
		init();
		initComponents(t);
		setComponents();
		setVisible(true);
	}
	
	public void init(){
		setTitle("Multiples");
		setMinimumSize(new Dimension(500, 300));
		setSize(500, 300);
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void initComponents(Turing t){
		MEP = new MultiplesEntriesPanel(t);
	}
	
	public void setComponents(){
		add(MEP);
	}
}
