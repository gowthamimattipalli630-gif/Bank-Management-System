package Com.bank.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;

import Com.bank.DTO.CustomerDetails;
import Com.bank.util.DatabaseConnection;

public class CustomerDAO {
	
	private final static String insert="insert into customer_details(Customer_Name, Customer_EmailId, Customer_MobileNumber, Customer_AadharNumber, Customer_Address, Customer_Gender, Customer_Amount)values(?,?,?,?,?,?,?)";
	private final static String selectAllcustomerDetails="select * from customer_details";
	private final static String selectAllCustomerDetailsByUsingStatus="select * from customer_details";
	private final static String	customerLogin="select * from customer_details where (Customer_EmailId=? or Customer_AccountNo=?) and Customer_Pin=?";
	private final static String customer_balance="select * from customer_details where Customer_Pin=?";
	private final static String select_CustomerDetails_ByUsingAccountNumber_And_PinNumber="select * from customer_details where Customer_AccountNo=? and Customer_Pin=?";
	public boolean InsertCustomerDetails(CustomerDetails customerDetails) {
		
		try {
			Connection connection= DatabaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(insert); 
	
			preparedStatement.setString(1,customerDetails.getName());
			preparedStatement.setString(2,customerDetails.getEmailId());
			preparedStatement.setLong(3,customerDetails.getMobileNumber());
			preparedStatement.setLong(4,customerDetails.getAadharNumber());
			preparedStatement.setString(5,customerDetails.getAddress());
			preparedStatement.setString(6,customerDetails.getGender());
			preparedStatement.setDouble(7,customerDetails.getAmount());
			int result=preparedStatement.executeUpdate();
			if(result!=0) {
				return true;
			}else {
				return false;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	 
 }
	
	public List<CustomerDetails> getAllCustomerDetails() {
		
		
		try {
			Connection connection= DatabaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(selectAllcustomerDetails);
			ResultSet resultset=preparedStatement.executeQuery();
			List<CustomerDetails> listOfCustomerDetails=new ArrayList<CustomerDetails>();
			if(resultset.isBeforeFirst()) {
				while(resultset.next()) {
					CustomerDetails  customerDetails=new CustomerDetails();
					
					customerDetails.setEmailId(resultset.getString("Customer_EmailId"));
					customerDetails.setAadharNumber(resultset.getLong("Customer_AadharNumber"));
					customerDetails.setMobileNumber(resultset.getLong("Customer_MobileNumber"));
					listOfCustomerDetails.add(customerDetails);
				}
				return listOfCustomerDetails;
			}else {
				return null;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<CustomerDetails> getAllCustomerDetailsByUsingStatus(String customer_Status) {
		try {
			Connection connection= DatabaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(selectAllCustomerDetailsByUsingStatus);
			preparedStatement.setString(1, customer_Status);
			ResultSet resultset=preparedStatement.executeQuery();
			List<CustomerDetails> listOfCustomerDetails=new ArrayList<CustomerDetails>();
			if(resultset.next()) {
				while(resultset.next()) {
					CustomerDetails  customerDetails=new CustomerDetails();
					customerDetails.setName(resultset.getString(1));
					customerDetails.setEmailId(resultset.getString("Customer_EmailId"));
					customerDetails.setAadharNumber(resultset.getLong("Customer_AadharNumber"));
					customerDetails.setMobileNumber(resultset.getLong("Customer_MobileNumber"));
					listOfCustomerDetails.add(customerDetails);
				}
				return listOfCustomerDetails;
			}else {
				return null;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public void UpdateAccountAndPinByUsingId(List<CustomerDetails> list) {
		// TODO Auto-generated method stub
		String update ="  update customer_details set Customer_AccountNo=? , Customer_Pin=? ,customer_Status=?";
		try {
			Connection connection=DatabaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(update);
			for(CustomerDetails customerDetails : list) {
				preparedStatement.setLong(1,customerDetails.getAccountNumber() );
				preparedStatement.setLong(2,customerDetails.getPin());
				preparedStatement.setString(3, "Accepted");
				preparedStatement.setString(4, customerDetails.getEmailId());
				preparedStatement.addBatch();
				
			}
			System.out.println(preparedStatement);
			int[] batch=preparedStatement.executeBatch();
			System.out.println("Updated");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public CustomerDetails selectCustomerDetailsByUsingEmailIdOrAccountNumberAndPin(String emailId,int pin) {
		try {
			Connection connection=DatabaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(customerLogin);
			preparedStatement.setString(1, emailId);
			preparedStatement.setString(2, emailId);
			preparedStatement.setInt(3, pin);
			ResultSet resultset=preparedStatement.executeQuery();
			if(resultset.next()) {
				String gender=resultset.getString("Customer_Gender");
				String name=resultset.getString("Customer_Name");
				CustomerDetails customerDetails=new CustomerDetails();
				customerDetails.setName(name);
				customerDetails.setGender(gender);
				return customerDetails;
			}else {
				return null;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static CustomerDetails CustomerBalance(int pin)
	{
		try {
			Connection connection=DatabaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(customer_balance);
			preparedStatement.setInt(1, pin);
			ResultSet resultset=preparedStatement.executeQuery();
			if(resultset.next())
			{
				double amount=resultset.getDouble("Customer_Amount");
				String name=resultset.getString("Customer_Name");
				CustomerDetails customerDetails = new CustomerDetails();
				customerDetails.setName(name);
				customerDetails.setAmount(amount);
				return customerDetails;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public boolean selectCustomerDetailsByUsingAccountNumberAndPinNumber(long accountNumber,int pin) {
		
		try {
			Connection connection=DatabaseConnection.forMySqlConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(select_CustomerDetails_ByUsingAccountNumber_And_PinNumber);
			preparedStatement.setLong(1, accountNumber);
			preparedStatement.setInt(2, pin);
			ResultSet resultset=preparedStatement.executeQuery();
			if(resultset.isBeforeFirst())
			{
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

	public boolean updateAccountByUsingPin(double balanceamount, int pin) {
		// TODO Auto-generated method stub
		try {
			Connection connection=DatabaseConnection.forMySqlConnection();
			
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
