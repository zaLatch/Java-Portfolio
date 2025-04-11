package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class grid {

	winpe wp;
	mouseEvt mouseEvt;
	mousePos mousePos;
	validator vc;
	events evt;
	
	int sqrSize, x, y;
	int rX, rY;;
	int defaultX;
	int moves;
	int round = 0;
	int outcome = -1;
	int score0 = 0;
	int score1 = 0;
	boolean isGameDone = false;
	
	Font arial;
	String[] outcomeString = {"", "X wins", "O wins", "Draw"};
	Rectangle[] rect;
	Rectangle resetBtn, rbtnArea, gridArea;
	
	public grid(winpe wp, mouseEvt mouseEvt, mousePos mousePos) {
		this.wp = wp;
		this.mouseEvt = mouseEvt;
		this.mousePos = mousePos;
		vc = new validator(wp);
		evt = new events(wp);
		setDefaultValues();
	}
	
	public void setDefaultValues() {
		rect = new Rectangle[9];
		arial = new Font("Arial", Font.BOLD, 20);
	}
	
	public void update() {
		
		if(mousePos.gridHovered == true || mousePos.rbtnHovered) {
			wp.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}else {
			wp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		
		if(mouseEvt.click == true) {
			if(isGameDone == false) evt.handleSquareClicked();
			evt.resetGameState();
			outcome = vc.getOutcome();
		}
		
		if(outcome > -1) {
			isGameDone = true;
		}
		else if(moves == 9){
			isGameDone = true;
			outcome = 2;
		}
	}
	
	public void draw(Graphics2D g2) {
		
		g2.setFont(g2.getFont().deriveFont(20F));
		g2.setColor(new Color(63, 125, 88));
		g2.drawString("Practice Project by me :D", 5, 20);
		
		g2.setFont(g2.getFont().deriveFont(38F));
		g2.drawString("Tic-Tac-Toe (Current Move: "+outcomeString[evt.currentPlayer+1].replace(" wins", "")+")", 20, 60);
		g2.drawString("Round-"+round+", Outcome: "+outcomeString[outcome+1], 20, 100);
		g2.drawString("Scores: X-"+score0+", O-"+score1, 20, 136);
		
		drawGrid(g2);
		drawResetBtn(g2);
		drawMoves(g2);
	}
	
	public void drawGrid(Graphics2D g2) {
		
		// Declare values
		final int rows = 3;
		final int cols = 3;
		int index = 0;
		
		sqrSize = wp.size * 2;
		x = wp.size/2*9;
		y = wp.size*4;
		defaultX = x;
		
		// Draw the grid's full area
		gridArea = new Rectangle();
		gridArea.x = wp.size/2*9;
		gridArea.y = wp.size*4;
		gridArea.width = 289;
		gridArea.height = 289;
		
		
		// Draw the tile and frame
		for(int i = 0; i < rows;) {
			for(int j = 0; j < cols;) {
				
				if(index%2 == 0) {
					g2.setColor(new Color(91, 145, 59));
					rect[index] = new Rectangle();
					rect[index].x = x;
					rect[index].y = y;
					rect[index].height = sqrSize;
					rect[index].width = sqrSize;
				}else {
					g2.setColor(new Color(119, 178, 84));
					rect[index] = new Rectangle();
					rect[index].x = x;
					rect[index].y = y;
					rect[index].height = sqrSize;
					rect[index].width = sqrSize;
				}
				
				g2.fill(rect[index]);
				
				g2.setStroke(new BasicStroke(4));
				g2.setColor(new Color(239, 239, 239));
				g2.drawRect(x, y, sqrSize, sqrSize);
				
				x += wp.size*2;
				j++;
				index++;
			}
			x = defaultX;
			y += wp.size/2*4;
			i++;
		}
		g2.setColor(new Color(217, 157, 129));
		g2.setStroke(new BasicStroke(4));
		g2.drawRect((int) gridArea.getX()-3, (int) gridArea.getY()-3, (int) gridArea.getWidth()+5, (int) gridArea.getHeight()+5);
		
	}

	public void drawResetBtn(Graphics2D g2) {
		
		x = wp.size/2*13;
		y = wp.size/2*21;
		
		rbtnArea = new Rectangle();
		rbtnArea.x = x;
		rbtnArea.y = y;
		rbtnArea.width =  wp.size*2;
		rbtnArea.height = 30;
		
		// Reset Button changes design when mouse hitbox hovers above it
		if(mousePos.rbtnHovered == true) {
			g2.setColor(new Color(198, 46, 46));
			g2.setFont(arial);
			
			if(mouseEvt.click == true) {
				g2.fill3DRect(x, y, wp.size*2, 30, false);
				g2.setColor(Color.white.darker());
				g2.drawString("Reset", x+22, y+22);
			}else {
				g2.fill3DRect(x, y, wp.size*2, 30, true);
				g2.setColor(Color.white.brighter());
				g2.drawString("Reset", x+22, y+22);
			}
			
			
		}else {
			g2.setColor(new Color(249, 84, 84));
			
			g2.setFont(arial);
			g2.fill3DRect(x, y, wp.size*2, 30, true);
			
			g2.setColor(Color.white);
			g2.drawString("Reset", x+22, y+22);
		}
		
	}
	
	public void drawMoves(Graphics2D g2) {
		
		if(!evt.clickedSquares.isEmpty()) {
			for(int i = 0; i < evt.clickedSquares.size(); i++) {
				
				/* Get the X,Y coordinates by:
				 * Finding the corresponding square that was clicked (in events class)
				 * Add it to the square list, Which we iterate through (events class to grid class)
				 * and get the index value
				*/
				rX = (int) rect[evt.clickedSquares.get(i)].getX()+28;
				rY = (int) rect[evt.clickedSquares.get(i)].getY()+70;
				
				g2.setFont(arial.deriveFont(Font.BOLD ,63F));
				g2.setColor(Color.black);
				
				/* After finding the x,y coordinates.
				 * We will draw the X or O, and position it
				 * by the correct X and Y coords
				 */
				g2.setColor(Color.white);
				if(evt.playerMoves.get(i) == 0) {
					g2.drawString("X", rX, rY);
				}
				else if(evt.playerMoves.get(i) == 1) {
					g2.drawString("O", rX-3, rY);
				}
			}
		}
	}
}
