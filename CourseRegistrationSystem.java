import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Class to represent a course
class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private ArrayList<Student> enrolledStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = new ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getAvailableSlots() {
        return capacity - enrolledStudents.size();
    }

    public boolean enrollStudent(Student student) {
        if (enrolledStudents.size() < capacity) {
            enrolledStudents.add(student);
            return true;
        }
        return false;
    }

    public boolean dropStudent(Student student) {
        return enrolledStudents.remove(student);
    }

    public void displayCourseDetails() {
        System.out.println("Course Code: " + courseCode);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Capacity: " + capacity);
        System.out.println("Schedule: " + schedule);
        System.out.println("Available Slots: " + getAvailableSlots());
    }
}

// Class to represent a student
class Student {
    private String studentId;
    private String name;
    private ArrayList<Course> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public boolean registerCourse(Course course) {
        if (!registeredCourses.contains(course) && course.enrollStudent(this)) {
            registeredCourses.add(course);
            return true;
        }
        return false;
    }

    public boolean dropCourse(Course course) {
        if (registeredCourses.contains(course) && course.dropStudent(this)) {
            registeredCourses.remove(course);
            return true;
        }
        return false;
    }

    public void displayRegisteredCourses() {
        System.out.println("Registered Courses for " + name + " (ID: " + studentId + "):");
        for (Course course : registeredCourses) {
            course.displayCourseDetails();
            System.out.println();
        }
    }
}

// Main class for the course registration system
public class CourseRegistrationSystem {
    private static HashMap<String, Course> courses;
    private static HashMap<String, Student> students;

    public static void main(String[] args) {
        courses = new HashMap<>();
        students = new HashMap<>();

        // Add some sample courses
        courses.put("CS101", new Course("CS101", "Introduction to Computer Science", "Basic concepts of computer science", 3, "MWF 10-11 AM"));
        courses.put("MATH101", new Course("MATH101", "Calculus I", "Introduction to differential calculus", 2, "TTh 2-3:30 PM"));
        courses.put("PHYS101", new Course("PHYS101", "General Physics I", "Basic principles of physics", 2, "MWF 1-2 PM"));

        // Add some sample students
        students.put("S001", new Student("S001", "Alice"));
        students.put("S002", new Student("S002", "Bob"));

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nCourse Registration System Menu:");
            System.out.println("1. List available courses");
            System.out.println("2. Register for a course");
            System.out.println("3. Drop a course");
            System.out.println("4. View registered courses");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    listAvailableCourses();
                    break;
                case 2:
                    registerForCourse(scanner);
                    break;
                case 3:
                    dropCourse(scanner);
                    break;
                case 4:
                    viewRegisteredCourses(scanner);
                    break;
                case 5:
                    System.out.println("Exiting... Thank you for using the Course Registration System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        scanner.close();
    }

    private static void listAvailableCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courses.values()) {
            course.displayCourseDetails();
            System.out.println();
        }
    }

    private static void registerForCourse(Scanner scanner) {
        System.out.print("\nEnter Student ID: ");
        String studentId = scanner.next();
        Student student = students.get(studentId);

        if (student != null) {
            System.out.print("Enter Course Code: ");
            String courseCode = scanner.next();
            Course course = courses.get(courseCode);

            if (course != null) {
                if (student.registerCourse(course)) {
                    System.out.println("Successfully registered for the course.");
                } else {
                    System.out.println("Failed to register for the course. It may be full or already registered.");
                }
            } else {
                System.out.println("Course not found.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void dropCourse(Scanner scanner) {
        System.out.print("\nEnter Student ID: ");
        String studentId = scanner.next();
        Student student = students.get(studentId);

        if (student != null) {
            System.out.print("Enter Course Code: ");
            String courseCode = scanner.next();
            Course course = courses.get(courseCode);

            if (course != null) {
                if (student.dropCourse(course)) {
                    System.out.println("Successfully dropped the course.");
                } else {
                    System.out.println("Failed to drop the course. It may not be registered.");
                }
            } else {
                System.out.println("Course not found.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void viewRegisteredCourses(Scanner scanner) {
        System.out.print("\nEnter Student ID: ");
        String studentId = scanner.next();
        Student student = students.get(studentId);

        if (student != null) {
            student.displayRegisteredCourses();
        } else {
            System.out.println("Student not found.");
        }
    }
}
