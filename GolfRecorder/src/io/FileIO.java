package io;
/**
 * Saves the program's data to the disk for later use.
 * 
 * @author Christopher Hsiao
 */
import game.Course;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class FileIO {


	/**
	 * Saves the players.
	 * @pre fileName is not empty.
	 * @param fileName - Path of file to save to.
	 * @param data - Data to be saved to the file.
	 * @post fileName and data are unchanged.
	 */
	public static void savePlayers(String fileName, String[][] data) {
		CSVWriter writer;
		if(!fileName.endsWith(".csv")){
			fileName += ".csv";
		}
		try {
			writer = new CSVWriter(new FileWriter(fileName), ',');
			for(int i = 0; i < data.length; i++){
				writer.writeNext(data[i]);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the courses.
	 * @pre courses is not null.
	 * @param courses - HashMap of the courses.
	 * @post courses are not changed.
	 */
	public static void saveCourses(HashMap<String, Course> courses){
		CSVWriter writer;
		ArrayList<Course> data = new ArrayList<Course>();
		for(Course c : courses.values()){
			data.add(c);
		}
		try {
			writer = new CSVWriter(new FileWriter("courses.csv"), ',');
			for(int i = 0; i < data.size(); i++){
				writer.writeNext(data.get(i).dumpData());
			}
			writer.close();
		} catch(IOException e){
			JOptionPane.showMessageDialog(null, "There was an error saving the courses!", "Notice!", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Reads data on players from a file.
	 * @pre filePath is not empty.
	 * @param filePath - Path of the file.
	 * @return 2D Object array of data read from the file.
	 * @post filepath is unchanged.
	 */
	public static Object[][] readPlayers(String filePath){
		CSVReader reader;
		String[] nextLine;
		ArrayList<String[]> data = new ArrayList<String[]>();
		try {
			reader = new CSVReader(new FileReader(filePath));
			try {
				while ((nextLine = reader.readNext()) != null) {
					data.add(nextLine);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convert(data);
	}

	/**
	 * Reads data on courses from a file.
	 * @return data on all courses
	 */
	public static Object[][] readCourses(){
		CSVReader reader;
		String[] nextLine;
		ArrayList<String[]> data = new ArrayList<String[]>();
		try {
			reader = new CSVReader(new FileReader("courses.csv"));
			try {
				while ((nextLine = reader.readNext()) != null) {
					data.add(nextLine);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return convert(data);
	}

	private static Object[][] convert(ArrayList<String[]> info){
		Object[][] output = new Object[info.size()][info.get(0).length];
		for(int i = 0; i < output.length; i++){
			for(int k = 0; k < output[0].length; k++){
				output[i][k] = info.get(i)[k];
			}
		}
		return output;
	}
}
