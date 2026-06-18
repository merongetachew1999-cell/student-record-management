import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentRecordManagementSystem {
    private static final String DATA_DIR = "data";
    private static final String TEXT_FILE = DATA_DIR + "/students.txt";
    private static final String BINARY_FILE = DATA_DIR + "/students.bin";
    private static final String SERIALIZED_FILE = DATA_DIR + "/students.ser";
    private static final String BACKUP_DIR = DATA_DIR + "/backup";
    private static final String REPORTS_DIR = DATA_DIR + "/reports";
    
    private List<Student> students = new ArrayList<>();
    
    public StudentRecordManagementSystem() {
        createDirectories();
        System.out.println("✓ Student Record Management System Initialized");
    }
    
    private void createDirectories() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
            Files.createDirectories(Paths.get(BACKUP_DIR));
            Files.createDirectories(Paths.get(REPORTS_DIR));
        } catch (IOException e) {
            System.err.println("✗ Error creating directories: " + e.getMessage());
        }
    }
    
    public void displayFileProperties(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("\n============================================================");
            System.out.println("FILE PROPERTIES");
            System.out.println("============================================================");
            System.out.println("File Name: " + file.getName());
            System.out.println("Absolute Path: " + file.getAbsolutePath());
            System.out.println("File Size: " + file.length() + " bytes");
            System.out.println("Last Modified: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(file.lastModified())));
            System.out.println("Is Directory: " + file.isDirectory());
            System.out.println("Can Read: " + file.canRead());
            System.out.println("Can Write: " + file.canWrite());
            System.out.println("============================================================\n");
        } else {
            System.out.println("✗ File does not exist: " + filePath);
        }
    }
    
    public void addStudent(Student student) {
        if (students.stream().anyMatch(s -> s.getStudentID().equalsIgnoreCase(student.getStudentID()))) {
            System.out.println("✗ Student with ID " + student.getStudentID() + " already exists!");
            return;
        }
        students.add(student);
        System.out.println("✓ Student added successfully: " + student.getName());
    }
    
    public Student searchStudentByID(String studentID) {
        Optional<Student> student = students.stream().filter(s -> s.getStudentID().equalsIgnoreCase(studentID)).findFirst();
        if (student.isPresent()) {
            System.out.println("✓ Student found:\n  " + student.get());
            return student.get();
        }
        System.out.println("✗ Student with ID " + studentID + " not found");
        return null;
    }
    
    public void updateStudent(String studentID, String name, String department, double gpa) {
        Student student = searchStudentByID(studentID);
        if (student != null) {
            student.setName(name);
            student.setDepartment(department);
            student.setGPA(gpa);
            System.out.println("✓ Student updated successfully!");
        }
    }
    
    public void deleteStudent(String studentID) {
        boolean removed = students.removeIf(s -> s.getStudentID().equalsIgnoreCase(studentID));
        if (removed) {
            System.out.println("✓ Student with ID " + studentID + " deleted successfully!");
        } else {
            System.out.println("✗ Student with ID " + studentID + " not found");
        }
    }
    
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("✗ No students in the system");
            return;
        }
        System.out.println("\n==========================================================================================");
        System.out.println("ALL STUDENTS RECORDS");
        System.out.println("==========================================================================================");
        for (Student student : students) { System.out.println(student); }
        System.out.println("==========================================================================================");
        System.out.println("Total Students: " + students.size() + "\n");
    }
    
    public void saveToTextFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TEXT_FILE))) {
            writer.println("# Student Records - Text Format");
            writer.println("# Format: StudentID,Name,Department,GPA");
            for (Student student : students) { writer.println(student.toFileFormat()); }
            System.out.println("✓ Students saved to text file: " + TEXT_FILE);
            displayFileProperties(TEXT_FILE);
        } catch (IOException e) {
            System.err.println("✗ Error saving to text file: " + e.getMessage());
        }
    }
    
    public void loadFromTextFile() {
        try (Scanner scanner = new Scanner(new FileReader(TEXT_FILE))) {
            students.clear();
            int count = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                Student student = Student.fromFileFormat(line);
                if (student != null) { students.add(student); count++; }
            }
            System.out.println("✓ Loaded " + count + " students from text file");
        } catch (FileNotFoundException e) {
            System.err.println("✗ Text file not found: " + TEXT_FILE);
        }
    }
    
    public void saveToBinaryFile() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(BINARY_FILE))) {
            dos.writeInt(students.size());
            for (Student student : students) {
                dos.writeUTF(student.getStudentID());
                dos.writeUTF(student.getName());
                dos.writeUTF(student.getDepartment());
                dos.writeDouble(student.getGPA());
            }
            System.out.println("✓ Students saved to binary file: " + BINARY_FILE);
            displayFileProperties(BINARY_FILE);
        } catch (IOException e) {
            System.err.println("✗ Error saving to binary file: " + e.getMessage());
        }
    }
    
    public void loadFromBinaryFile() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(BINARY_FILE))) {
            students.clear();
            int count = dis.readInt();
            for (int i = 0; i < count; i++) {
                students.add(new Student(dis.readUTF(), dis.readUTF(), dis.readUTF(), dis.readDouble()));
            }
            System.out.println("✓ Loaded " + count + " students from binary file");
        } catch (FileNotFoundException e) {
            System.err.println("✗ Binary file not found: " + BINARY_FILE);
        } catch (IOException e) {
            System.err.println("✗ Error loading from binary file: " + e.getMessage());
        }
    }
    
    public void saveUsingObjectSerialization() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SERIALIZED_FILE))) {
            oos.writeObject(students);
            System.out.println("✓ Students saved using object serialization: " + SERIALIZED_FILE);
            displayFileProperties(SERIALIZED_FILE);
        } catch (IOException e) {
            System.err.println("✗ Error in object serialization: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public void loadUsingObjectSerialization() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SERIALIZED_FILE))) {
            students = (List<Student>) ois.readObject();
            System.out.println("✓ Loaded " + students.size() + " students from serialized file");
        } catch (FileNotFoundException e) {
            System.err.println("✗ Serialized file not found: " + SERIALIZED_FILE);
        } catch (Exception e) {
            System.err.println("✗ Error deserializing students: " + e.getMessage());
        }
    }
    
    public void createBackup() {
        if (students.isEmpty()) { System.out.println("✗ No students to backup"); return; }
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String backupFile = BACKUP_DIR + "/students_backup_" + timestamp + ".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(backupFile))) {
            bw.write("STUDENT RECORDS BACKUP - " + timestamp + "\n" + "=".repeat(90) + "\n");
            for (Student student : students) { bw.write(student.toString() + "\n"); }
            bw.write("=".repeat(90) + "\nTotal Records: " + students.size());
            System.out.println("✓ Backup created successfully: " + backupFile);
            displayFileProperties(backupFile);
        } catch (IOException e) {
            System.err.println("✗ Error creating backup: " + e.getMessage());
        }
    }
    
    public void generateReport() {
        if (students.isEmpty()) { System.out.println("✗ No students to generate report"); return; }
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportFile = REPORTS_DIR + "/report_" + timestamp + ".txt";
        double totalGPA = 0, maxGPA = Double.MIN_VALUE, minGPA = Double.MAX_VALUE;
        for (Student student : students) {
            double gpa = student.getGPA();
            totalGPA += gpa; maxGPA = Math.max(maxGPA, gpa); minGPA = Math.min(minGPA, gpa);
        }
        double averageGPA = totalGPA / students.size();
        
        System.out.println("\n============================================================");
        System.out.println("STUDENT RECORDS REPORT");
        System.out.println("============================================================");
        System.out.println("Total Students: " + students.size());
        System.out.println("Highest GPA: " + String.format("%.2f", maxGPA));
        System.out.println("Lowest GPA: " + String.format("%.2f", minGPA));
        System.out.println("Average GPA: " + String.format("%.2f", averageGPA));
        System.out.println("============================================================\n");
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(reportFile))) {
            writer.println("STUDENT RECORDS MANAGEMENT SYSTEM - REPORT\nGenerated: " + new Date() + "\n" + "=".repeat(60) + "\n");
            writer.printf("Total Students: %d\nHighest GPA: %.2f\nLowest GPA: %.2f\nAverage GPA: %.2f\n\n", students.size(), maxGPA, minGPA, averageGPA);
            writer.println("DETAILED STUDENT RECORDS:\n" + "=".repeat(60));
            for (Student student : students) { writer.println(student); }
        } catch (IOException e) {
            System.err.println("✗ Error saving report file: " + e.getMessage());
        }
    }
    
    public int getStudentCount() { return students.size(); }
}