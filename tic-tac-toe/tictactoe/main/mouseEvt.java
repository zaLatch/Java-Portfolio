package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class mouseEvt implements MouseListener{
	
	winpe wp;
	boolean click;

	public mouseEvt(winpe wp) {
		this.wp = wp;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if(e.getButton() == 1 && wp.grid.gridArea.intersects(wp.mp.hitbox) || wp.grid.rbtnArea.intersects(wp.mp.hitbox)) {
			click = true;
		}
			
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		click = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

}
