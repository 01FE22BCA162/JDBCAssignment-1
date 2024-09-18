import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MyJDBCEmployeeDepartment {
    public static void main(String[] args) {
        String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
        String USER = "SYSTEM";
        String PASSWORD = "BCA5C";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            if (con != null) {
                System.out.println("Connected to Oracle DB");
            } else {
                System.out.println("Failed to make connection to Oracle DB");
            }

            Statement stmt = con.createStatement();

            // Create Department table
            String createDepartmentTableSQL = "CREATE TABLE Department (" +
                    "Did INT PRIMARY KEY, " +
                    "Dname VARCHAR(50)" +
                    ")";
            stmt.executeUpdate(createDepartmentTableSQL);
            System.out.println("Department table created successfully.");

            // Create Employee table with Did as foreign key
            String createEmployeeTableSQL = "CREATE TABLE Employee (" +
                    "Eid INT PRIMARY KEY, " +
                    "Ename VARCHAR(50), " +
                    "Salary DECIMAL(10, 2), " +
                    "Address VARCHAR(100), " +
                    "Did INT, " +
                    "FOREIGN KEY (Did) REFERENCES Department(Did)" +
                    ")";
            stmt.executeUpdate(createEmployeeTableSQL);
            System.out.println("Employee table created successfully.");

            // Close the statement and connection
            stmt.close();
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
