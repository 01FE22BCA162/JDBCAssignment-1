import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCScrollableDisplay {
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

            // Retrieve and display data from Department table using a scrollable ResultSet
            String selectDepartmentSQL = "SELECT * FROM Department";
            try (Statement deptStmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                 ResultSet deptRs = deptStmt.executeQuery(selectDepartmentSQL)) {

                System.out.println("Department Table:");
                while (deptRs.next()) {
                    System.out.println("Department ID: " + deptRs.getInt("Did") + ", Name: " + deptRs.getString("Dname"));
                }
            }

            // Retrieve and display data from Employee table using a scrollable ResultSet
            String selectEmployeeSQL = "SELECT * FROM Employee";
            try (Statement empStmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                 ResultSet empRs = empStmt.executeQuery(selectEmployeeSQL)) {

                System.out.println("Employee Table:");
                while (empRs.next()) {
                    System.out.println("Employee ID: " + empRs.getInt("Eid") + ", Name: " + empRs.getString("Ename") +
                            ", Salary: " + empRs.getDouble("Salary") + ", Address: " + empRs.getString("Address") +
                            ", Department ID: " + empRs.getInt("Did"));
                }
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
