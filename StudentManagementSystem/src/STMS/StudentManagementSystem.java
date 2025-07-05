package STMS;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StudentManagementSystem {

	private static ArrayList<Student> students = new ArrayList<>();
	private static Scanner scanner = new Scanner(System.in);
	
	
	public static void main(String[] args) {
		loadStudentsFromFile();
		boolean running = true;
		
		while(running) {
			System.out.println("\n===== Student Management System ======");
			System.out.println("1.Add Student");
			System.out.println("2. View All Students");
			System.out.println("3. Search Student by ID");
			System.out.println("4. Delete Student");
			System.out.println("5. Exit");
			System.out.println("6. Update Student");
			System.out.println("7. Rest All Data");
			System.out.println("Enter your choice: ");
			
			int choice = scanner.nextInt();
			
			switch (choice) {
			case 1:
				addStudent();
				break;
			case 2:
				viewStudents();
				break;
			case 3:
				searchStudent();
				break;
			case 4:
				deleteStudent();
				break;
			case 5:
				saveStudentsToFile();
				running = false;
				System.out.println("Exiting system.. GoodBye!");
				break;
				
			case 6:
				updateStudent();
				break;
				
			case 7:
				resetAllData();
				break;
				
				default:
					System.out.println("Invalid choice. please try again");
	
			}
	
		}
	
	}
	
	private static void addStudent() {
		System.out.println("Enter Student ID: ");
		int id = scanner.nextInt();
		scanner.nextLine();  // consume newLine
		
		// check if ID already exists
		for(Student s: students) {
			if(s.getId() == id) {
				System.out.println("Student with this ID already exists!");
				return;
			}
		}
		
		System.out.println("Enter Name: ");
		String name = scanner.nextLine();
		
		System.out.println("Enter Age: ");
		int age = scanner.nextInt();
		scanner.nextLine();  // consume newLine
		
		if(age <=0) {
			System.out.println("Invalid age! Age must be greater then 0.");
			return;
		}
		
		System.out.println("Enter Course: ");
		String course = scanner.nextLine();
		
		Student newStudent = new Student(id, name, age, course);
		students.add(newStudent);
		System.out.println("student added successfully!");
		
	}
	
	private static void viewStudents() {
		if (students.isEmpty()) {
			System.out.println("No students found.");
			return;
		}
		
		System.out.println("Sort by: ");
	    System.out.println("1. ID");
	    System.out.println("2. Name");
	    System.out.print("Enter choice: ");
	    int sortChoice = scanner.nextInt();
	    scanner.nextLine();
	    if (sortChoice == 1) {
	        students.sort((s1, s2) -> Integer.compare(s1.getId(), s2.getId()));
	    } else if (sortChoice == 2) {
	        students.sort((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()));
	    }
		else {
			System.out.println("Invalid choice. showing unsorted list.");
		}
			for(Student s: students) {
				System.out.println(s);
			}
		
	}
	
	private static void searchStudent() {
	    System.out.println("\nSearch By:");
	    System.out.println("1. ID");
	    System.out.println("2. Name");
	    System.out.println("3. Course");
	    System.out.print("Enter choice: ");
	    int choice = scanner.nextInt();
	    scanner.nextLine(); // consume newline

	    switch (choice) {
	        case 1:
	            System.out.print("Enter Student ID: ");
	            int id = scanner.nextInt();
	            boolean foundById = false;
	            for (Student s : students) {
	                if (s.getId() == id) {
	                    System.out.println("Student found: " + s);
	                    foundById = true;
	                    break;
	                }
	            }
	            if (!foundById) {
	                System.out.println("Student not found with ID: " + id);
	            }
	            break;

	        case 2:
	            System.out.print("Enter Name to search: ");
	            String name = scanner.nextLine().toLowerCase();
	            boolean foundByName = false;
	            for (Student s : students) {
	                if (s.getName().toLowerCase().contains(name)) {
	                    System.out.println(s);
	                    foundByName = true;
	                }
	            }
	            if (!foundByName) {
	                System.out.println("No students found with that name.");
	            }
	            break;

	        case 3:
	            System.out.print("Enter Course to search: ");
	            String course = scanner.nextLine().toLowerCase();
	            boolean foundByCourse = false;
	            for (Student s : students) {
	                if (s.getCourse().toLowerCase().contains(course)) {
	                    System.out.println(s);
	                    foundByCourse = true;
	                }
	            }
	            if (!foundByCourse) {
	                System.out.println("No students found in that course.");
	            }
	            break;

	        default:
	            System.out.println("Invalid choice.");
	    }
	}
	
	private static void deleteStudent() {
		System.out.println("Enter Student ID to delete: ");
		int id = scanner.nextInt();
		
		Student toRemove = null;
		for(Student s : students) {
			if(s.getId() == id) {
				toRemove = s;
				break;
			}
		}
		
		if(toRemove != null) {
			students.remove(toRemove);
			System.out.println("Student deleted successfully.");
		}else {
			System.out.println("Student not found.");
		}
		
		
	}
	
	private static void updateStudent() {
	    System.out.print("Enter Student ID to update: ");
	    int id = scanner.nextInt();
	    scanner.nextLine();  // consume newline

	    boolean found = false;

	    for (Student s : students) {
	        if (s.getId() == id) {
	            found = true;
	            System.out.println("Current details: " + s);

	            System.out.print("Enter new name (or press Enter to keep current): ");
	            String name = scanner.nextLine();
	            if (!name.trim().isEmpty()) {
	                // We can't set name directly, so create a new object or use setters
	                s = new Student(s.getId(), name, s.getAge(), s.getCourse());
	            }

	            System.out.print("Enter new age (or 0 to keep current): ");
	            int age = scanner.nextInt();
	            scanner.nextLine();
	            if (age > 0) {
	                s = new Student(s.getId(), s.getName(), age, s.getCourse());
	            }

	            System.out.print("Enter new course (or press Enter to keep current): ");
	            String course = scanner.nextLine();
	            if (!course.trim().isEmpty()) {
	                s = new Student(s.getId(), s.getName(), s.getAge(), course);
	            }

	            // Replace the old student with the updated one
	            for (int i = 0; i < students.size(); i++) {
	                if (students.get(i).getId() == id) {
	                    students.set(i, s);
	                    break;
	                }
	            }

	            System.out.println("Student updated successfully.");
	            break;
	        }
	    }

	    if (!found) {
	        System.out.println("Student with ID " + id + " not found.");
	    }
	}
	
	private static void resetAllData() {
	    System.out.print("Are you sure you want to delete all student data? (yes/no): ");
	    String confirm = scanner.nextLine().trim().toLowerCase();

	    if (confirm.equals("yes")) {
	        students.clear(); // clear in-memory list

	        // Overwrite the file with empty content
	        try (FileWriter writer = new FileWriter("students.txt")) {
	            writer.write("");
	            System.out.println("All student data has been deleted.");
	        } catch (IOException e) {
	            System.out.println("Error clearing the file: " + e.getMessage());
	        }
	    } else {
	        System.out.println("Reset cancelled.");
	    }
	}
	


	private static void saveStudentsToFile() {
	    try (FileWriter writer = new FileWriter("students.txt")) {
	        for (Student s : students) {
	            writer.write(s.getId() + "," + s.getName() + "," + s.getAge() + "," + s.getCourse() + "\n");
	        }
	        System.out.println("Student data saved to students.txt");
	    } catch (IOException e) {
	        System.out.println("Error saving student data: " + e.getMessage());
	    }
	}
	
	private static void loadStudentsFromFile() {
	    try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split(",");
	            if (parts.length == 4) {
	                int id = Integer.parseInt(parts[0]);
	                String name = parts[1];
	                int age = Integer.parseInt(parts[2]);
	                String course = parts[3];

	                Student student = new Student(id, name, age, course);
	                students.add(student);
	            }
	        }
	        System.out.println("Loaded student data from file.");
	    } catch (IOException e) {
	        System.out.println("No previous data found or error loading file.");
	    }
	}
	
	
	

}
