import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JDBCDeletePrepared {
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

            // Delete all rows from Employee table
            String deleteEmployeeSQL = "DELETE FROM Employee";
            try (PreparedStatement pstmt = con.prepareStatement(deleteEmployeeSQL)) {
                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Deleted " + rowsAffected + " rows from Employee table.");
            }

            // Delete all rows from Department table
            String deleteDepartmentSQL = "DELETE FROM Department";
            try (PreparedStatement pstmt = con.prepareStatement(deleteDepartmentSQL)) {
                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Deleted " + rowsAffected + " rows from Department table.");
            }

            // Close the connection
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
