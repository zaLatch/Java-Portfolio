package drawingPadThing;

import javax.swing.JFrame;

class Main extends JFrame {
	public static void main(String[] argv) {
		JFrame frame = new JFrame();
		Panel panel = new Panel();
		
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(Main.EXIT_ON_CLOSE);
		frame.setTitle("Drawing Thing");
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		panel.startThread();
	}
}
