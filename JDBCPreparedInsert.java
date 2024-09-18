import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;
public class JDBCPreparedInsert {
    public static void main(String[] args) {
        String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
        String USER = "SYSTEM";
        String PASSWORD = "BCA5C";
        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            if (con != null) {
                System.out.println("Connected to Oracle DB");
            } else {
                System.out.println("Failed to make connection to Oracle DB");
            }
            // Insert rows into Department table
            String insertDepartmentSQL = "INSERT INTO Department (Did, Dname) VALUES (?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertDepartmentSQL)) {
                for (int i = 1; i <= 5; i++) {
                    System.out.print("Enter Department ID for Department " + i + ": ");
                    int did = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Department Name for Department " + i + ": ");
                    String dname = scanner.nextLine();

                    pstmt.setInt(1, did);
                    pstmt.setString(2, dname);
                    pstmt.executeUpdate();
                } }
            System.out.println("Inserted 5 rows into Department table.");
            // Insert rows into Employee table
            String insertEmployeeSQL = "INSERT INTO Employee (Eid, Ename, Salary, Address, Did) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertEmployeeSQL)) {
                for (int i = 1; i <= 5; i++) {
                    System.out.print("Enter Employee ID for Employee " + i + ": ");
                    int eid = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Employee Name for Employee " + i + ": ");
                    String ename = scanner.nextLine();
                    System.out.print("Enter Salary for Employee " + i + ": ");
                    double salary = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Address for Employee " + i + ": ");
                    String address = scanner.nextLine();
                    System.out.print("Enter Department ID for Employee " + i + ": ");
                    int did = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    pstmt.setInt(1, eid);
                    pstmt.setString(2, ename);
                    pstmt.setDouble(3, salary);
                    pstmt.setString(4, address);
                    pstmt.setInt(5, did); // Referencing the department ID
                    pstmt.executeUpdate();
                }}
            System.out.println("Inserted 5 rows into Employee table.");
            // Close the connection
            con.close();
            scanner.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }}}
