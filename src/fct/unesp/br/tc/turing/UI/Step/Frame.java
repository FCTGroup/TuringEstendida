package fct.unesp.br.tc.turing.UI.Step;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Hashtable;

import javax.swing.JFrame;

import fct.unesp.br.tc.turing.FinalNodeException;
import fct.unesp.br.tc.turing.FuncaoPrograma;
import fct.unesp.br.tc.turing.InvalidCharacterException;
import fct.unesp.br.tc.turing.InvalidPositionException;
import fct.unesp.br.tc.turing.Turing;

public class Frame extends JFrame{
	
	private static final long serialVersionUID = -3991416132677927029L;

	private MainPanel mainPanel;
	private Turing turing;
        private int numeroDeFitas;
	
	public Frame(Hashtable<Integer, Point> positions, String fita, FuncaoPrograma p, int numeroDeFitas){
		super();
                this.numeroDeFitas = numeroDeFitas;
		init(fita);
		initComponents(positions, fita, p);
		setComponents();
		setVisible(true);
		mainPanel.draw(turing);
	}
	
	private void init(String fita){
		Dimension size = new Dimension(500, 400);
		setSize(size);
		setResizable(true);
		setMinimumSize(size);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Turing Machine v1.0 - Step by Step - "+fita);
	}
	
	private void initComponents(Hashtable<Integer, Point> positions, String fita, FuncaoPrograma p){
		turing = new Turing(p);
                turing.reseta();
		turing.carregaFita(fita);
		mainPanel = new MainPanel(positions, fita, numeroDeFitas);
	}
	
	private void setComponents(){		
		add(mainPanel);
	}
	
	protected void nextStep(){
		try {
			int movement[] = turing.iteraPrograma();
                        mainPanel.updateFitas(turing.getArrayDeFitas());
			mainPanel.moveTape(movement);
		} catch (InvalidCharacterException | InvalidPositionException e) {
			mainPanel.error();
		} catch (FinalNodeException e) {
			mainPanel.success();
		}finally{
			mainPanel.draw(turing);
		}
	}
}
