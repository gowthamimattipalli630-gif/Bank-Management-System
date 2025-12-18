package Com.bank.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import Com.bank.DAO.AdminDAO;
import Com.bank.DAO.CustomerDAO;
import Com.bank.DTO.CustomerDetails;

public class AdminService {
	AdminDAO adminDAO=new AdminDAO();
	CustomerDAO customerDAO=new CustomerDAO();
	
	Scanner sc=new Scanner(System.in);
	public void adminLogin() {
		/* Email id */
		System.out.println("Enter Admin EmailId");
		String emailId=sc.next();
		
		/* Password */
		System.out.println("Enter Admin Password");
		String password=sc.next();
		if(adminDAO.selectAdminDetailsByUsingEmailIdAndPassword(emailId, password)) {
			System.out.println("Enter \n 1. To Get All Customer Details  \n 2. To Get All Account Request Details  \n 3. To Get All Account Closing Details");
			switch(sc.nextInt())
			{
			case 1:
				System.out.println("All Customer Details");
				break;
			case 2:
				System.out.println("All Account Request Details");
				List<CustomerDetails> allcustomerDetailsByUsingStatus=customerDAO.getAllCustomerDetailsByUsingStatus("Pending");
				allcustomerDetailsByUsingStatus.stream().forEach((customer-> {
					System.out.println("Customer Name :" + customer.getName());
					System.out.println("Customer EmailId : "+  customer.getEmailId());
					long mobileNumber=customer.getMobileNumber();
					String mb=""+mobileNumber;
					long aadharNumber=customer.getAadharNumber();
					String ab=""+aadharNumber;
					System.out.println("Customer Mobile Number : "+ mb.substring(0,3) + "****" + mb.substring(7,10));
					System.out.println("Customer Aadhar Number : "+ ab.substring(0,3) + "****" + ab.substring(9,12));
					System.out.println("***-----------*****----------------*****");
				}));
				System.out.println("Enter 1.To Generate Account Number For One Person \n 2. To Select all to Generate Account Number ");
				switch(sc.nextInt()) {
				case 1:
					break;
				case 2:
					List<CustomerDetails> accepteddetails=new ArrayList<CustomerDetails>();
					for(int i=0;i< allcustomerDetailsByUsingStatus.size();i++) {
						CustomerDetails customerDetails=allcustomerDetailsByUsingStatus.get(i);
						Random random=new Random();
						int ac=random.nextInt(1000000);
						if(ac<100000) {
							ac+=100000;
						}
						customerDetails.setAccountNumber(ac);
						int pin=random.nextInt(10000);
						if(pin<1000) {
							pin+=1000;
						}
						customerDetails.setPin(pin);
						accepteddetails.add(customerDetails);
					}
					customerDAO.UpdateAccountAndPinByUsingId(allcustomerDetailsByUsingStatus);
					System.out.println(accepteddetails);
					break;
					
					default :
						break;
				}
				break;
			case 3:
				System.out.println("All Account Closing Request Details");
				break;
			default :
				System.out.println("Invalid Request....");
				break;
			}
		}else {
			System.out.println("Invalid EmailId and Password");
		}
	}

}
