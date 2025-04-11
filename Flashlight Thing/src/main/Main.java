package main;


import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Main extends JFrame{
	
	public static class icon {
		BufferedImage Icon;
		UTools.image image = new UTools.image();
		
		public BufferedImage setIcon() {
			return Icon;
		}
		
	}
	
	public static void main(String[] args) {
		JFrame window = new JFrame();
		Panel panel = new Panel();
		
		window.add(panel);
		window.pack();
		
		window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		window.setTitle("Flashlight testing");
		
		panel.startThread();
		
	}
}
