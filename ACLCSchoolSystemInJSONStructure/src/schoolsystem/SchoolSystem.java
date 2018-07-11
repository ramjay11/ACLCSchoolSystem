package schoolsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SchoolSystem {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter student name: ");
		String name = input.nextLine();
		JSONObject root = new JSONObject();
		root.put("name", name);
		JSONArray courses = new JSONArray();
		while(true){
			System.out.println("Enter course name: ");
			String course = input.nextLine();
			if(course.length() == 0) { break; }
			System.out.println("Enter grade : ");
			int grade = input.nextInt();
			if(input.hasNextLine()){
				input.nextLine();
			}
			System.out.println("Press ENTER if done");
			JSONObject courseObject = new JSONObject();
			courseObject.put("grade", grade);
			courseObject.put("name", course);
			courses.add(courseObject);
		}
		root.put("courses", courses);
		System.out.println(root.toJSONString());
	
		File file = new File("StudentGrades.txt");
		try(PrintWriter writer = new PrintWriter(file)) {
			writer.print(root.toJSONString());
		} catch(FileNotFoundException e) {
			System.out.println(e.toString());
		}
		System.out.println("File created successfully\n\n Hit Enter to display");
		input.nextLine();
		try {
			input = new Scanner(file);
			StringBuilder jsonIn = new StringBuilder();
			while(input.hasNextLine()) {
				jsonIn.append(input.nextLine());
			}
			System.out.println(jsonIn.toString());
			JSONParser parser = new JSONParser();
			JSONObject objRoot = (JSONObject) parser.parse(jsonIn.toString());
			System.out.printf("Student name is %s\n", objRoot.get("name").toString());
			JSONArray coursesIn = (JSONArray) objRoot.get("courses");
			for (int i = 0; i < coursesIn.size(); i++) {
				JSONObject courseIn = (JSONObject) coursesIn.get(i);
				long gradeIn = (long) courseIn.get("grade");
				String nameIn = (String) courseIn.get("name");
				System.out.printf("Course is %s and Grade is %d\n", nameIn, gradeIn);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		} catch (ParseException e) {
			System.out.println(e.toString());
		}

	}

}
