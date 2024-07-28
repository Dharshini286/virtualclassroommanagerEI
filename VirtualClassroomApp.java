 import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Observer Pattern Interfaces and Classes
interface Observer {
    void update(String message);
}

class AssignmentObserver implements Observer {
    private String name;

    public AssignmentObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println("Notification to " + name + ": " + message);
    }
}

class Observable {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}

// Classroom Class
class Classroom {
    private String name;
    private List<String> students;
    private List<String> assignments;

    public Classroom(String name) {
        this.name = name;
        this.students = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addStudent(String studentId) {
        students.add(studentId);
    }

    public void addAssignment(String assignmentDetails) {
        assignments.add(assignmentDetails);
    }

    public List<String> getStudents() {
        return students;
    }

    public List<String> getAssignments() {
        return assignments;
    }

    public void submitAssignment(String studentId, String assignmentDetails) {
        System.out.println("Assignment submitted by Student " + studentId + " in " + name + ": " + assignmentDetails);
    }
}

// Classroom Factory Class
class ClassroomFactory {
    public static Classroom createClassroom(String name) {
        return new Classroom(name);
    }
}

// Observable Classroom Class
class ObservableClassroom extends Classroom {
    private Observable observable;

    public ObservableClassroom(String name) {
        super(name);
        observable = new Observable();
    }

    public void addObserver(Observer observer) {
        observable.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        observable.removeObserver(observer);
    }

    @Override
    public void submitAssignment(String studentId, String assignmentDetails) {
        super.submitAssignment(studentId, assignmentDetails);
        observable.notifyObservers("Assignment submitted by Student " + studentId + " in " + getName() + ": " + assignmentDetails);
    }
}

// Observable Classroom Factory Class
class ObservableClassroomFactory {
    public static ObservableClassroom createClassroom(String name) {
        return new ObservableClassroom(name);
    }
}

// Virtual Classroom Manager Class
class VirtualClassroomManager {
    private static VirtualClassroomManager instance;
    private Map<String, ObservableClassroom> classrooms;

    private VirtualClassroomManager() {
        classrooms = new HashMap<>();
    }

    public static VirtualClassroomManager getInstance() {
        if (instance == null) {
            instance = new VirtualClassroomManager();
        }
        return instance;
    }

    public String addClassroom(String name) {
        if (classrooms.containsKey(name)) {
            return "Classroom " + name + " already exists.";
        }
        ObservableClassroom classroom = ObservableClassroomFactory.createClassroom(name);
        classrooms.put(name, classroom);
        return "Classroom " + name + " has been created.";
    }

    public String addStudent(String studentId, String className) {
        ObservableClassroom classroom = classrooms.get(className);
        if (classroom == null) {
            return "Error: Classroom " + className + " does not exist.";
        }
        classroom.addStudent(studentId);
        return "Student " + studentId + " has been enrolled in " + className + ".";
    }

    public String scheduleAssignment(String className, String assignmentDetails) {
        ObservableClassroom classroom = classrooms.get(className);
        if (classroom == null) {
            return "Error: Classroom " + className + " does not exist.";
        }
        classroom.addAssignment(assignmentDetails);
        return "Assignment for " + className + " has been scheduled.";
    }

    public String submitAssignment(String studentId, String className, String assignmentDetails) {
        ObservableClassroom classroom = classrooms.get(className);
        if (classroom == null) {
            return "Error: Classroom " + className + " does not exist.";
        }
        classroom.submitAssignment(studentId, assignmentDetails);
        return "Assignment submitted by Student " + studentId + " in " + className + ".";
    }

    public void listClassrooms() {
        if (classrooms.isEmpty()) {
            System.out.println("No classrooms available.");
            return;
        }
        System.out.println("Classrooms:");
        classrooms.keySet().forEach(System.out::println);
    }

    public void listStudents(String className) {
        ObservableClassroom classroom = classrooms.get(className);
        if (classroom == null) {
            System.out.println("Error: Classroom " + className + " does not exist.");
            return;
        }
        List<String> students = classroom.getStudents();
        if (students.isEmpty()) {
            System.out.println("No students enrolled in " + className + ".");
            return;
        }
        System.out.println("Students in " + className + ":");
        students.forEach(System.out::println);
    }
}

// Main Application Class
public class VirtualClassroomApp {
    public static void main(String[] args) {
        VirtualClassroomManager manager = VirtualClassroomManager.getInstance();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an option: 1. Add Classroom  2. Add Student  3. Schedule Assignment  4. Submit Assignment  5. List Classrooms  6. List Students  7. Exit");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.println("Enter classroom name:");
                    String className = scanner.nextLine();
                    System.out.println(manager.addClassroom(className));
                    break;
                case 2:
                    System.out.println("Enter student ID:");
                    String studentId = scanner.nextLine();
                    System.out.println("Enter classroom name:");
                    className = scanner.nextLine();
                    System.out.println(manager.addStudent(studentId, className));
                    break;
                case 3:
                    System.out.println("Enter classroom name:");
                    className = scanner.nextLine();
                    System.out.println("Enter assignment details:");
                    String assignmentDetails = scanner.nextLine();
                    System.out.println(manager.scheduleAssignment(className, assignmentDetails));
                    break;
                case 4:
                    System.out.println("Enter student ID:");
                    studentId = scanner.nextLine();
                    System.out.println("Enter classroom name:");
                    className = scanner.nextLine();
                    System.out.println("Enter assignment details:");
                    assignmentDetails = scanner.nextLine();
                    System.out.println(manager.submitAssignment(studentId, className, assignmentDetails));
                    break;
                case 5:
                    manager.listClassrooms();
                    break;
                case 6:
                    System.out.println("Enter classroom name:");
                    className = scanner.nextLine();
                    manager.listStudents(className);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}

