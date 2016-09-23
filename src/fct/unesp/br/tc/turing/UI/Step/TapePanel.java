package fct.unesp.br.tc.turing.UI.Step;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JPanel;

public class TapePanel extends JPanel implements Runnable, ComponentListener, MouseListener{
	
	private static final long serialVersionUID = 6584093280292832495L;

	private BufferedImage bfImage;
	private Graphics2D graphics;
	private Color backgroundColor;
	private String fita;
	private int pos;
	private int selectedPos = -1;
	private int count;
	private Rectangle button;
        private boolean fitaEntrada;
        
	public TapePanel(String fita, boolean fitaEntrada){
		super();
                this.fitaEntrada = fitaEntrada;
		init();
		initComponents();
		setVisible(true);
		this.fita = fita;
	}

	private void initComponents() {
		bfImage = new BufferedImage(500, 30, BufferedImage.TYPE_INT_RGB);
		graphics = (Graphics2D) bfImage.getGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	private void init() {
		setBackground(Color.WHITE);
		backgroundColor = new Color(240,240,240);
		setMinimumSize(new Dimension(500, 30));
		setPreferredSize(new Dimension(500,30));
		addComponentListener(this);
		pos = 0;
		button = new Rectangle(5, 5, 20, 20);
                if(fitaEntrada)
                    addMouseListener(this);
	}
	
	private synchronized void draw() throws NullPointerException{
		
		graphics.setColor(backgroundColor);
		graphics.fillRect(0, 0, bfImage.getWidth(), bfImage.getHeight());
		graphics.setColor(Color.DARK_GRAY);
		graphics.fillRect(5, 5, 20, bfImage.getHeight()-10);
		graphics.setColor(Color.WHITE);
		graphics.fillRect(25, 5, bfImage.getWidth()-bfImage.getWidth()%20, bfImage.getHeight()-10);
		graphics.setColor(Color.GRAY);
		
		for(int i = 0; i < bfImage.getWidth()-20; i+=20)
			graphics.drawRect(5 + i, 5, 20,  bfImage.getHeight()-10);
		if(selectedPos == -1){
			selectedPos = (bfImage.getWidth()-20)/2;
			while(selectedPos%20 != 0)
				selectedPos++;
		}
		graphics.setColor(Color.RED);
		graphics.drawRect(5 + selectedPos, 5, 20,  bfImage.getHeight()-10);
		graphics.setColor(Color.BLACK);
		
		count = pos;
		for(int i = selectedPos; i < bfImage.getWidth()-20 && count < fita.length();i+=20){
                    try{
			graphics.drawString(String.valueOf(fita.charAt(count)), 5 + 6 + i, 5 + 3 + 12);
                    }catch(Exception e){
                        graphics.drawString(" ", 5 + 6 + i, 5 + 3 + 12);
                    }
			count++;
		}
		
		count = pos - 1;
                String txt = "";
		for(int i = selectedPos - 20; i > 0 && count >= 0;i-=20){
                        try{
                            txt = String.valueOf(fita.charAt(count));
                        }catch(Exception e){
                            txt = " ";
                        }
			graphics.drawString(txt, 5 + 6 + i, 5 + 3 + 12);
			count--;
		}
		
                if(fitaEntrada){ 
                    graphics.setColor(Color.GREEN);
                    graphics.fillPolygon(new int[]{6+5,6+5,15+5}, new int[]{6+5,16+5,11+5}, 3);
                    
                }
                    
		
		getGraphics().drawImage(bfImage, 0, 0, null);
		
	}
	
	public void move(int movement){
		pos += movement;
	}
	
	public void error(){
		backgroundColor = Color.RED;
	}
	
	public void success(){
		backgroundColor = Color.GREEN;
	}
	
	public void updateFita(List<Character> fita){
            this.fita = "";
            for(Character ch:fita)
                this.fita += ch;
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

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void componentResized(ComponentEvent arg0) {
		bfImage = new BufferedImage((int)getSize().getWidth(), (int)getSize().getHeight(), BufferedImage.TYPE_INT_RGB);
		graphics = (Graphics2D) bfImage.getGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		selectedPos = -1;
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(button.intersects(new Rectangle(e.getX(), e.getY(),1,1))){
			((Frame)getTopLevelAncestor()).nextStep();
		}
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
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
