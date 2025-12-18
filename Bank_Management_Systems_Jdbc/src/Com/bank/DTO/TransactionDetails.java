package Com.bank.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class TransactionDetails {
	/* transaction_id, transaction_type, transaction_amount, transaction_time, transaction_date, balance_amount, transaction_status, customer_account_number*/
	private int transactioId;
	private String transactionType;
	private double transactionAmount;
	private LocalDate transactiondate;
	private LocalTime transcationtime;
	private double balanceamount;
	private String transactionstatus;
	private long customeraccountnumber;
	
	public TransactionDetails() {
		
	}
	public TransactionDetails(int transactioId, String transactionType, double transactionAmount,
			LocalDate transactiondate, LocalTime transcationtime, double balanceamount, String transactionstatus,
			long customeraccountnumber) {
		super();
		this.transactioId = transactioId;
		this.transactionType = transactionType;
		this.transactionAmount = transactionAmount;
		this.transactiondate = transactiondate;
		this.transcationtime = transcationtime;
		this.balanceamount = balanceamount;
		this.transactionstatus = transactionstatus;
		this.customeraccountnumber = customeraccountnumber;
	}
	public int getTransactioId() {
		return transactioId;
	}
	public void setTransactioId(int transactioId) {
		this.transactioId = transactioId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public LocalDate getTransactiondate() {
		return transactiondate;
	}
	public void setTransactiondate(LocalDate transactiondate) {
		this.transactiondate = transactiondate;
	}
	public LocalTime getTranscationtime() {
		return transcationtime;
	}
	public void setTranscationtime(LocalTime transcationtime) {
		this.transcationtime = transcationtime;
	}
	public double getBalanceamount() {
		return balanceamount;
	}
	public void setBalanceamount(double balanceamount) {
		this.balanceamount = balanceamount;
	}
	public String getTransactionstatus() {
		return transactionstatus;
	}
	public void setTransactionstatus(String transactionstatus) {
		this.transactionstatus = transactionstatus;
	}
	public long getCustomeraccountnumber() {
		return customeraccountnumber;
	}
	public void setCustomeraccountnumber(long customeraccountnumber) {
		this.customeraccountnumber = customeraccountnumber;
	}
	@Override
	public String toString() {
		return "TransactionDetails [transactioId=" + transactioId + ", transactionType=" + transactionType
				+ ", transactionAmount=" + transactionAmount + ", transactiondate=" + transactiondate
				+ ", transcationtime=" + transcationtime + ", balanceamount=" + balanceamount + ", transactionstatus="
				+ transactionstatus + ", customeraccountnumber=" + customeraccountnumber + "]";
	}
	

	
	
}
