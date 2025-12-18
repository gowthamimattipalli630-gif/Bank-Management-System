package Com.bank.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Com.bank.util.DatabaseConnection;

public class AdminDAO {
	public static final String admin_login="Select * from admin_details where emailId=? and password=?";

	public boolean selectAdminDetailsByUsingEmailIdAndPassword(String emailId,String password) 
	{
		/* communication by using jdbc */
		
		try {
			Connection connection=DatabaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(admin_login);
			preparedStatement.setString(1, emailId);
			preparedStatement.setString(2, password);
			ResultSet resultset=preparedStatement.executeQuery();
			if(resultset.isBeforeFirst()) {
				return true;
			}else {
				return false;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
}
