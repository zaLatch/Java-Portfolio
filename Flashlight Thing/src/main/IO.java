package main;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class IO {
	
	public static class mouseEvt implements MouseListener, MouseMotionListener, MouseWheelListener {

		Panel p;
		
		public boolean zoomIn, zoomOut;
		public int mouseCurrentX, mouseCurrentY;
		
		
		public mouseEvt(Panel p) {
			this.p = p;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			mouseCurrentX = (int) (e.getX() - p.flashlight.flashlight_outer.getWidth() / 2);
			mouseCurrentY = (int) (e.getY() - p.flashlight.flashlight_outer.getHeight() / 2);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mouseCurrentX = (int) (e.getX() - p.flashlight.flashlight_outer.getWidth() / 2);
			mouseCurrentY = (int) (e.getY() - p.flashlight.flashlight_outer.getHeight() / 2);
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			if(e.getPreciseWheelRotation() < 1) {
				zoomIn = true;
			}else {
				zoomOut = true;
			}
		}
		
		
	}
	
	public static class keyEvt implements KeyListener {
		
		boolean zoomIn, zoomOut;

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
//			int code = e.getKeyCode();
//			
//			if(code == KeyEvent.VK_E) {
//				zoomIn = true;
//			}
//			if(code == KeyEvent.VK_Q) {
//				zoomOut = true;
//				System.out.println("pressed");
//			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
		
	}
		
}
