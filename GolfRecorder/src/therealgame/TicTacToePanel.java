package therealgame;

import gui.GolfFrame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TicTacToePanel extends JPanel {

	private JButton[][] buttons;
	private int[][] logicBoard;
	
	private boolean turn;
	private int drawCounter;
	
	private GolfFrame gf;

	public TicTacToePanel(GolfFrame gf) {
		setBackground(Color.black);
		setLayout(new GridLayout(3, 3, 3, 3));

		initialize();

		this.gf = gf;
	}

	/**
	 * Initializes the board.
	 */
	private void initialize() {
		// Init arrays
		buttons = new JButton[3][3];
		logicBoard = new int[3][3];
		for(int r = 0; r < 3; r++) {
			for(int c = 0; c < 3; c++) {
				buttons[c][r] = new JButton();
				buttons[c][r].addActionListener(new ButtonListener());
				buttons[c][r].setActionCommand(r + " " + c);
				logicBoard[c][r] = 0;

				add(buttons[c][r]);
			}
		}
	}

	/**
	 * Resets the board.
	 */
	private void reset() {
		// TODO Auto-generated method stub
		for(int r = 0; r < 3; r++) {
			for(int c = 0; c < 3; c++) {
				this.remove(buttons[c][r]);
			}
		}
		turn = false;
		drawCounter = 0;
		initialize();
	}

	/**
	 * Checks for a win condition.
	 */
	public void checkWin() {
		// TODO Auto-generated method stub
		// Vertical and Horizontal
		for(int i = 0; i < 3; i++) {
			if (logicBoard[i][0] == logicBoard[i][1] && logicBoard[i][0] != 0) {
				if (logicBoard[i][1] == logicBoard[i][2]) {
					launchFinish();
				}
			}
			if (logicBoard[0][i] == logicBoard[1][i] && logicBoard[0][i] != 0) {
				if (logicBoard[1][i] == logicBoard[2][i]) {
					launchFinish();
				}
			}
		}
		// Diagonal
		if (logicBoard[0][0] == logicBoard[1][1] && logicBoard[0][0] != 0) {
			if (logicBoard[0][0] == logicBoard[2][2]) {
				launchFinish();
			}
		}
		if (logicBoard[0][2] == logicBoard[1][1] && logicBoard[0][2] != 0) {
			if (logicBoard[0][2] == logicBoard[2][0]) {
				launchFinish();
			}
		}
	}

	/**
	 * Launches the JOptionPane with winner, then returns to the menu. 
	 */
	public void launchFinish() {
		// TODO Auto-generated method stub
		if(turn)
			JOptionPane.showMessageDialog(null, "X wins!", "Win!", JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(null, "O wins!", "Win!", JOptionPane.INFORMATION_MESSAGE);

		reset();
		gf.changeScene("MenuPanel");
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			JButton source = new JButton();
			int row = -1, col = -1;

			if (e.getSource() instanceof JButton) {
				// TODO Auto-generated method stub
				source = (JButton) e.getSource();
				String[] rCSA = source.getActionCommand().split(" ");
				row = Integer.parseInt(rCSA[0]);
				col = Integer.parseInt(rCSA[1]);
			}
			if (turn) {
				source.setText("O");
				turn = false;
				logicBoard[row][col] = 2;
			} else {
				source.setText("X");
				turn = true;
				logicBoard[row][col] = 1;
			}
			source.setEnabled(false);
			checkWin();
			
			// Checks for draw
			drawCounter++;
			if (drawCounter == 9) {
				JOptionPane.showMessageDialog(null, "Draw!", "Draw", JOptionPane.INFORMATION_MESSAGE);

				reset();
				gf.changeScene("MenuPanel");
			}
		}
	}
}
