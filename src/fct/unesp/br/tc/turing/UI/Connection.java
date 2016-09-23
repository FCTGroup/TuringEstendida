package fct.unesp.br.tc.turing.UI;

import static fct.unesp.br.tc.turing.Transicao.L;
import static fct.unesp.br.tc.turing.Transicao.S;
import java.util.ArrayList;

public class Connection {
	
	private Node source;
	private Node dest;
	private ArrayList<Action> actions;
	
	public Connection(Node source, Node dest){
		this.source = source;
		this.dest = dest;
		actions = new ArrayList<Action>();
	}
	
	public Connection(Node source, Node dest, ArrayList<Action> actions){
		this.source = source;
		this.dest = dest;
		this.actions = actions;
	}

	public Node getSource() {
		return source;
	}

	public void setSource(Node source) {
		this.source = source;
	}

	public Node getDest() {
		return dest;
	}

	public void setDest(Node dest) {
		this.dest = dest;
	}

	public ArrayList<Action> getActions() {
		return actions;
	}

	public void addAction(Action action) {
		this.actions.add(action);
	}
	
}

class Action{
	
	public static final int EQUALS = 0;
	public static final int NOTEQUALS = 1;
	
	private ArrayList<Character> read;
	private ArrayList<Character> writen;
	private ArrayList<Character> movement;
	private ArrayList<Integer> modifier;
	
	public Action(){
            read = new ArrayList<Character>();
            writen = new ArrayList<Character>();
            movement = new ArrayList<Character>();
            modifier = new ArrayList<Integer>();

	}

	public char getRead(int numeroDaFita) {
		return read.get(numeroDaFita);
	}

	public void addRead(char read) {
		this.read.add(read);
	}
        
        public void setRead(char read, int numeroDaFita){
                this.read.set(numeroDaFita, read);
        }

	public char getWriten(int numeroDaFita) {
		return writen.get(numeroDaFita);
	}

	public void addWriten(char writen) {
		this.writen.add(writen);
	}
        
        public void setWriten(char writen, int numeroDaFita){
                this.writen.set(numeroDaFita, writen);
        }

	public char getMovement(int numeroDaFita) {
		return movement.get(numeroDaFita);
	}

	public void addMovement(char movement) {
		this.movement.add(movement);
	}
        
        public void setMovement(char movement, int numeroDaFita){
                this.movement.set(numeroDaFita, movement);
        }
        
        public void addModifier(int modifier){
                this.modifier.add(modifier);
        }
	
	public int getModifier(int numeroDaFita) {
		return modifier.get(numeroDaFita);
	}
        
        public void setModifier(int modifier, int numeroDaFita){
                this.modifier.set(numeroDaFita, modifier);
        }
        
        @Override
	public String toString(){
            
            String retorno = "";
            int transicao = 0;
            do{
                
                if(transicao != 0)
                    retorno += " | ";
                
                retorno = retorno.concat(this.getModifier(transicao)==1?retorno = "!":"");
		retorno = retorno.concat(this.getRead(transicao) == ' '?"□":String.valueOf(this.getRead(transicao)));
		retorno = retorno.concat(";");
		retorno = retorno.concat(this.getWriten(transicao) == ' '?"□":String.valueOf(this.getWriten(transicao)));
		retorno = retorno.concat(";");
		retorno = retorno.concat(String.valueOf(this.getMovement(transicao)));
                
                transicao++;
                
            }while(transicao < read.size());
           	
            return retorno;
	}
	
	/*@Override
	public String toString(){
		String retorno = "";
		retorno = retorno.concat(modifier == NOTEQUALS?retorno = "!":"");
		retorno = retorno.concat(read == ' '?"□":String.valueOf(read));
		retorno = retorno.concat("|");
		retorno = retorno.concat(writen == ' '?"□":String.valueOf(writen));
		retorno = retorno.concat("|");
		retorno = retorno.concat(String.valueOf(movement));
		return retorno;
	}*/
}
