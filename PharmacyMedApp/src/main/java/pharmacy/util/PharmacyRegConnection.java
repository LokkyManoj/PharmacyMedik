package pharmacy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PharmacyRegConnection {
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con  = DriverManager.getConnection("jdbc:mysql://localhost:3306/medik", "root", "12345678");
        return con;
    }

}
