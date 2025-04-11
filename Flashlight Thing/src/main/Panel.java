package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import objects.*;

@SuppressWarnings("serial")
public class Panel extends JPanel implements Runnable{

	final int tile = 16;
	final int scale = 3;
	
	public final int scaledTile = tile*scale;
	final int maxScrRow = 15;
	final int maxScrCol = 20;
	
	public final int width = maxScrCol*scaledTile;
	public final int height = maxScrRow*scaledTile;
	
	int FPS = 60;
	
	// From stackOverFlow: https://stackoverflow.com/questions/1984071/how-to-hide-cursor-in-a-swing-application
	BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
	// End
	UTools.image imageScale = new UTools.image();
	UTools.graphics gt = new UTools.graphics(this);
	IO.keyEvt keyEvt = new IO.keyEvt();
	IO.mouseEvt mouseEvt = new IO.mouseEvt(this);
	flashlight flashlight = new flashlight(this, gt, mouseEvt, keyEvt);
	Thread thread;
	
	
	public Panel() {
		this.setBackground(new Color(65, 68, 75));
		this.setPreferredSize(new Dimension(width, height));
		this.setDoubleBuffered(true);
		this.addMouseMotionListener(mouseEvt);
		this.addMouseWheelListener(mouseEvt);
		this.addKeyListener(keyEvt);
		this.setCursor(blankCursor);
		this.setFocusable(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		flashlight.draw(g2);
		
		g2.setColor(Color.green.brighter());
		g2.dispose();
	}
	
	public void update() {
		flashlight.update();
	}
	
	public void setupGame() {
		
	}
	
	public void startThread() {
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		System.out.println("Running");
		double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        
        while(thread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval;
            lastTime = currentTime;
            
            if(delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
	}

}
