import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String studentID;
    private String name;
    private String department;
    private double gpa;
    
    public Student(String studentID, String name, String department, double gpa) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.gpa = gpa;
    }
    
    public String getStudentID() { return studentID; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getGPA() { return gpa; }
    
    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setGPA(double gpa) { this.gpa = gpa; }
    
    @Override
    public String toString() {
        return String.format("ID: %s | Name: %-20s | Department: %-15s | GPA: %.2f", 
                             studentID, name, department, gpa);
    }
    
    public String toFileFormat() {
        return String.format("%s,%s,%s,%.2f", studentID, name, department, gpa);
    }
    
    public static Student fromFileFormat(String line) {
        String[] parts = line.split(",");
        if (parts.length == 4) {
            return new Student(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]));
        }
        return null;
    }
}
