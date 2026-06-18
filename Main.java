import java.util.Scanner;

public class Main {
    private static final StudentRecordManagementSystem system = new StudentRecordManagementSystem();
    private static final Scanner scanner = new Scanner(java.util.Objects.requireNonNull(System.console() != null ? System.console().reader() : new java.io.InputStreamReader(System.in)));

    public static void main(String[] args) {
        while (true) {
            System.out.println("  STUDENT RECORD MANAGEMENT SYSTEM");
            System.out.println("1. Student Operations (CRUD)");
            System.out.println("2. File Operations (Save/Load)");
            System.out.println("3. System Reports & Backups");
            System.out.println("4. Check File Metadata");
            System.out.println("5. Run Sample Data Demo");
            System.out.println("6. Exit Application");
            System.out.print("Select an option: ");
            
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": handleCRUD(); break;
                case "2": handleFiles(); break;
                case "3": handleReports(); break;
                case "4": handleMetadata(); break;
                case "5": runDemo(); break;
                case "6": System.out.println("Goodbye!"); return;
                default: System.out.println("✗ Invalid choice. Try again.");
            }
        }
    }

    private static void handleCRUD() {
        System.out.println("\n--- Student Operations ---");
        System.out.println("1. Add Student\n2. Search Student\n3. Update Student\n4. Delete Student\n5. View All");
        System.out.print("Choice: ");
        String op = scanner.nextLine().trim();
        
        if (op.equals("1")) {
            System.out.print("ID: "); String id = scanner.nextLine().trim();
            System.out.print("Name: "); String name = scanner.nextLine().trim();
            System.out.print("Dept: "); String dept = scanner.nextLine().trim();
            System.out.print("GPA: "); double gpa = Double.parseDouble(scanner.nextLine().trim());
            system.addStudent(new Student(id, name, dept, gpa));
        } else if (op.equals("2")) {
            System.out.print("Enter ID: "); system.searchStudentByID(scanner.nextLine().trim());
        } else if (op.equals("3")) {
            System.out.print("ID to update: "); String id = scanner.nextLine().trim();
            System.out.print("New Name: "); String name = scanner.nextLine().trim();
            System.out.print("New Dept: "); String dept = scanner.nextLine().trim();
            System.out.print("New GPA: "); double gpa = Double.parseDouble(scanner.nextLine().trim());
            system.updateStudent(id, name, dept, gpa);
        } else if (op.equals("4")) {
            System.out.print("ID to delete: "); system.deleteStudent(scanner.nextLine().trim());
        } else if (op.equals("5")) {
            system.displayAllStudents();
        }
    }

    private static void handleFiles() {
        System.out.println("\n--- File Operations ---");
        System.out.println("1. Save to Text | 2. Load from Text\n3. Save to Binary | 4. Load from Binary\n5. Save via Serial | 6. Load via Serial");
        System.out.print("Choice: ");
        switch (scanner.nextLine().trim()) {
            case "1": system.saveToTextFile(); break;
            case "2": system.loadFromTextFile(); break;
            case "3": system.saveToBinaryFile(); break;
            case "4": system.loadFromBinaryFile(); break;
            case "5": system.saveUsingObjectSerialization(); break;
            case "6": system.loadUsingObjectSerialization(); break;
        }
    }

    private static void handleReports() {
        System.out.println("\n--- Reports & Backups ---");
        System.out.println("1. Generate Performance Report\n2. Create Buffered System Backup");
        System.out.print("Choice: ");
        String ch = scanner.nextLine().trim();
        if (ch.equals("1")) system.generateReport();
        else if (ch.equals("2")) system.createBackup();
    }

    private static void handleMetadata() {
        System.out.println("\n--- Check Metadata ---");
        System.out.println("1. Text File\n2. Binary File\n3. Serialized File");
        System.out.print("Choice: ");
        switch (scanner.nextLine().trim()) {
            case "1": system.displayFileProperties("data/students.txt"); break;
            case "2": system.displayFileProperties("data/students.bin"); break;
            case "3": system.displayFileProperties("data/students.ser"); break;
        }
    }

    private static void runDemo() {
        System.out.println("\n--- Injecting Demo Student Records ---");
        system.addStudent(new Student("S001", "Alice Johnson", "Computer Sci", 3.85));
        system.addStudent(new Student("S002", "Bob Smith", "Data Science", 3.40));
        system.addStudent(new Student("S003", "Charlie Brown", "Mechanical Eng", 2.95));
        system.addStudent(new Student("S004", "Diana Prince", "Electrical Eng", 3.95));
        system.displayAllStudents();
    }
}