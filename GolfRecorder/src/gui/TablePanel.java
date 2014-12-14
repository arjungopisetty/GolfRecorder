package gui;

import game.Course;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * Class that represents the panel with the JTable
 * 
 * @author Edward Kim, Arjun Gopisetty
 */
public class TablePanel extends JPanel {

	private static final long serialVersionUID = -5838109297325005728L;

	private List<TableCellEditor> editors = new ArrayList<TableCellEditor>();
	private JTable dataTable;
	private DefaultTableModel dataTableModel;
	private JComboBox[] courseSelector;

	private boolean saved, initializedFromFile;
	private int numGames;

	/**
	 * Constructs a default table panel.
	 */
	public TablePanel() {
		this(new Object[][] { 
				{"Course", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
				{"Date", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
				{"Michael Wang", "", "", "", "", "", "", "", "", "", "", "", "", "", ""}
				}, new Object[][]{
				{0.0, 0, "Select a course"},
				{69.3, 123, "Shoreline"},
				{70.4, 117, "Pa Muni"},
				{70.4, 121, "Sunnyvale Muni"},
				{59.0, 96, "Deepcliff"}
		});
		saved = false;
		initializedFromFile = false;
	}

	/**
	 * Constructs a table panel with given data.
	 * 
	 * @param data Data to be used to initialize table with.
	 * @post a tablepanel object is created, and data and courses are not changed
	 */
	public TablePanel(Object[][] data, Object[][] courses) {
		setLayout(new BorderLayout());
		saved = true;
		initializedFromFile = true;

		Course.courses.clear();
		// Initialize data and model
		Object[] datacols = new Object[data[0].length + 1];
		datacols[0] = "Name";
		int colnum = 1;
		for (int i = 1; i < data[0].length; i += 2) {
			datacols[i] = "Game " + colnum + " score";
			datacols[i + 1] = "Round Index";
			colnum++;
		}
		numGames = 8;
		datacols[data[0].length] = "Final Average";
		dataTableModel = new DefaultTableModel(data, datacols) {
			private static final long serialVersionUID = -5397833437967562455L;

			public boolean isCellEditable(int row, int col) {
				String data = "";
				if(getValueAt(row, col) instanceof String) {
					data = (String) getValueAt(row, col);
				}
				if(data != null) {
					if (getColumnName(col).equals("Round Index") || getColumnName(col).equals("Final Average") || data.equals("Course") || data.equals("Date")) {
						return false;
					}
				}
				return true;
			}
		};

		// Initialize table
		dataTable = new JTable(dataTableModel) {
			private static final long serialVersionUID = -2457902241175616146L;

			// Determine editor to be used by row
			public TableCellEditor getCellEditor(int row, int column) {
				int col = convertColumnIndexToModel(column);
				if (row == 0 && col != 0 && col % 2 != 0
						&& col != getColumnCount() - 1)
					return editors.get(row);
				else
					return super.getCellEditor(row, column);
			}

			// Sets the tool tips
			public String getToolTipText(MouseEvent e) {
				String tip = null;
				Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int realRowIndex = convertRowIndexToModel(rowIndex);

				if (realRowIndex == 0) {
					tip = "Select course from here";
				} else if (realRowIndex == 1) { // Date row
					tip = "Enter date here in the format MM/DD/YY";

				} else { // Another row
					tip = super.getToolTipText(e);
				}
				return tip;
			}
			
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
	            Component component = super.prepareRenderer(renderer, row, column);
	            int rendererWidth = component.getPreferredSize().width;
	            String colName = dataTableModel.getColumnName(column);
	            Object data = getValueAt(row, column);
	            int colSize = colName.length() * 10;
	            int dataSize = 0;
	            if (data != null) {
		            if (data instanceof String) {
		            	if(!data.equals("")) {
		            		dataSize = ((String) data).length() * 10;
		            	}
		            } else if (data instanceof Double) {
		            	dataSize = Double.toString((Double) data).length() * 10;
		            }
	            }
	            TableColumn tableColumn = getColumnModel().getColumn(column);
	            tableColumn.setPreferredWidth(Math.max(Math.max(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()), dataSize), colSize));
	            return  component;
	         }
		};
		dataTable.setCellSelectionEnabled(true);
		dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// Set width of first column to 100
		dataTable.getColumnModel().getColumn(0).setPreferredWidth(140);
		// Sets the height of the cells to 30
		dataTable.setRowHeight(30);
		dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		// Intialize courses
		courseSelector = new JComboBox[dataTable.getColumnCount() / 2];
		for(int i = 0; i < dataTable.getColumnCount() / 2; i++) {
			courseSelector[i] = new JComboBox();
			courseSelector[i].setToolTipText("Select the Desired Course Here");
			for(Object[] course : courses) {
				double rating = 0;
				int slope = 0;
				String name = "";
				if(course[0] instanceof Double) {
					rating = (Double)course[0];
				}
				if(course[1] instanceof Integer) {
					slope = (Integer)course[1];
				}
				name = (String)course[2];
				
				Course c = new Course(rating, slope, name);
				courseSelector[i].addItem(c.getName());
			}
			DefaultCellEditor dce1 = new DefaultCellEditor(courseSelector[i]);
			editors.add(dce1);
		}

		// Turn off reordering
		dataTable.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane pane = new JScrollPane(dataTable);
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(pane, BorderLayout.CENTER);
	}

	/**
	 * Adds a row to the table.
	 * 
	 * @param a Objects to add
	 * @post a row is added to the table.
	 */
	public void addRow(Object ... a) {
		dataTableModel.addRow(a);
		setSave(false);
	}
	
