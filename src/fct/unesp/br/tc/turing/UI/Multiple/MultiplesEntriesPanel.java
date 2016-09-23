package fct.unesp.br.tc.turing.UI.Multiple;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fct.unesp.br.tc.turing.Turing;

public class MultiplesEntriesPanel extends JPanel{

	private static final long serialVersionUID = -2937958504469085339L;
	
	private JButton run;
	private JButton newField;
	private FieldPanel fieldPanel;
	private JScrollPane JSPfields;
	private GroupLayout GL;
	
	public MultiplesEntriesPanel(Turing t){
		super();
		init();
		initComponents(t);
		setComponents();
		setVisible(true);
	}
	
	public void init(){
		GL = new GroupLayout(this);
		setLayout(GL);
	}
	
	public void initComponents(Turing t){
		run = new JButton("Check");
		newField = new JButton("Add new entry");
		fieldPanel = new FieldPanel(t);
		JSPfields = new JScrollPane(fieldPanel);
		
		JSPfields.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JSPfields.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		newField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fieldPanel.newField();
				fieldPanel.update();
			}
		});
		
		run.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fieldPanel.run();
			}
		});
	}
	
	public void setComponents(){
		GL.setHorizontalGroup(
				GL.createParallelGroup()
					.addGroup(GL.createSequentialGroup()
						.addGap(100,100,Integer.MAX_VALUE)
						.addComponent(newField,200,200,200)
						.addGap(20,20,50)
						)
					.addComponent(JSPfields,500,500,Integer.MAX_VALUE)
					.addGroup(GL.createSequentialGroup()
						.addGap(100,100,Integer.MAX_VALUE)
						.addComponent(run,200,200,200)
						.addGap(20,20,50)
						)
				);
		
		GL.setVerticalGroup(
				GL.createSequentialGroup()
					.addGap(10)
					.addComponent(newField,25,25,25)
					.addGap(10)
					.addComponent(JSPfields,100,100,Integer.MAX_VALUE)
					.addGap(10)
					.addComponent(run,25,25,25)
					.addGap(10)
				);
	}
}
