package fct.unesp.br.tc.turing.UI.Multiple;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fct.unesp.br.tc.turing.FinalNodeException;
import fct.unesp.br.tc.turing.InvalidCharacterException;
import fct.unesp.br.tc.turing.InvalidPositionException;
import fct.unesp.br.tc.turing.Turing;

public class FieldPanel extends JPanel{
	
	private static final long serialVersionUID = 4990509633543479975L;
	private ArrayList<JTextField> entries;
	private GroupLayout GL;
	private Group verticalGroup;
	private Group horizontalGroup;
	private Turing turing;
	
	public FieldPanel(Turing t){
		super();
		GL = new GroupLayout(this);
		setLayout(GL);
		entries = new ArrayList<JTextField>();
		turing = t;
		newField();
		update();
	}
	
	public void update(){
		horizontalGroup = GL.createParallelGroup();
		verticalGroup = GL.createSequentialGroup();
		
		for(JTextField field:entries){
			horizontalGroup.addGroup(
					GL.createSequentialGroup()
					.addGap(10)
					.addComponent(field, 400, 400, Integer.MAX_VALUE)
					.addGap(10)
					);
			verticalGroup.addGap(10)
			.addComponent(field, 25, 25, 25);
			
		}
		
		GL.setHorizontalGroup(horizontalGroup);
		GL.setVerticalGroup(verticalGroup);
	}
	
	public void newField(){
		entries.add(new JTextField());
	}

	public void run() {
		for(JTextField field:entries){
			turing.carregaFita(field.getText());
			try {
				turing.executaPrograma();
			} catch (InvalidCharacterException e) {
				field.setBackground(Color.RED);
			} catch (InvalidPositionException e) {
				field.setBackground(Color.RED);
			} catch (FinalNodeException e) {
				field.setBackground(Color.GREEN);
			}finally{
				turing.reseta();
			}
		}
		update();
	}

}
