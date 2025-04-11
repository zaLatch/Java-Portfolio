package main;

import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class mousePos implements MouseMotionListener {
	
	winpe wp;
	Rectangle hitbox = new Rectangle();
	int x, y;
	boolean gridHovered, rbtnHovered;

	public mousePos(winpe wp) {
		this.wp = wp;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// Offset by a bit to avoid hitting two squares at once
		x = e.getX()+7;
		y = e.getY()+8;
		
		hitbox.x = x;
		hitbox.y = y;
		hitbox.width = 2;
		hitbox.height = 2;
		
		if(wp.grid.rbtnArea.intersects(hitbox)) {
			rbtnHovered = true;
		}
		else if(wp.grid.gridArea.intersects(hitbox)) {
			gridHovered = true;
		}
		else {
			rbtnHovered = false;
			gridHovered = false;
		}
	}

}
