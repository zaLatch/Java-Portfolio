package main;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class window extends JFrame{
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		winpe wp = new winpe();
		
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setTitle("tiki taki toe");
		frame.add(wp);
		frame.pack();
		frame.setResizable(false);
		
		frame.setVisible(true);
		frame.setIconImage(wp.animeLogo);
		frame.setLocationRelativeTo(null);
		
		wp.startThread();
	}
}