package gui;
/**
 * The JFrame containing all components in the project.
 * 
 */

import io.FileIO;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GolfFrame extends JFrame {
	
	private static final long serialVersionUID = 5714125822315122310L;
	
	private JPanel cards;
	private JPanel mainPanel;
	private TablePanel btp;
	private TopPanel tp;
	
	/**
	 * Constructs a default GolfFrame.
	 */
	public GolfFrame(int width, int height) {
		super();
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		btp = new TablePanel();
		tp = new TopPanel(btp, this);
		InitialPanel mp = new InitialPanel(this, width, height);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(tp, BorderLayout.NORTH);
		mainPanel.add(btp, BorderLayout.CENTER);
		
		cards = new JPanel();
		cards.setLayout(new CardLayout());
		cards.add(mp, "MenuPanel");
		cards.add(mainPanel, "MainPanel");
		
		add(cards);
	}
	
	
	/**
	 * Changes the scene.
	 * @pre sceneName is valid
	 * @param sceneName - Name of scene to change.
	 * @post Scene with the specified name is shown.
	 */
	public void changeScene(String sceneName) {
		CardLayout c = (CardLayout) cards.getLayout();
		if (sceneName.equals("MainPanel")) {
			if(btp != null && tp != null && mainPanel != null) {
				mainPanel.remove(btp);
				mainPanel.remove(tp);
				cards.remove(mainPanel);
			}
			
			btp = new TablePanel();
			tp = new TopPanel(btp, this);
			mainPanel.add(tp, BorderLayout.NORTH);
			mainPanel.add(btp, BorderLayout.CENTER);
			cards.add(mainPanel, "MainPanel");
			c.show(cards, sceneName);
		} else if(sceneName.equals("MainPanelLoad")){
			c.show(cards, sceneName);
		} else {
			c.show(cards, sceneName);
		}
	}
	
	/**
	 * Loads tablepanel with the given data.
	 * @param fileName name of the file to load tablepanel from.
	 * @post a new tablepanel with the given data is created.
	 */
	public void loadScene(String fileName) {
		CardLayout c = (CardLayout) cards.getLayout();
		mainPanel.remove(btp);
		mainPanel.remove(tp);
		cards.remove(mainPanel);
		
		Object[][] playersData = FileIO.readPlayers(fileName);
		Object[][] coursesData = FileIO.readCourses();
		
		btp = new TablePanel(playersData, coursesData);
		tp = new TopPanel(btp, this);
		mainPanel.add(tp, BorderLayout.NORTH);
		mainPanel.add(btp, BorderLayout.CENTER);
		cards.add(mainPanel, "MainPanel");
		c.show(cards, "MainPanel");
	}
}
