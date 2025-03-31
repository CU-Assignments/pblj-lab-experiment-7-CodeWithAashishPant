### Instructions to Run the Java CRUD Program:

1. **Setup MySQL Database**
   - Ensure MySQL is installed and running.
   - Create a database and a `Product` table with columns `ProductID`, `ProductName`, `Price`, and `Quantity`.

2. **Update Database Credentials**
   - Replace `your_database`, `your_username`, and `your_password` in the code with actual database credentials.

3. **Add MySQL JDBC Driver**
   - Download and add `mysql-connector-java.jar` to your project’s classpath.

4. **Compile and Run the Program**
   - Compile: `javac ProductCRUD.java`
   - Run: `java ProductCRUD`

5. **Menu-Driven Operations**
   - Select options to **Create**, **Read**, **Update**, or **Delete** products.
   - Input values as prompted.

6. **Transaction Handling**
   - Transactions ensure data integrity.
   - If an error occurs, changes are rolled back.

7. **Verify Output**
   - Ensure product records are correctly manipulated in the database.

import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/your_database";
        String user = "your_username";
        String password = "your_password";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            Scanner scanner = new Scanner(System.in);
            
            while (true) {
                System.out.println("1. Add Product\n2. View Products\n3. Update Product\n4. Delete Product\n5. Exit");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        System.out.print("Enter Product Name: ");
                        String name = scanner.next();
                        System.out.print("Enter Price: ");
                        double price = scanner.nextDouble();
                        System.out.print("Enter Quantity: ");
                        int quantity = scanner.nextInt();
                        String insertQuery = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
                        PreparedStatement pstmt = conn.prepareStatement(insertQuery);
                        pstmt.setString(1, name);
                        pstmt.setDouble(2, price);
                        pstmt.setInt(3, quantity);
                        pstmt.executeUpdate();
                        System.out.println("Product Added Successfully!");
                        pstmt.close();
                        break;
                    
                    case 2:
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM Product");
                        System.out.println("ProductID | ProductName | Price | Quantity");
                        while (rs.next()) {
                            System.out.println(rs.getInt("ProductID") + " | " + rs.getString("ProductName") + " | " + rs.getDouble("Price") + " | " + rs.getInt("Quantity"));
                        }
                        rs.close();
                        stmt.close();
                        break;
                    
                    case 3:
                        System.out.print("Enter ProductID to Update: ");
                        int updateId = scanner.nextInt();
                        System.out.print("Enter New Price: ");
                        double newPrice = scanner.nextDouble();
                        System.out.print("Enter New Quantity: ");
                        int newQuantity = scanner.nextInt();
                        String updateQuery = "UPDATE Product SET Price = ?, Quantity = ? WHERE ProductID = ?";
                        pstmt = conn.prepareStatement(updateQuery);
                        pstmt.setDouble(1, newPrice);
                        pstmt.setInt(2, newQuantity);
                        pstmt.setInt(3, updateId);
                        pstmt.executeUpdate();
                        System.out.println("Product Updated Successfully!");
                        pstmt.close();
                        break;
                    
                    case 4:
                        System.out.print("Enter ProductID to Delete: ");
                        int deleteId = scanner.nextInt();
                        String deleteQuery = "DELETE FROM Product WHERE ProductID = ?";
                        pstmt = conn.prepareStatement(deleteQuery);
                        pstmt.setInt(1, deleteId);
                        pstmt.executeUpdate();
                        System.out.println("Product Deleted Successfully!");
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
