package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class winpe extends JPanel implements Runnable {

	final int  size = 48;
	
	final int maxScrCol = 15;
	final int maxScrRow = 15;
	
	final int scrWidth = maxScrCol * size;
	final int scrHeight = maxScrRow * size;
	
	int FPS = 60;
	BufferedImage animeLogo;
	
	mouseEvt m = new mouseEvt(this);
	mousePos mp = new mousePos(this);
	grid grid = new grid(this, m, mp);
	Thread thread;
	
	public winpe() {
		this.setPreferredSize(new Dimension(scrWidth, scrHeight));
		this.setBackground(new Color(239, 239, 239));
		this.setDoubleBuffered(true);
		this.addMouseListener(m);
		this.addMouseMotionListener(mp);
		this.setFocusable(true);
		setImages();
	}
	
	public void setImages() {
		try {
			animeLogo = ImageIO.read(getClass().getResourceAsStream("/res/girl4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startThread() {
		thread = new Thread(this);
		thread.start();
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		grid.draw(g2);
		g2.dispose();
	}
	
	public void update() {
		grid.update();
	}
	
	public void run() {
		
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
