package fct.unesp.br.tc.turing.UI;

public class Node {

	private int id;
	private String text;
	private int[] pos;
	private boolean initialNode;
	private boolean finalNode;
	
	public Node(int id, String text, int[] pos){
		this.id = id;
		this.text = text;
		this.pos = pos;
	}
	
	public Node(int id, String text, int x, int y){
		this.id = id;
		this.text = text;
		this.pos = new int[2];
		this.pos[0] = x;
		this.pos[1] = y;
	}
	
	public boolean isInicial(){
		return initialNode;
	}
	
	public boolean isFinal(){
		return finalNode;
	}
	
	public void setFinal(boolean finalNode){
		this.finalNode = finalNode;
	}
	
	public void setInitial(boolean initialNode){
		this.initialNode = initialNode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setPos(int x, int y) {
		this.pos[0] = x;
		this.pos[1] = y;
	}
	
	public int getX(){
		return pos[0];
	}
	
	public int getY(){
		return pos[1];
	}
	
}
