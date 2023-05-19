package edu.jsp.atmappusingjdbc.dto;

public class Account {

	private int id;
	private String userName;
	private String atmCardNumber;
	private int pin;
	private double amount;

	public Account() {
		super();
	}

	public Account(int id, String userName, String atmCardNumber, int pin, double amount) {
		super();
		this.id = id;
		this.userName = userName;
		this.atmCardNumber = atmCardNumber;
		this.pin = pin;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAtmCardNumber() {
		return atmCardNumber;
	}

	public void setAtmCardNumber(String atmCardNumber) {
		this.atmCardNumber = atmCardNumber;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", userName=" + userName + ", atmCardNumber=" + atmCardNumber + ", pin=" + pin
				+ ", amount=" + amount + "]";
	}

}
