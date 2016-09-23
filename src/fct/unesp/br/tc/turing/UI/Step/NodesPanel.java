package fct.unesp.br.tc.turing.UI.Step;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JPanel;

import com.google.common.collect.ArrayListMultimap;

import fct.unesp.br.tc.turing.Transicao;
import fct.unesp.br.tc.turing.TransicaoMultipla;

public class NodesPanel extends JPanel implements Runnable{
	
	private static final long serialVersionUID = 4567586333175478297L;
	private Hashtable<Integer, Point> positions;
	private BufferedImage bfImage;
	private Graphics2D graphics;
	private Color nodeBackground;
	private Color nodeFocusBackground;
	private int nodeRadious;
	private int borderNodeSize;
	private ArrayListMultimap<Integer, TransicaoMultipla> allTransitions;
	private Integer estadoAtual;
	private Integer estadoInicial;
	private ArrayList<Integer> estadosFinais;
	
	public NodesPanel(Hashtable<Integer, Point> positions){
		super();
		init(positions);
		setVisible(true);
	}
	
	private void init(Hashtable<Integer, Point> positions){
		this.positions = positions;
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(500,400));
		bfImage = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);
		graphics = (Graphics2D) bfImage.getGraphics();
		nodeBackground = new Color(250,250,0); //yellow
		nodeFocusBackground = new Color(0,250,250); //yellow
		nodeRadious = 30;
		borderNodeSize = 3;
		positions = new Hashtable<Integer, Point>();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
	}
	
	public synchronized void draw(ArrayListMultimap<Integer, TransicaoMultipla> allTransitions, Integer estadoAtual, Integer estadoInicial, ArrayList<Integer> estadosFinais){
		this.allTransitions = allTransitions;
		this.estadoAtual = estadoAtual;
		this.estadoInicial = estadoInicial;
		this.estadosFinais = estadosFinais;
		draw();
	}
	
	private synchronized void draw(){
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, bfImage.getWidth(), bfImage.getHeight());			
		Integer node;
		Enumeration<Integer> p = positions.keys();
		while(p.hasMoreElements()){
			node = p.nextElement();
			drawConnections(node, allTransitions.get(node));
		}
		
		drawNodes(estadoAtual, estadoInicial, estadosFinais);
		
		getGraphics().drawImage(bfImage, 0, 0, null);
	}
	
	private void drawConnections(Integer node, List<TransicaoMultipla> transitions){
		int x1 = 0;
		int x2 = 0;
		int y1 = 0;
		int y2 = 0;
		Hashtable<Integer, Integer> toNode = new Hashtable<Integer, Integer>();
		int count = 0;
		for(TransicaoMultipla transition:transitions){
			if(transition == null)
				continue; //existe nó, porém não existe transicao
			try{
				count = (int) toNode.get(transition.getEstadoSucessor());
			}catch(NullPointerException e){
				count = 0;
			}
			finally{
					toNode.put(transition.getEstadoSucessor(), count++);
			}
			try{
				graphics.setColor(Color.BLACK);
				
				x1 = (int)positions.get(node).getX();
				x2 = (int)positions.get(transition.getEstadoSucessor()).getX();
				y1 = (int)positions.get(node).getY();
				y2 = (int)positions.get(transition.getEstadoSucessor()).getY();
				
				if(transition.getEstadoSucessor() == node){
					graphics.drawLine(x1-nodeRadious/2, y1-nodeRadious/2, x1-nodeRadious/2, y1-(int)(nodeRadious*1.5f));
	                graphics.drawLine(x1-nodeRadious/2, y1-(int)(nodeRadious*1.5f), x1+nodeRadious/2, y1-(int)(nodeRadious*1.5f));
	                graphics.drawLine(x1+nodeRadious/2, y1-nodeRadious/2, x1+nodeRadious/2, y1-(int)(nodeRadious*1.5f));
				}
				else{
					graphics.drawLine(x1, y1, x2, y2);
					drawArrow(x1,y1,x2,y2,5);
				}			
				drawText(node, transition, count);
			}catch(NullPointerException e){
				
			}
		}			
	}
	
	private void drawNodes(Integer estadoAtual, Integer estadoInicial, ArrayList<Integer> estadosFinais){
		Point point;
		String text;
		Integer node;
		Enumeration<Integer> p = positions.keys();
		while(p.hasMoreElements()){
			node = p.nextElement();
			point = positions.get(node);
			if(point.getX() + nodeRadious*2> bfImage.getWidth()){
				enlargeImage(50,0);
			}
			if(point.getY() + nodeRadious*2> bfImage.getHeight()){
				enlargeImage(0,50);
			}
			graphics.setColor(Color.BLACK);
			graphics.fillOval((int)point.getX() - nodeRadious, (int)point.getY() - nodeRadious,nodeRadious*2, nodeRadious*2);
			if(node != estadoAtual)
				graphics.setColor(nodeBackground);
			else
				graphics.setColor(nodeFocusBackground);
			
			graphics.fillOval((int)point.getX()+borderNodeSize - nodeRadious, (int)point.getY()+borderNodeSize - nodeRadious,
					nodeRadious*2-borderNodeSize*2, nodeRadious*2-borderNodeSize*2);
			
			if(estadosFinais.contains(node)){
				graphics.setColor(Color.BLACK);
				graphics.fillOval((int)point.getX() - Math.round(nodeRadious/1.2f), (int)point.getY() - Math.round(nodeRadious/1.2f),
						Math.round(nodeRadious*1.666667f), Math.round(nodeRadious*1.666667f));
				
				if(node != estadoAtual)
					graphics.setColor(nodeBackground);
				else
					graphics.setColor(nodeFocusBackground);
				
				graphics.fillOval((int)point.getX() + Math.round(borderNodeSize/1.2f) - Math.round(nodeRadious/1.2f), 
						(int)point.getY() + Math.round(borderNodeSize/1.2f) - Math.round(nodeRadious/1.2f),
						Math.round(nodeRadious*1.666667f) - Math.round(borderNodeSize*1.666667f), 
						Math.round(nodeRadious*1.666667f) - Math.round(borderNodeSize*1.666667f));
			}
			
			if(node == estadoInicial){
				graphics.setColor(Color.BLACK);
				graphics.drawLine((int)point.getX() - nodeRadious, (int)point.getY(), (int)point.getX() - 25 - nodeRadious, (int)point.getY() - 25);
				graphics.drawLine((int)point.getX() - nodeRadious, (int)point.getY(), (int)point.getX() - 25 - nodeRadious, (int)point.getY() + 25);
				graphics.drawLine((int)point.getX() - 25 - nodeRadious, (int)point.getY() - 25, (int)point.getX() - 25 - nodeRadious, (int)point.getY() + 25);
			}
			
			graphics.setColor(Color.BLACK);
			text = "q".concat(String.valueOf(node));
			graphics.drawString(text,
					(int)point.getX() - text.length()*(graphics.getFont().getSize()/2)/2,
					(int)point.getY() + graphics.getFont().getSize()/4);
		}		
	}
	
	private void drawText(Integer node, TransicaoMultipla transition, int count){
        int x1 = 0;
		int x2 = 0;
		int y1 = 0;
		int y2 = 0;

			x1 = (int)positions.get(node).getX();
			x2 = (int)positions.get(transition.getEstadoSucessor()).getX();
			y1 = (int)positions.get(node).getY();
			y2 = (int)positions.get(transition.getEstadoSucessor()).getY();
			
			if(node == transition.getEstadoSucessor())
				graphics.drawString(transition.toString(),
		        		(x1+x2)/2 - Math.round(transition.toString().length()*(graphics.getFont().getSize()/2)/2), 
		        		y1 - nodeRadious*1.5f - graphics.getFont().getSize()*count);
			
			else
		        graphics.drawString(transition.toString(),
		        		(x1+x2)/2 - Math.round(transition.toString().length()*(graphics.getFont().getSize()/2)/2), 
		        		(y1+y2)/2 - graphics.getFont().getSize()*count);
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

	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(1000/60);
				draw();		
			} catch (InterruptedException e) {
				e.printStackTrace();
			}catch(NullPointerException e){
				try { Thread.sleep(100); } catch (InterruptedException e1) {}
			}
		}
	}

	public void error() {
		nodeFocusBackground = Color.RED;
	}
	
	public void success(){
		nodeFocusBackground = Color.GREEN;
	}

}
