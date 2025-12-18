package Com.bank.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import Com.bank.DAO.CustomerDAO;
import Com.bank.DAO.TransactionDetailsDAO;
import Com.bank.DTO.CustomerDetails;
import Com.bank.DTO.TransactionDetails;
import Com.bank.exception.CustomerDetailsInvalidException;

public class CustomerService {
	
	CustomerDAO customerDAO=new CustomerDAO();
	CustomerDetails customerDetails = new CustomerDetails();
	TransactionDetailsDAO transactionDetailsDAO=new TransactionDetailsDAO();
	Scanner sc=new Scanner (System.in);

	public void customerRegistration()  {
		/* to take the values from customer */
		
		/* To set all the customer Details inside the object to transfer from service to DAO */
		List<CustomerDetails> allCustomerDetails=customerDAO.getAllCustomerDetails();
		CustomerDetails customerDetails=new CustomerDetails();
		System.out.println("Enter Customer Name");
		String name=sc.next();
		System.out.println("Enter Cusomer EmailId");
		String emailId=sc.next();
		boolean mbstatus=true;
		System.out.println("Enter Cusomer MobileNumber");
		while(mbstatus) {
			try {
				long mobileNumber=sc.nextLong();
			    if(mobileNumber>=6000000000l && mobileNumber<=9999999999l)
				{
				customerDetails.setMobileNumber( mobileNumber);
				mbstatus=false;
			
			}else {
					throw new CustomerDetailsInvalidException("Invalid MobileNumber");
				}
				}catch(CustomerDetailsInvalidException e) {
					
					System.out.println(e.getExceptionMessage());
					System.out.println("Re-enter the MobileNumber");		
				}
		}
		boolean abstatus=true;
		System.out.println("Enter Cusomer AadharNumber");
		while(abstatus) {
			try {
				long aadharNumber=sc.nextLong();
				if(aadharNumber>=100000000000l && aadharNumber<=999999999999l) {
					customerDetails.setAadharNumber( aadharNumber);
					abstatus=false;
				}else {
					
						throw new CustomerDetailsInvalidException("Invalid AadharNumber");	
				}
			}catch(CustomerDetailsInvalidException e1) {
				
				System.out.println(e1.getExceptionMessage());
				System.out.println("Re-enter the Aadharnumber");		
			}
		}
		System.out.println("Enter Cusomer Address");
		String address=sc.next();
		System.out.println("Enter Cusomer Gender");
		String gender=sc.next();
		System.out.println("Enter Cusomer Amount");
		double amount=sc.nextDouble();
		
		customerDetails.setName(name);
		customerDetails.setAddress(address);
		customerDetails.setEmailId(emailId);
		customerDetails.setGender(gender);
		customerDetails.setAmount(amount);
		
		/* Get it from the CustomerDAO by making CustomerDao Has-A Relationship with CustomerService */
		if(customerDAO.InsertCustomerDetails(customerDetails)) {
			System.out.println("Data Inserted");
		}else {
			System.out.println("Server Error");
		}
	}
	
	public void customerLogin() {
		System.out.println("Enter Customer EmailId OR AccountNumber");
		String emailidoraccountnumber=sc.next();
		System.out.println("Enter Customer PIN");
		int pin=sc.nextInt();
		CustomerDetails customerDetails=customerDAO.selectCustomerDetailsByUsingEmailIdOrAccountNumberAndPin(emailidoraccountnumber, pin);
			if(customerDetails!=null) {
				 String[] capchadata= {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","1","2","3","4","5","6","7","8","9"};
				 String capcha="";
				 for(int i=0;i<4;i++) {
					 Random random=new Random();
					 int capchapik=random.nextInt(capchadata.length);
					 capcha=capcha+capchadata[capchapik];
				 }
				 System.out.println("CAPTCHA :"+capcha);
					System.out.println("Enter CAPTCHA");
					String usercaptcha=sc.next();
					if(capcha.equals(usercaptcha)) {
						/*System.out.println("Login Successful..");	*/
						if(customerDetails.getGender().equalsIgnoreCase("Male")) {
							System.out.println("Hello \n Mr : " + customerDetails.getName());
							customerOperation();
						}
						if(customerDetails.getGender().equalsIgnoreCase("female")) {
							System.out.println("Helle \n Miss : " + customerDetails.getName());
							customerOperation();
						}

					}else {
						System.out.println("Invalid Captcha");
					}
		}
		else
		{
			System.out.println("Invalid credentials");
		}
	}
	
	private void customerOperation() {
		System.out.println("Enter \n 1 For Credit \n 2 For Debit \n 3 For Check Statement \n 4 For Check Balance \n 5 For Update Pin \n 6 For Close Account");
		switch(sc.nextInt()) {
		case 1:
			System.out.println("Credit");
			break;
		case 2:
			System.out.println("Debit");
			debit();
			break;
		case 3:
			System.out.println("Check Statement");
			break;
		case 4:
			System.out.println("Check Balance");
			System.out.println("Enter Pin :");
			int pin=sc.nextInt();
			CustomerDetails customerDetails= customerDAO.CustomerBalance(pin);
			if(customerDetails != null)
			{
				System.out.println("NAME :"+customerDetails.getName());
				System.out.println("Balance :"+customerDetails.getAmount()+"/-");
			}
			break;
		case 5:
			System.out.println("Update Pin");
			break;
		case 6:
			System.out.println("Close Account");
			break;
		}
	}
	
	public void debit() {
		System.out.println("Enter Customer Account Number");
		long accountNumber=sc.nextLong();
		System.out.println("Enter Customer Pin");
		int pin=sc.nextInt();
		if(customerDAO.selectCustomerDetailsByUsingAccountNumberAndPinNumber(accountNumber, pin)) {
			System.out.println("Enter Amount");
			double amount =sc.nextDouble();
			if(amount>=0) {
				double dbamount=customerDetails.getAmount();
				if(amount<=dbamount) {
					double balanceamount=dbamount-amount;
					if(customerDAO.updateAccountByUsingPin(balanceamount,pin)) {
						TransactionDetails transactionDetails=new TransactionDetails();
						transactionDetails.setTransactionType("DEBIT");
						transactionDetails.setTransactionAmount(amount);
						transactionDetails.setBalanceamount(balanceamount);
						transactionDetails.setTransactiondate(LocalDate.now());
						transactionDetails.setTranscationtime(LocalTime.now());
						transactionDetails.setCustomeraccountnumber(accountNumber);
						transactionDetails.setTransactionstatus("Transfered");
						transactionDetailsDAO.insertTransactionDetails(transactionDetails);
						System.out.println("Updated");
					}
				}else {
					System.out.println("Server Error");
				}
				
			}else {
				System.out.println("In-Sufficient Amount");
			}
		}else {
			System.out.println("Invalid Credentials");
		}
	}
}



