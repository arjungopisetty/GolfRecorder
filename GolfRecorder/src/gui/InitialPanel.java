package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * Class that represents the menu JPanel
 * 
 * @author Arjun Gopisetty, Chris Hsiao, Edward Kim
 *
 */
public class InitialPanel extends JPanel {

	private static final long serialVersionUID = -1928211068606112484L;

	private JButton newTableButton, openTableButton;
	private JLabel welcomeLabel;
	private GolfFrame golfFrameObj;
	private JFileChooser fileBrowser;
	
	private int height;
	private int width;
	
	private Image background;

	/**
	 * Constructs a default InitialPanel using a GolfFrame object
	 * 
	 * @param golfFrameObj
	 */
	public InitialPanel(GolfFrame golfFrameObj, int width, int height) {
		this.golfFrameObj = golfFrameObj;
		this.height = height;
		this.width = width;

		setLayout(null);
		
		newTableButton = new JButton("New Table");
		newTableButton.setActionCommand("new table");
		newTableButton.addActionListener(new ButtonHandler());
		newTableButton.setBounds(width / 2 - 150, height / 2 - 75, 300, 75);
		
		openTableButton = new JButton("Open Table From Savefile");
		openTableButton.setActionCommand("open table");
		openTableButton.addActionListener(new ButtonHandler());
		openTableButton.setBounds(width / 2 - 150, height / 2 + height / 25, 300, 75);

		Color c = new Color(64, 70, 121);
		welcomeLabel = new JLabel("Golf Recorder", JLabel.CENTER);
		welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 50));
		welcomeLabel.setBounds(width / 2 - 200, 100, 400, 50);
		welcomeLabel.setForeground(c);
		
		fileBrowser = new JFileChooser();
		fileBrowser.setDialogTitle("Choose Save File");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
		fileBrowser.setFileFilter(filter);
		fileBrowser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		add(welcomeLabel);
		add(newTableButton);
		add(openTableButton);
		
		
		background = new ImageIcon("Background2.jpg").getImage();
	}
	
	/**
	 * Paints the background of the panel.
	 */
	public void paintComponent(Graphics g) {
		setBackground(Color.white);
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
	}

	
	/**
	 * Represents a class to handle buttons in this panel
	 *
	 */
	class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("new table")) {
				golfFrameObj.changeScene("MainPanel");
			} else if (e.getActionCommand().equals("open table")){
				File f = new File("C:/");
				File namePath = null;
				int check;
				fileBrowser.setCurrentDirectory(f);
				check = fileBrowser.showOpenDialog(null);
				if(check == JFileChooser.APPROVE_OPTION){
					namePath = fileBrowser.getSelectedFile();
					System.out.println(namePath.getPath());
				} else {
					JOptionPane.showMessageDialog(null, "No file was selected.",
												"Notice!", JOptionPane.WARNING_MESSAGE);
				}
				
				if (namePath != null) {
					golfFrameObj.loadScene(namePath.getPath());
				}
			}
		}
	}
}