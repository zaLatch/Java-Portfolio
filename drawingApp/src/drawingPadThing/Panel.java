package drawingPadThing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

class Panel extends JPanel implements Runnable{
	
	final int gridSize = 50;
	final int maxScrRow = 13;
	final int maxScrCol = 17;
	final int screenWidth = gridSize*maxScrCol;
	final int screenHeight = gridSize*maxScrRow;
	
	final int FPS = 120;
	MouseEvt mouseEvt = new MouseEvt(this);
	UI_handler drawPad = new UI_handler(this, mouseEvt);
	Thread thread;
	
	// From stackOverFlow: https://stackoverflow.com/questions/1984071/how-to-hide-cursor-in-a-swing-application
	BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	Cursor drawCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
	// End
	
	protected Panel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setDoubleBuffered(true);
		this.addMouseListener(mouseEvt);
		this.addMouseMotionListener(mouseEvt);
		this.addMouseWheelListener(mouseEvt);
		this.setFocusable(true);
	}
	
	static final String ANSI_RED = "\u001B[31m";
    
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		drawPad.draw(g2d);
		
		
		g2d.setColor(Color.black);
		
		if(drawPad.drawingArea.contains(mouseEvt.point)) 
		{
			this.setCursor(drawCursor);
			mouseEvt.drawCursor(g2d);
		}
		else if(drawPad.paletteArea.contains(mouseEvt.point) || drawPad.clearBtn.contains(mouseEvt.point)) 
		{
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		else {
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		
		
		g2d.dispose();
	}
	
	private void update() {
		drawPad.update();
	}
	
	protected void startThread() {
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		// From RYI-Snow (Delta Game Loop)
		System.out.println("Running..");
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while(thread != null)
		{
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime)/drawInterval;
			lastTime = currentTime;
		            
			if(delta >= 1) 
			{
				update();
				delta--;
			}
			
			repaint();
		}
	}
}
