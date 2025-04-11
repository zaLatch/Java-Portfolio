package drawingPadThing;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class EventHandler {
	UI_handler ui;
	
	List<Color> availableColors = Arrays.asList(Color.black, Color.red, Color.green, Color.blue, Color.magenta, Color.yellow,
												Color.orange, Color.pink, Color.cyan);
	Color[] colorPalette;
	List<Float> valuePerColor = new ArrayList<>();
	
	protected EventHandler(UI_handler ui) {
		this.ui = ui;
		setColorsPixelValue();
		setColorPalette();
	}
	
	protected void setColorPalette() {
		colorPalette = new Color[ui.paletteSize];
		
		for(int i = 0; i < availableColors.size(); i++) 
		{
			colorPalette[i] = availableColors.get(i);
		}
	}
	
	protected void setColorsPixelValue() {
		float startValue = 1.0F;
		
		for(int i = 0; i < availableColors.size(); i++) 
		{
			valuePerColor.add(startValue);
			startValue +=.01F;
		}
	}
	
	protected void handlePalette() {
		for(int i = 0; i < ui.colorPaletteSelection.length; i++) 
		{

			if(ui.colorPaletteSelection[i] != null && ui.colorPaletteSelection[i].contains(ui.mouseEvt.point)) 
			{
				float colorValue = valuePerColor.get(i);
				ui.currentPixelColorValue = colorValue;
			} 
		}
	}
	
	// Chat GPT (chat gpt didnt make it with the off-screen rendering step by step guide. i was just tasked to do it:
	protected Color getColorFromValue(float colorValue) {
		int index = valuePerColor.indexOf(colorValue);
		Color colorFromValue = availableColors.get(index);
		
		return colorFromValue;
	}
	// End
	
	protected void handleDrawing() {
//		for(int i = 0; i < ui.pixels.length; i++) 
//		{
//			if(ui.drawingArea.contains(ui.mouseEvt.point) && ui.pixels[i].intersects(ui.mouseEvt.cursorArea)) 
//			{
//				ui.clickedPixel.add(i);
//				ui.valuePerPixel[i] = ui.currentPixelColorValue;
//			}
//		}
		
		// Chat gpt:
		
		// Calculate the column and row based on the mouse's x and y position, divided by grid size
		int col = ui.mouseEvt.point.x / ui.gridSize;  // The column index based on mouse's horizontal position
		int row = ui.mouseEvt.point.y / ui.gridSize;  // The row index based on mouse's vertical position

		// Calculate half of the stroke thickness divided by 10 (likely to create a more precise or smaller stroke area)
		int half = ui.strokeThickness / 20;  // This determines the range of pixels that will be affected by the stroke

		// Loop through the area surrounding the current pixel to apply the stroke effect
		for (int dy = -half; dy <= half; dy++) {  // Loop through vertical range (dy: up and down)
		    for (int dx = -half; dx <= half; dx++) {  // Loop through horizontal range (dx: left and right)
		        
		        // Calculate the position of the current pixel to be affected by the stroke, adding the offset (dx, dy)
		        int drawCol = col + dx;  // Calculate the column index by adding the horizontal offset (dx)
		        int drawRow = row + dy;  // Calculate the row index by adding the vertical offset (dy)
		        
		        // Check if the calculated pixel position is within the bounds of the drawing area
		        if (drawCol >= 0 && drawCol < ui.maxCol && drawRow >= 0 && drawRow < ui.maxRow && ui.drawingArea.contains(ui.mouseEvt.point)) {
		            
		            // Calculate the index of the pixel in the grid using row and column
		            int drawIndex = drawRow * ui.maxCol + drawCol;

		            // Add the pixel index to the list of clicked pixels to keep track of which pixels are being drawn
		            ui.clickedPixel.add(drawIndex);
		            
		            // Assign the current color value to the pixel in the grid
		            ui.valuePerPixel[drawIndex] = ui.currentPixelColorValue;

		            // Get the actual Color object based on the current pixel color value
		            Color color = getColorFromValue(ui.currentPixelColorValue);  // Map the current color value to a Color object
		            
		            // Set the drawing color to the calculated color
		            ui.canvasGraphics.setColor(color);
		            
		            // Fill the rectangle at the calculated position, using grid size to determine the pixel size
		            ui.canvasGraphics.fillRect(drawCol * ui.gridSize, drawRow * ui.gridSize, ui.gridSize, ui.gridSize);
		            // The fillRect method draws the filled rectangle at the specific (x, y) position,
		            // using the grid size for each pixel's width and height.
		        }
		    }
		}

		// End

	}
	
	protected void handleErasing() {
//		for(int i = 0; i < ui.pixels.length; i++) 
//		{
//			if(ui.drawingArea.contains(ui.mouseEvt.point) && ui.pixels[i].intersects(ui.mouseEvt.cursorArea) 
//			   && ui.clickedPixel.contains(i)) 
//			{
//				ui.clickedPixel.remove(ui.clickedPixel.indexOf(i));
//				ui.valuePerPixel[i] = 0;
//			}
//	
//		}
		
		// Same thing but just erasing
		int col = ui.mouseEvt.point.x / ui.gridSize; 
		int row = ui.mouseEvt.point.y / ui.gridSize;  

		int half = ui.strokeThickness / 20;  
				
		for (int dy = -half; dy <= half; dy++) {
			for (int dx = -half; dx <= half; dx++) {  
				        
				int drawCol = col + dx; 
				int drawRow = row + dy;  
				        
				if (drawCol >= 0 && drawCol < ui.maxCol && drawRow >= 0 && drawRow < ui.maxRow && ui.drawingArea.contains(ui.mouseEvt.point)) {
				            
					int drawIndex = drawRow * ui.maxCol + drawCol;

					ui.clickedPixel.add(drawIndex);
				            
					ui.valuePerPixel[drawIndex] = ui.currentPixelColorValue;
				            
					ui.canvasGraphics.clearRect(drawCol * ui.gridSize, drawRow * ui.gridSize, ui.gridSize, ui.gridSize);
				}
			}
		}
				// End
	}
	
	protected void setScrollStrokeThickness() {
		if(ui.mouseEvt.zoomIn == true) 
		{
			ui.strokeThickness += 1;
		}
		if(ui.mouseEvt.zoomOut == true && ui.strokeThickness >= 4) 
		{
			ui.strokeThickness -=1;
		}
		
		ui.mouseEvt.zoomIn = false;
		ui.mouseEvt.zoomOut = false;
	}
	
	protected void clear() {
		ui.clickedPixel.clear();
//		ui.valuePerPixel = new float[ui.maxCol*ui.maxRow];
		ui.canvasGraphics.clearRect(0, 0, ui.canvasImage.getWidth(), ui.canvasImage.getHeight());
	}
}
