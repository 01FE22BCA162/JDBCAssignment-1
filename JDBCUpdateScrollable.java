import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCUpdateScrollable {
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

            // Retrieve and display data from Department table using a scrollable ResultSet
            String selectDepartmentSQL = "SELECT * FROM Department";
            try (Statement deptStmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                 ResultSet deptRs = deptStmt.executeQuery(selectDepartmentSQL)) {

                System.out.println("Department Table:");
                while (deptRs.next()) {
                    System.out.println("Department ID: " + deptRs.getInt("Did") + ", Name: " + deptRs.getString("Dname"));
                }

                // Update Department Name
                System.out.print("Enter Department ID to update: ");
                int deptIdToUpdate = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter new Department Name: ");
                String newDeptName = scanner.nextLine();

                // Move to the first record to search
                deptRs.beforeFirst();
                while (deptRs.next()) {
                    if (deptRs.getInt("Did") == deptIdToUpdate) {
                        deptRs.updateString("Dname", newDeptName);
                        deptRs.updateRow();
                        System.out.println("Department updated successfully.");
                        break;
                    }
                }
            }

            // Retrieve and display data from Employee table using a scrollable ResultSet
            String selectEmployeeSQL = "SELECT * FROM Employee";
            try (Statement empStmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                 ResultSet empRs = empStmt.executeQuery(selectEmployeeSQL)) {

                System.out.println("Employee Table:");
                while (empRs.next()) {
                    System.out.println("Employee ID: " + empRs.getInt("Eid") + ", Name: " + empRs.getString("Ename") +
                            ", Salary: " + empRs.getDouble("Salary") + ", Address: " + empRs.getString("Address") +
                            ", Department ID: " + empRs.getInt("Did"));
                }

                // Update Employee Name
                System.out.print("Enter Employee ID to update: ");
                int empIdToUpdate = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter new Employee Name: ");
                String newEmpName = scanner.nextLine();

                // Move to the first record to search
                empRs.beforeFirst();
                while (empRs.next()) {
                    if (empRs.getInt("Eid") == empIdToUpdate) {
                        empRs.updateString("Ename", newEmpName);
                        empRs.updateRow();
                        System.out.println("Employee updated successfully.");
                        break;
                    }
                }
            }

            // Close the connection
            con.close();
            scanner.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
