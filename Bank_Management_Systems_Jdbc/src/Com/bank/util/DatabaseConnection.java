package Com.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	public static Connection forMySqlConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return  DriverManager.getConnection("jdbc:mysql://localhost:3306/ e8_bank_management_system?user=root&password=root");
	}
}
