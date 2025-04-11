package main;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UTools {
	
	public static class image {
		
		// Returns the scaled image of the original image (Not usable by the user)
		private BufferedImage scaleImage(BufferedImage origImage, int Width, int Height) {
			
			BufferedImage scaledImage = new BufferedImage(Width, Height, origImage.getType());
			Graphics2D g2 = scaledImage.createGraphics();
			g2.drawImage(origImage, 0, 0, Width, Height, null);
			g2.dispose();
			
			return scaledImage;
		}
		
		// Lock the image ratio 1:1
		public BufferedImage getImage(String imagePath, int LockSizeAt) {
			
			BufferedImage Image = null;
			
			try {
				Image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
				Image = scaleImage(Image, LockSizeAt, LockSizeAt);
				
			}catch (IOException e) {
				e.printStackTrace();
			}
			return Image;
		}
		
		// Set your own image ratio
		public BufferedImage getImage(String imagePath, int Height, int Width) {
			
			BufferedImage Image = null;
			
			try {
				Image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
				Image = scaleImage(Image, Width, Height);
				
			}catch (IOException e) {
				e.printStackTrace();
			}
			return Image;
		}
	}
	
	public static class graphics {
		
		Panel p;
		
		public graphics(Panel p) {
			this.p = p;
		}
		
		public void changeOpacity(Graphics2D g2, float opacity) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		}
	}
}
