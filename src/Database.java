import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection getConnection() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=DBPerpus;user=sa;password=12345;encrypt=true;trustServerCertificate=true";
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