	/**
	 * Adds a new game to the table.
	 * @post two columns are added to the table.
	 */
	public void addGame() {
		removeColumn(dataTableModel.getColumnCount() - 1);
		dataTableModel.addColumn("Game " + numGames + " score");
		dataTableModel.addColumn("Round Index");
		dataTableModel.addColumn("Final Average");
		dataTable.getColumnModel().getColumn(0).setPreferredWidth(140);
		numGames++;
		setSave(false);
	}
	
	private String[] getRemainingIdentifiers(int columnIndex) {

	    String[] identifiers = new String[dataTable.getColumnCount() - 1];
	    int k = 0;

	    for(int i = 0; i < dataTable.getColumnCount(); i++) {
	        if(i != columnIndex) {
	            identifiers[k++] = dataTable.getColumnName(i);
	        }
	    }

	    return identifiers;
	}

	private void removeColumn(int columnIndex) {

	    String[][] data = new String[dataTable.getRowCount()][dataTable.getColumnCount() - 1];

	    for(int i = 0; i < dataTable.getRowCount(); i++) {
	        for(int j = 0; j < dataTable.getColumnCount(); j++) {

	            if(j != columnIndex) {
	                if(dataTable.getValueAt(i, j) == null) {
	                	dataTable.setValueAt("", i, j);
	                }
	                if(j < columnIndex) {
	                    data[i][j] = dataTable.getValueAt(i, j).toString();
	                } else {
	                    data[i][j - 1] = dataTable.getValueAt(i, j).toString();
	                }
	            }
	        }
	    }

	    dataTableModel = new DefaultTableModel(data, getRemainingIdentifiers(columnIndex));
	    dataTable.setModel(dataTableModel);
	    setSave(false);
	}
	
	/**
	 * Gets the number of games played
	 * 
	 * @return numGames The number of games played.
	 */
	public int getGameNumber() {
		return numGames;
	}

	/**
	 * Increments the number of games played.
	 * 
	 * @post numGames is incremented by 1.
	 */
	public void incrementGameNumber() {
		numGames++;
		setSave(false);
	}

	/**
	 * Gets all the data in the table.
	 * 
	 * @return A String[][] of data from the table.
	 */
	public String[][] dumpData() {
		String[][] data = new String[dataTableModel.getRowCount()][dataTableModel.getColumnCount() - 1];
		for (int r = 0; r < dataTableModel.getRowCount(); r++) {
			for (int c = 0; c < dataTableModel.getColumnCount() - 1; c++) {
				Object value = dataTableModel.getValueAt(r, c);
				if(value instanceof Double) {
					System.out.println("saved as double");
					data[r][c] = Double.toString((Double)value);
				} else {
					data[r][c] = (String) dataTableModel.getValueAt(r, c);
				}
			}
		}
		return data;
	}
	
	/**
	 * @return saved whether the table is saved.
	 */
	public boolean isSaved() {
		return saved;
	}

	/**
	 * Sets the save field.
	 * @param s boolean to set save as.
	 */
	public void setSave(boolean s) {
		saved = s;
	}
	
	/**
	 * Sets the InitializedFromFile field.
	 * @param s
	 */
	public void setInitializedFromFile(boolean s){
		initializedFromFile = s;
	}

	/**
	 * @return courseSelector JComboBoxes for selecting courses.
	 */
	public JComboBox[] getCourseSelectors() {
		return courseSelector;
	}
	
	/**
	 * @return courses All courses in this table.
	 */
	public HashMap<String, Course> getCourses() {
		return Course.courses;
	}
	
	/**
	 * @return whether or not this table was initialized from a file.
	 */
	public boolean isInitializedFromFile(){
		return initializedFromFile;
	}
	
	/**
	 * Adds a new course to the list.
	 * 
	 * @param c - Course to add.
	 * @post courseSelector is added with new course.
	 */
	public void addNewCourse(Course c) {
		if (c != null) {
			String course = c.getName();
			if (course != null && !course.equals("")) {
				for(JComboBox s : courseSelector) {
					s.addItem(course);
				}
			}
		}
		setSave(false);
	}

	/**
	 * Updates the round index for all players and games.
	 * 
	 * @post appropriate cells in the table are updated with the calculated round index.
	 */
	public void updateRoundIndex() {
		for (int c = 1; c < dataTableModel.getColumnCount() - 1; c += 2) {
			String courseName = (String) dataTableModel.getValueAt(0, c);
			if(!courseName.equals("Select a course")) {
				for(int r = 2; r < dataTableModel.getRowCount(); r++) {
					if (dataTableModel.getValueAt(r, c) != null && courseName != null && !courseName.equals("") && !dataTableModel.getValueAt(r, c).equals("")) {
						try {
							int score = Integer.parseInt((String) dataTableModel.getValueAt(r, c));
							double index = Course.courses.get(courseName).getRoundIndex(score);
							dataTableModel.setValueAt(index, r, c + 1);
						} catch(NumberFormatException e) {
							JOptionPane.showMessageDialog(this, "Invalid score!");
						}
					}
				}
			}
		}
		setSave(false);
	}
	
	/**
	 * Updates the final average for all players.
	 * 
	 * @post cells in the last column are updated with the final average.
	 */
	public void updateFinalAverage() {
		int curNumGames = 0;
		for(int r = 2; r < dataTableModel.getRowCount(); r++) {
			double average = 0;
			for(int c = 2; c < dataTableModel.getColumnCount() - 1; c += 2) {
				Object data = dataTableModel.getValueAt(r, c);
				if(data instanceof Double) {
					average += (Double)dataTableModel.getValueAt(r, c);
					curNumGames++;
				}
			}
			average /= curNumGames;
			average = (double)Math.round(average * 100) / 100;
			dataTableModel.setValueAt(average, r, dataTableModel.getColumnCount() - 1);
		}
		setSave(false);
	}
}
