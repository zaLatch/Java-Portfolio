package objects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import main.*;

public class flashlight {
	
	Panel p;
	UTools.graphics gt;
	IO.mouseEvt mouseEvt;
	IO.keyEvt keyEvt;
	int x, y, barY;
	int ratio, maxRatio, minRatio, addPerZoom;
	int batteryCounter;
	
	Rectangle bgBar, indicator;
	public Ellipse2D flashlight_outer, flashlight_middle;
	
	
	public flashlight(Panel p, UTools.graphics gt, IO.mouseEvt mouseEvt, IO.keyEvt keyEvt) {
		this.p = p;
		this.gt = gt;
		this.mouseEvt = mouseEvt;
		this.keyEvt = keyEvt;
		defaultValues();
	}
	
	public void defaultValues() {
		// Declare sizes
		ratio = 200; // 1:1
		maxRatio = 300;
		minRatio = 100;
		addPerZoom = 20;
		
		x = (p.width-ratio)/2;
		y = (p.height-ratio)/2;
		
		// Instatiate the size bar and declare the starting point of the indicator (barY)
		indicator = new Rectangle();
		bgBar = new Rectangle((p.width-30-10), (p.height-ratio-20)/2, 30, 120);
		barY = (p.height-ratio+100)/2;
		
		// Instatiate the flashlight shape
		flashlight_outer = new Ellipse2D.Double();
		flashlight_middle =  new Ellipse2D.Double();
		
	}
	
	public void drawBar(Graphics2D g2) {
		final int x = (p.width-bgBar.width-10);
		indicator.setFrame(x-5, barY, 40, 5);
		
		// Bar
		gt.changeOpacity(g2, 0.50F);
		g2.setColor(Color.gray);
		g2.fill(bgBar);
		
		// Indicator
		gt.changeOpacity(g2, 0.80F);
		g2.setColor(Color.green);
		g2.fill(indicator);
	}
	
	public void drawFlashLight(Graphics2D g2) {
		
		// Instatiate the frame
		flashlight_outer.setFrame(x, y, ratio, ratio);
		flashlight_middle.setFrame(x+p.scaledTile/2, y+p.scaledTile/2, ratio-p.scaledTile, ratio-p.scaledTile);
		
		// Outer light
		g2.setColor(new Color(248, 218, 119));
		gt.changeOpacity(g2, 0.90F);
		g2.fill(flashlight_outer);
		
		// Middle light
		g2.setColor(new Color(243, 210, 101));
		gt.changeOpacity(g2, 0.60F);
		g2.fill(flashlight_middle);
		
		gt.changeOpacity(g2, 1F);
	}

	
	public void draw(Graphics2D g2) {
		
		drawFlashLight(g2);
		drawBar(g2);
		gt.changeOpacity(g2, 1F);
		
		g2.setColor(new Color(65, 68, 75));
		g2.drawString("Hello, You found this text", 100, 100);
	}
	
	public void update() {
//		batteryCounter++;
		
		// Check if mouse scroll is active
		if(mouseEvt.zoomIn || mouseEvt.zoomOut) {
			
			if(mouseEvt.zoomIn) {
				if(ratio >= maxRatio) {
					ratio = maxRatio;
					ratio = maxRatio;
				}
				else {
					// Shift the position in the middle
					y -= addPerZoom*2/2;
					x -= addPerZoom*2/2;
					
					// Just adding sizes
					ratio += addPerZoom;
					ratio += addPerZoom;
					barY -= addPerZoom;
				}
			}
			
			
			else if(mouseEvt.zoomOut) {
				if(ratio <= minRatio) {
					ratio = minRatio;
					ratio = minRatio;
				}else {
					// Same as before
					y += addPerZoom*2/2;
					x += addPerZoom*2/2;
					ratio -= addPerZoom;
					ratio -= addPerZoom;
					barY += addPerZoom;
				}
			}
			mouseEvt.zoomIn = false;
			mouseEvt.zoomOut = false;
		}
		
//		if(batteryCounter >= 120) {
//			batteryCounter = 0;
//		}

		x = mouseEvt.mouseCurrentX;
		y = mouseEvt.mouseCurrentY;
	}
}
