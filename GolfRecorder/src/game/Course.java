package game;

import java.util.HashMap;

/**
 * The Course class represents a golf course.
 * 
 * @author Arjun Gopisetty
 */
public class Course {

	private double courseRating;
	private int slope;
	private String name;
	
	public static HashMap<String, Course> courses = new HashMap<String, Course>();
	
	/**
	 * Constructs a Course that is initialized with a course rating and slope.
	 * 
	 * @param rating - Course rating
	 * @param slope - Slope of the course
	 */
	public Course(double rating, int slope, String name) {
		this.courseRating = rating;
		this.slope = slope;
		this.name = name;
		
		courses.put(name, this);
	}
	
	/**
	 * @return Name of course.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return The slope of the course.
	 */
	public int getSlope() {
		return slope;
	}
	
	/**
	 * @return The course rating;
	 */
	public double getRating() {
		return courseRating;
	}
	
	/**
	 * @return All the data;
	 */
	public String[] dumpData(){
		String[] dump = new String[3];
		dump[0] = courseRating + "";
		dump[1] = slope + "";
		dump[2] = name;
		return dump;
	}
	
	/**
	 * Calculates and returns the round index.
	 * 
	 * @param score - Score of the player.
	 * @return The round index
	 */
	public double getRoundIndex(int score) {
		return (double)Math.round(((score - (0.5 * courseRating)) * (113.0 / slope)) * 100) / 100;
	}
}
