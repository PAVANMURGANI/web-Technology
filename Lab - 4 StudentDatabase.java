import java.sql.*;

public class StudentDatabase {
    public static void main(String[] args) {
        // Database connection variables
        String url =  "jdbc:mysql://localhost:3306/studentdb";   // Adjust with your database URL
        String user = "root";  // Replace with your database username
        String password = "system";  // Replace with your database password

        // SQL query to retrieve all records from Students table
        String query = "SELECT * FROM students";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            // Check if the table exists
            DatabaseMetaData dbMetaData = conn.getMetaData();
            ResultSet tables = dbMetaData.getTables(null, null, "students", null);
            if (!tables.next()) {
                System.out.println("Table 'Students' does not exist.");
                return;
            }

            // Execute query to get student records
            ResultSet rs = stmt.executeQuery(query);

            // Display student records
            System.out.println("Student Records:");
            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                int age = rs.getInt("Age");
                String major = rs.getString("Major");
                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age + ", Major: " + major);
            }

            // Fetch metadata for the Students table
            ResultSetMetaData rsMetaData = rs.getMetaData();
            System.out.println("\nTable Metadata:");
            int columnCount = rsMetaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rsMetaData.getColumnName(i);
                String columnType = rsMetaData.getColumnTypeName(i);
                System.out.println("Column " + i + ": " + columnName + " (" + columnType + ")");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
