package drawingPadThing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Ellipse2D;

class MouseEvt implements MouseListener, MouseMotionListener, MouseWheelListener{
	
	Panel p;
	Rectangle cursorArea = new Rectangle();
	Ellipse2D cursor = new Ellipse2D.Double();
	
	Point point = new Point();
	boolean isMouseRightClicked, isMouseLeftClicked;
	boolean zoomIn, zoomOut;
	int x, y, width, height;
	int cursorX, cursorY;
	int mouseCurrentX, mouseCurrentY;
	int mousePrevX;
	int pointSize;
	
	protected MouseEvt(Panel p) {
		this.p = p;
	}
	
	protected void drawCursor(Graphics2D g2d) {
		pointSize = p.drawPad.strokeThickness;
		cursor.setFrame(cursorX, cursorY, pointSize, pointSize);
		
		x = (int) (cursor.getCenterX()-pointSize/2);
		y = (int) (cursor.getCenterY()-pointSize/2);
		width = (int) cursor.getWidth();
		height = (int) cursor.getHeight();
		
		g2d.setColor(p.mouseEvt.isMouseRightClicked ? Color.red:Color.gray);
		g2d.fill(cursor);
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawString(String.valueOf(p.drawPad.strokeThickness-2), x+13, y);
	}
	private void setArea() {
		cursorArea.x = x;
		cursorArea.y = y;
		cursorArea.width = width;
		cursorArea.height = height;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// For pixel
		mouseCurrentX = e.getX();
		mouseCurrentY = e.getY();
		
		// For cursor
		cursorX = e.getX();
		cursorY = e.getY();
		
		point = e.getPoint();
		setArea();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		cursorX = e.getX();
		cursorY = e.getY();
		
		point = e.getPoint();
		setArea();
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getPreciseWheelRotation() < 1) 
		{
			zoomIn = true;
		}else 
		{
			zoomOut = true;
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) 
		{
			isMouseLeftClicked = true;
		}
		if(e.getButton() == 3) 
		{
			isMouseRightClicked = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		isMouseRightClicked = false;
		isMouseLeftClicked = false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
