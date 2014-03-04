import gui.GolfFrame;

import javax.swing.JFrame;

/**
 * Commit test by Edward Kim + Arjun Gopisetty + Public CSV Files
 */

public class GolfRecorder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int width = 1400;
		int height = 875;

		GolfFrame frame = new GolfFrame(width, height);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
