package fct.unesp.br.tc.turing.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fct.unesp.br.tc.turing.FuncaoPrograma;
import fct.unesp.br.tc.turing.Transicao;
import fct.unesp.br.tc.turing.TransicaoMultipla;
import fct.unesp.br.tc.turing.Turing;

public class EditPanel extends JPanel implements Runnable, MouseMotionListener, MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4567586333175478297L;
	
	public static final int SELECT_STATE = 0;
	public static final int ADD_STATE = 1;
	public static final int REMOVE_STATE = 2;
	public static final int CONNECTION_STATE = 3;
	
	private ArrayList<Node> nodes;
	private ArrayList<Connection> connections;
	private BufferedImage bfImage;
	private Graphics2D graphics;
	private Color nodeBackground;
	private int nodeRadious;
	private Node focusedNode;
	private int state;
	private int elementId;
	private int borderNodeSize;
	
	public EditPanel(){
		super();
		init();
		setVisible(true);
	}
	
	private void init(){
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(500,400));
		nodes = new ArrayList<Node>();
		connections = new ArrayList<Connection>();
		bfImage = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);
		graphics = (Graphics2D) bfImage.getGraphics();
		nodeBackground = new Color(250,250,0); //yellow
		nodeRadious = 30;	
		focusedNode = null;
		state = SELECT_STATE;
		elementId = 0;
		borderNodeSize = 3;
		
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(1000/60);
				draw();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (NullPointerException e){ 
				try {Thread.sleep(100);} catch (InterruptedException e1) {}
			}
		}
	}
	
	private synchronized void draw() throws NullPointerException{
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, bfImage.getWidth(), bfImage.getHeight());			
		
		drawConnections();
		
		drawNodes();
		
		getGraphics().drawImage(bfImage, 0, 0, null);
	}
	
	private void drawConnections(){
		int x1 = 0;
		int x2 = 0;
		int y1 = 0;
		int y2 = 0;
		
		for(Connection connection:connections){
			try{
				if(mouseIsInside(connection.getSource().getX(), connection.getSource().getY()))
					graphics.setColor(Color.GREEN);
				else
					graphics.setColor(Color.BLACK);
				
				x1 = connection.getSource().getX();
				x2 = connection.getDest().getX();
				y1 = connection.getSource().getY();
				y2 = connection.getDest().getY();
				
				if(connection.getSource().equals(connection.getDest())){
					graphics.drawLine(x1-nodeRadious/2, y1-nodeRadious/2, x1-nodeRadious/2, y1-(int)(nodeRadious*1.5f));
	                graphics.drawLine(x1-nodeRadious/2, y1-(int)(nodeRadious*1.5f), x1+nodeRadious/2, y1-(int)(nodeRadious*1.5f));
	                graphics.drawLine(x1+nodeRadious/2, y1-nodeRadious/2, x1+nodeRadious/2, y1-(int)(nodeRadious*1.5f));
				}
				else{
					graphics.drawLine(x1, y1, x2, y2);
					drawArrow(x1,y1,x2,y2,5);
				}			
				drawText(connection);
			}catch(NullPointerException e){
				
			}
		}
		if(state == CONNECTION_STATE && focusedNode != null){
			try{
				graphics.drawLine(focusedNode.getX(), focusedNode.getY(), (int)getMousePosition().getX(), (int)getMousePosition().getY());
			}catch(NullPointerException e){
				
			}
		}
			
	}
	
	private void drawNodes(){
		for(Node node:nodes){
			if(node.getX() + nodeRadious*2> bfImage.getWidth()){
				enlargeImage(50,0);
			}
			if(node.getY() + nodeRadious*2> bfImage.getHeight()){
				enlargeImage(0,50);
			}
			graphics.setColor(Color.BLACK);
			graphics.fillOval(node.getX() - nodeRadious, node.getY() - nodeRadious,nodeRadious*2, nodeRadious*2);
			if(mouseIsInside(node.getX(), node.getY())){
				if(state == REMOVE_STATE)
					graphics.setColor(Color.RED);
				else
					graphics.setColor(Color.CYAN);
			}
			else if(node.equals(focusedNode))
				graphics.setColor(Color.MAGENTA);
			else
				graphics.setColor(nodeBackground);
			
			graphics.fillOval(node.getX()+borderNodeSize - nodeRadious, node.getY()+borderNodeSize - nodeRadious,
					nodeRadious*2-borderNodeSize*2, nodeRadious*2-borderNodeSize*2);
			
			if(node.isFinal()){
				graphics.setColor(Color.BLACK);
				graphics.fillOval(node.getX() - Math.round(nodeRadious/1.2f), node.getY() - Math.round(nodeRadious/1.2f),
						Math.round(nodeRadious*1.666667f), Math.round(nodeRadious*1.666667f));
				if(mouseIsInside(node.getX(), node.getY())){
					if(state == REMOVE_STATE)
						graphics.setColor(Color.RED);
					else
						graphics.setColor(Color.CYAN);
				}
				else if(node.equals(focusedNode))
					graphics.setColor(Color.MAGENTA);
				else
					graphics.setColor(nodeBackground);
				
				graphics.fillOval(node.getX() + Math.round(borderNodeSize/1.2f) - Math.round(nodeRadious/1.2f), 
						node.getY() + Math.round(borderNodeSize/1.2f) - Math.round(nodeRadious/1.2f),
						Math.round(nodeRadious*1.666667f) - Math.round(borderNodeSize*1.666667f), 
						Math.round(nodeRadious*1.666667f) - Math.round(borderNodeSize*1.666667f));
			}
			
			if(node.isInicial()){
				graphics.setColor(Color.BLACK);
				graphics.drawLine(node.getX() - nodeRadious, node.getY(), node.getX() - 25 - nodeRadious, node.getY() - 25);
				graphics.drawLine(node.getX() - nodeRadious, node.getY(), node.getX() - 25 - nodeRadious, node.getY() + 25);
				graphics.drawLine(node.getX() - 25 - nodeRadious, node.getY() - 25, node.getX() - 25 - nodeRadious, node.getY() + 25);
			}
			graphics.setColor(Color.BLACK);
			graphics.drawString(node.getText(),
					node.getX() - node.getText().length()*(graphics.getFont().getSize()/2)/2,
					node.getY() + graphics.getFont().getSize()/4);
		}		
		if(state == ADD_STATE){
			try{
				graphics.setColor(Color.BLACK);
				graphics.fillOval((int)getMousePosition().getX() - nodeRadious, 
						(int)getMousePosition().getY() - nodeRadious,nodeRadious*2, nodeRadious*2);
				graphics.setColor(Color.GREEN);
				graphics.fillOval((int)getMousePosition().getX()+borderNodeSize - nodeRadious, 
						(int)getMousePosition().getY()+borderNodeSize - nodeRadious,
						nodeRadious*2-borderNodeSize*2, nodeRadious*2-borderNodeSize*2);
			}catch(NullPointerException e){
				
			}
		}
	}
	
	private void drawText(Connection connection){
        int count = 0;
        
		for(Action action:connection.getActions()){
			++count;
			if(connection.getSource().equals(connection.getDest()))
				graphics.drawString(action.toString(),
		        		(connection.getSource().getX()+connection.getDest().getX())/2 - Math.round(action.toString().length()*(graphics.getFont().getSize()/2)/2), 
		        		connection.getSource().getY() - nodeRadious*1.5f - graphics.getFont().getSize()*count);
			
			else
		        graphics.drawString(action.toString(),
		        		(connection.getSource().getX()+connection.getDest().getX())/2 - Math.round(action.toString().length()*(graphics.getFont().getSize()/2)/2), 
		        		(connection.getSource().getY()+connection.getDest().getY())/2 - graphics.getFont().getSize()*count);
		}
	}
	
	private void drawArrow(int x1, int y1, int x2, int y2, float size) {
        float r = (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        float cos = (x2 - x1) / (r);
        float sen = (y2 - y1) / (r);

        //rotação e translação
        int transX = (int) ((x2 + x1) * 0.5f); //metade da reta
        int transY = (int) ((y2 + y1) * 0.5f); //metade da reta

        Point pa = new Point(Math.round(-sen * size) + (transX), Math.round(cos * size) + (transY));
        Point pb = new Point(Math.round(-sen * -size) + (transX), Math.round(cos * -size) + (transY));
        Point pc = new Point(Math.round(cos * size) + (transX), Math.round(sen * size) + (transY));

        graphics.drawLine(pa.x, pa.y, pc.x, pc.y);
        graphics.drawLine(pb.x, pb.y, pc.x, pc.y);
    }
	
	private void enlargeImage(int dx, int dy){
		BufferedImage bfImageTemp = new BufferedImage(bfImage.getWidth() + dx, bfImage.getHeight() + dy, BufferedImage.TYPE_INT_RGB);
		graphics = (Graphics2D) bfImageTemp.getGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.drawImage(bfImage, 0, 0, null);
		bfImage = bfImageTemp;
		if(bfImage.getHeight() > getPreferredSize().getHeight() || bfImage.getWidth() > getPreferredSize().getWidth()){
			enlargePanel(dx,dy);
		}		
	}
	
	private void enlargePanel(int dx, int dy){
		int nx, ny;
		nx = (int)Math.round(getPreferredSize().getWidth());
		ny = (int)Math.round(getPreferredSize().getHeight());
		nx += dx;
		ny += dy;
		setPreferredSize(new Dimension(nx,ny));
		revalidate();
	}
	
	private boolean mouseIsInside(int x1, int y1){
		int x2;
		int y2;
		try{
			x2 = (int) getMousePosition().getX() - x1;
			y2 = (int) getMousePosition().getY() - y1;
			if(Math.sqrt(Math.pow(x2, 2) + Math.pow(y2, 2)) < nodeRadious)
				return true;
		}catch(NullPointerException e){
			
		}
		return false;
	}
	
	private void insertNode(int x, int y){
		nodes.add(new Node(elementId++, "q"+nodes.size(), new int[]{x,y}));
	}
	
	private synchronized Connection getConnection(Node source, Node dest){
		try{
		for(Connection connection:connections)
			if(connection.getSource().equals(source) && connection.getDest().equals(dest))
				return connection;
		}catch(NullPointerException e){
			
		}
		return null;
	}
	
	public void editState(int state){
		if(state >= 0 || state <= 3){
			focusedNode = null;
			this.state = state;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(focusedNode!=null){
			if(state == SELECT_STATE)
				focusedNode.setPos(e.getX(), e.getY());
		}
			
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	public synchronized void mousePressed(MouseEvent e) {
		focusedNode = null;
		if(state != ADD_STATE){
			for(int i = nodes.size() - 1; i >= 0; i--)
				if(mouseIsInside(nodes.get(i).getX(), nodes.get(i).getY())){
					focusedNode = nodes.get(i);
					break;
				}
			if(focusedNode == null)
				return;
			if(state == REMOVE_STATE){
				for(int i = connections.size() - 1; i >= 0; i--)
					if(connections.get(i).getSource().equals(focusedNode) || 
							connections.get(i).getDest().equals(focusedNode))
						connections.remove(i);
				nodes.remove(focusedNode);
				((Frame)getTopLevelAncestor()).loadNode();
				focusedNode = null;
			}
			if(state == SELECT_STATE){
				@SuppressWarnings("unchecked")
				ArrayList<Connection> focusedNodeConnections = (ArrayList<Connection>) connections.clone();
				for(int i = focusedNodeConnections.size() - 1; i >= 0; i--)
					if(!focusedNodeConnections.get(i).getSource().equals(focusedNode))
						focusedNodeConnections.remove(i);
				((Frame)getTopLevelAncestor()).loadNode(focusedNode, focusedNodeConnections);
			}
		}
		else
			insertNode(e.getX(), e.getY());
	}

	@Override
	public synchronized void mouseReleased(MouseEvent e) {
		if(state == CONNECTION_STATE){
			for(int i = nodes.size() - 1; i >= 0; i--)
				if(mouseIsInside(nodes.get(i).getX(), nodes.get(i).getY())){
					Connection connection = getConnection(focusedNode, nodes.get(i));
					if(connection == null){
						connection = new Connection(focusedNode, nodes.get(i));
						connections.add(connection);
					}
                                        Action action = new Action();
                                        
                                        for(int filaAtual = 0; filaAtual < ((Frame)getTopLevelAncestor()).getNumFitas(); filaAtual++){
                                            action.addWriten(' ');
                                            action.addRead(' ');
                                            action.addMovement('S');
                                            action.addModifier(Action.EQUALS);
                                        }
                                        
                                        connection.addAction(action);
                                        
					((Frame)getTopLevelAncestor()).loadNode();
					break;
				}
		}
		if(state != SELECT_STATE)
			focusedNode = null;
	}

	public void removeConnection(Connection connection) {
		connections.remove(connection);		
	}

	public Node getFocusedNode() {
		return focusedNode;
	}

	public ArrayList<Connection> getFocusedNodeConnections() {
		@SuppressWarnings("unchecked")
		ArrayList<Connection> focusedNodeConnections = (ArrayList<Connection>) connections.clone();
		for(int i = focusedNodeConnections.size() - 1; i >= 0; i--)
			if(!focusedNodeConnections.get(i).getSource().equals(focusedNode))
				focusedNodeConnections.remove(i);
		return focusedNodeConnections;
	}
	
	public ArrayList<Node> getNodes(){
		return nodes;
	}
	
	public void executeStep(){
                String fita = "";
                try{
                    fita = JOptionPane.showInputDialog("");
                }catch(Exception e){
                    e.printStackTrace();
                }
		Hashtable<Integer,Point> positions = new Hashtable<Integer,Point>();
		FuncaoPrograma p = new FuncaoPrograma();
                p.setNumFitas(((Frame)getTopLevelAncestor()).getNumFitas());
		for(Node node:nodes){
			positions.put(node.getId(), new Point(node.getX(), node.getY()));
			if(node.isFinal())
				p.addEstadoFinal(node.getId());
			if(node.isInicial())
				p.setEstadoInicial(node.getId());
		}
		TransicaoMultipla transicao;
                Transicao transicaoFita;
		for(Connection connection:connections){
			for(Action action:connection.getActions()){
                                transicao = new TransicaoMultipla(connection.getDest().getId());
                                for(int fitaAtual = 0; fitaAtual < p.getNumFitas(); fitaAtual++){
                                    transicaoFita = new Transicao(action.getRead(fitaAtual), 
					action.getWriten(fitaAtual), 
					action.getMovement(fitaAtual) == 'L'?-1:(action.getMovement(fitaAtual) == 'S'?0:1), 
                                        action.getModifier(fitaAtual) == Action.EQUALS?false:true);
                                    transicao.add(transicaoFita);
                                }
                                p.addTransicao(connection.getSource().getId(), transicao);
			}
		}
		final String finalFita = fita;
		final Hashtable<Integer,Point> finalPositions = positions;
		final FuncaoPrograma finalP = p;
                
		new Thread(){
			@Override
			public void run(){
				new fct.unesp.br.tc.turing.UI.Step.Frame(finalPositions, finalFita, finalP, ((Frame)getTopLevelAncestor()).getNumFitas());
			}
		}.start();
	}

	public void executeMultiples() {
		String fita = "";
                try{
                    fita = JOptionPane.showInputDialog("");
                }catch(Exception e){
                    e.printStackTrace();
                }
		Hashtable<Integer,Point> positions = new Hashtable<Integer,Point>();
		FuncaoPrograma p = new FuncaoPrograma();
                p.setNumFitas(((Frame)getTopLevelAncestor()).getNumFitas());
		for(Node node:nodes){
			positions.put(node.getId(), new Point(node.getX(), node.getY()));
			if(node.isFinal())
				p.addEstadoFinal(node.getId());
			if(node.isInicial())
				p.setEstadoInicial(node.getId());
		}
		TransicaoMultipla transicao;
                Transicao transicaoFita;
		for(Connection connection:connections){
			for(Action action:connection.getActions()){
                                transicao = new TransicaoMultipla(connection.getDest().getId());
                                for(int fitaAtual = 0; fitaAtual < p.getNumFitas(); fitaAtual++){
                                    transicaoFita = new Transicao(action.getRead(fitaAtual), 
					action.getWriten(fitaAtual), 
					action.getMovement(fitaAtual) == 'L'?-1:(action.getMovement(fitaAtual) == 'S'?0:1), 
                                        action.getModifier(fitaAtual) == Action.EQUALS?false:true);
                                    transicao.add(transicaoFita);
                                }
                                p.addTransicao(connection.getSource().getId(), transicao);
			}
		}
		final FuncaoPrograma finalP = p;
		new Thread(){
			@Override
			public void run(){
				new fct.unesp.br.tc.turing.UI.Multiple.Frame(new Turing(finalP));
			}
		}.start();
	}

}
