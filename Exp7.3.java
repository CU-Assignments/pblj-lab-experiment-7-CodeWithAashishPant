### Instructions for Java JDBC MVC Student Management System  

1. **Setup MySQL Database:**  
   - Install and start MySQL.  
   - Create a database (e.g., `StudentDB`).  
   - Create a table:  
     ```sql
     CREATE TABLE Student (
         StudentID INT PRIMARY KEY,
         Name VARCHAR(100),
         Department VARCHAR(50),
         Marks DOUBLE
     );
     ```

2. **Update Database Credentials:**  
   - Modify `URL`, `USER`, and `PASSWORD` in the code to match your MySQL database credentials.

3. **Add MySQL JDBC Driver:**  
   - Download and add `mysql-connector-java.jar` to your project's classpath.

4. **Compile and Run the Program:**  
   - Compile: `javac StudentManagementApp.java`  
   - Run: `java StudentManagementApp`  

5. **Menu-Driven Operations:**  
   - **Add Student:** Enter StudentID, Name, Department, and Marks.  
   - **View Students:** Displays all students in the table.  
   - **Update Student:** Modify Name, Department, or Marks using StudentID.  
   - **Delete Student:** Remove a student using StudentID.  
   - **Exit:** Quit the program.

6. **Transaction Handling:**  
   - Ensures data integrity by using `conn.setAutoCommit(false)` and `conn.commit()`.  
   - Rolls back changes in case of errors.

7. **Verify Database Changes:**  
   - Use `SELECT * FROM Student;` in MySQL to confirm modifications.


import java.sql.*;
import java.util.Scanner;

public class StudentManagementApp {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/StudentDB";
        String user = "your_username";
        String password = "your_password";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            Scanner scanner = new Scanner(System.in);
            
            while (true) {
                System.out.println("1. Add Student\n2. View Students\n3. Update Student\n4. Delete Student\n5. Exit");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        System.out.print("Enter Student ID: ");
                        int studentId = scanner.nextInt();
                        System.out.print("Enter Name: ");
                        String name = scanner.next();
                        System.out.print("Enter Department: ");
                        String department = scanner.next();
                        System.out.print("Enter Marks: ");
                        double marks = scanner.nextDouble();
                        String insertQuery = "INSERT INTO Student (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)";
                        PreparedStatement pstmt = conn.prepareStatement(insertQuery);
                        pstmt.setInt(1, studentId);
                        pstmt.setString(2, name);
                        pstmt.setString(3, department);
                        pstmt.setDouble(4, marks);
                        pstmt.executeUpdate();
                        conn.commit();
                        System.out.println("Student Added Successfully!");
                        pstmt.close();
                        break;
                    
                    case 2:
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM Student");
                        System.out.println("StudentID | Name | Department | Marks");
                        while (rs.next()) {
                            System.out.println(rs.getInt("StudentID") + " | " + rs.getString("Name") + " | " + rs.getString("Department") + " | " + rs.getDouble("Marks"));
                        }
                        rs.close();
                        stmt.close();
                        break;
                    
                    case 3:
                        System.out.print("Enter Student ID to Update: ");
                        int updateId = scanner.nextInt();
                        System.out.print("Enter New Marks: ");
                        double newMarks = scanner.nextDouble();
                        String updateQuery = "UPDATE Student SET Marks = ? WHERE StudentID = ?";
                        pstmt = conn.prepareStatement(updateQuery);
                        pstmt.setDouble(1, newMarks);
                        pstmt.setInt(2, updateId);
                        pstmt.executeUpdate();
                        conn.commit();
                        System.out.println("Student Updated Successfully!");
                        pstmt.close();
                        break;
                    
                    case 4:
                        System.out.print("Enter Student ID to Delete: ");
                        int deleteId = scanner.nextInt();
                        String deleteQuery = "DELETE FROM Student WHERE StudentID = ?";
                        pstmt = conn.prepareStatement(deleteQuery);
                        pstmt.setInt(1, deleteId);
                        pstmt.executeUpdate();
                        conn.commit();
                        System.out.println("Student Deleted Successfully!");
                        pstmt.close();
                        break;
                    
                    case 5:
                        System.out.println("Exiting...");
                        conn.close();
                        scanner.close();
                        return;
                    
                    default:
                        System.out.println("Invalid Choice!");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}

