package drawingPadThing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

class UI_handler {

	Panel p;
	MouseEvt mouseEvt;
	EventHandler evtHandler;
	
	Rectangle[] pixels, colorPaletteSelection;
	Rectangle drawingArea, paletteArea, clearBtn, arrowLeft, arrowRight; 
	int paletteSize = 30;
	int gridSize, strokeThickness;
	
	protected int maxRow, maxCol;
	float[] valuePerPixel;
	float currentPixelColorValue;
	List<Integer> clickedPixel = new ArrayList<>();
	List<Integer> action = new ArrayList<>();
	
	int bottomX = 50;
	int bottomY = 0;
	
	// Chat GPT:
	BufferedImage canvasImage;
	Graphics2D canvasGraphics;
	// End
	
	protected UI_handler(Panel p, MouseEvt mouseEvt) {
		this.p = p;
		this.mouseEvt = mouseEvt;
		setDefaults();
	}
	
	private void setDefaults() {
		evtHandler = new EventHandler(this);
		drawingArea = new Rectangle(0, 0, p.screenWidth, p.screenHeight-110);
		gridSize = 10;
		strokeThickness = 3; // Default stroke thickness
		currentPixelColorValue = 1.00F;
		
		maxRow = (int) drawingArea.getHeight()/gridSize;
		maxCol = (int) drawingArea.getWidth()/gridSize;
		
		colorPaletteSelection = new Rectangle[4];
		pixels = new Rectangle[maxCol*maxRow];
		valuePerPixel = new float[maxCol*maxRow];
		
		// Chat GPT:
		canvasImage = new BufferedImage(drawingArea.width, drawingArea.height, BufferedImage.TYPE_INT_ARGB);
		canvasGraphics = canvasImage.createGraphics();
		canvasGraphics.setBackground(new Color(0, 0, 0, 0)); 
		// End
	}
	
	private void drawGridOfPixels(Graphics2D g2d) {
		int index = 0;
		int gridX = 0;
		int gridY = 0;
		int maxOfX = 0;
		
		for(int i = 0; i < maxRow; i++) 
		{
			for(int j = 0; j < maxCol; j++) 
			{
				pixels[index] = new Rectangle(gridX, gridY, gridSize, gridSize);
				gridX += gridSize;
				index++;
			}
			
			if(i+1 == maxRow) 
			{
				maxOfX = gridX;
			}
			
			gridY += gridSize;
			gridX = 0;
		}
		
		g2d.drawLine(gridX, gridY, maxOfX, gridY);
		bottomY = gridY+10;
	}
	
	private void drawPixels(Graphics2D g2d) {
//		if(!clickedPixel.isEmpty()) 
//		{
//			for(int i = 0; i < clickedPixel.size(); i++) 
//			{
//				int  pX = (int) pixels[clickedPixel.get(i)].getX();
//				int pY = (int) pixels[clickedPixel.get(i)].getY();
//				float valueOnPixel = valuePerPixel[clickedPixel.get(i)];
//				
//				if(evtHandler.valuePerColor.contains(valuePerPixel[clickedPixel.get(i)])) 
//				{
//					int indexOf = evtHandler.valuePerColor.indexOf(valueOnPixel);
//					g2d.setColor(evtHandler.availableColors.get(indexOf));
//					g2d.fillRect(pX, pY, gridSize, gridSize);
//				}
//			}
//		}
		g2d.drawImage(canvasImage, 0, 0, null);
	}
	
	private void drawClearButton(Graphics2D g2d) {
		clearBtn = new Rectangle((int) (paletteArea.getX()-90), bottomY, 70, 40);
		Color buttonColor = Color.gray;
		String text = "Clear";
		
		int x = (int) clearBtn.getX();
		int y = (int) clearBtn.getY();
		int textScrX = (int) (clearBtn.getCenterX()-text.length()*3);
		int textScrY = (int) clearBtn.getY()+25;
		int width = (int) clearBtn.getWidth();
		int height = (int) clearBtn.getHeight();

		if(clearBtn.contains(mouseEvt.point)) 
		{
			g2d.setColor(buttonColor);
			g2d.fill3DRect(x, y, width, height, true);
			
			g2d.setColor(Color.white.darker());
			g2d.drawString(text, textScrX, textScrY);
			
			if(mouseEvt.isMouseLeftClicked && clearBtn.contains(mouseEvt.point))
			{
				g2d.setColor(buttonColor.darker());
				g2d.fill3DRect(x, y, width, height, false);
					
				g2d.setColor(Color.white.darker());
				g2d.drawString(text, textScrX, textScrY);
				evtHandler.clear();
			}
		}else 
		{
			g2d.setColor(buttonColor.brighter());
			g2d.fill3DRect(x, y, width, height, true);
			
			g2d.setColor(Color.black);
			g2d.drawString(text, textScrX, textScrY);
		}
	}
	
	private void drawColorPalette(Graphics2D g2d) {
		int x = (drawingArea.width-300)/2;
		int defaultX = x;
		int y = bottomY;
		int i = 0, j = 0, index = 0;
		int amount = paletteSize-20;
		
		paletteArea = new Rectangle(x-8, y-8, 300+8, 20*5);
		colorPaletteSelection = new Rectangle[paletteSize];
		
		g2d.setColor(Color.darkGray);
		g2d.fill(paletteArea);
		
		// Nothing special just a change from For-Loop to While
		while(i < 3) 
		{
			while(j < amount) 
			{
				boolean paletteNotNull = evtHandler.colorPalette[index] != null;
				
				if(paletteNotNull == true) {
					Color color = evtHandler.colorPalette[index] != null ? evtHandler.colorPalette[index]:Color.black;
					colorPaletteSelection[index] = new Rectangle();
					colorPaletteSelection[index].setFrame(x, y, 20, 20);
					
					g2d.setColor(color);
					g2d.fill(colorPaletteSelection[index]);
				}else {
					g2d.setColor(Color.white);
					g2d.drawRect(x, y, 20, 20);
				}
				index++;
				x += 30;
				j++;
			}
			x = defaultX;
			y += 30;
			i++;
			j = 0;
		}
		
		if(mouseEvt.isMouseLeftClicked && paletteArea.contains(mouseEvt.point)) 
		{
			evtHandler.handlePalette();
			mouseEvt.isMouseLeftClicked = false;
		}
	}

	long drawStart = 0;
    long drawEnd;
    long passed;
    public String drawFPS;
    
	protected void draw(Graphics2D g2d) {
		drawStart = System.nanoTime();
		drawGridOfPixels(g2d);
		drawPixels(g2d);
		drawColorPalette(g2d);
		drawClearButton(g2d);
		
		drawEnd = System.nanoTime();
		passed = drawEnd - drawStart;
		drawFPS = String.valueOf(Math.abs(passed)).substring(0, 2);
		
		System.out.println(Integer.parseInt(drawFPS) <= 40 ? "GOOD FPS"+drawFPS:"BAD FPS");
	}
	
	protected void update() {
		if(mouseEvt.isMouseLeftClicked) 
		{
			evtHandler.handleDrawing();
		}
		if(mouseEvt.isMouseRightClicked) 
		{
			evtHandler.handleErasing();
		}
		
		evtHandler.setScrollStrokeThickness();
	}
}