package Com.bank.Main;

import java.util.Scanner;
import Com.bank.service.AdminService;
import Com.bank.service.CustomerService;

public class BankMainClass {
	
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		
		CustomerService customerService=new CustomerService();
		AdminService adminService=new AdminService();
		
		
		System.out.println("Enter \n 1. For Customer Registration \n 2. For Costomer Login \n 3. For Admin Login");
		switch(sc.nextInt()) 
		{
		case 1:
			System.out.println("Customer Registration");
			customerService.customerRegistration();
			break;
		case 2:
			System.out.println("Customer Login");
			customerService.customerLogin();
			break;
		case 3:
			System.out.println("Admin Login");
			adminService.adminLogin();
			break;	
		default :
			System.out.println("Invalid Request");
			break;
		}
	}

}
