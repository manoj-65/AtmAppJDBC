package edu.jsp.atmappusingjdbc.service;

import java.util.Scanner;

import edu.jsp.atmappusingjdbc.dao.AtmDao;
import edu.jsp.atmappusingjdbc.dto.Account;

public class ATMService {
	static {
		System.out.println("Welcome TO BanckServiceCenter\n");
	}

	public static void main(String[] args) {
		Scanner read = new Scanner(System.in);
		AtmDao dbTask = new AtmDao();
		boolean valid = false;
		double ballance = 0;
		while (true) {
			System.out.println("Select Your Choice :");
			System.out.println("--------------Choices----------------");
			System.out.println(
					"1.Create Account\n2.Check Account Ballance\n3.Add Money TO Account\n4.WithDraw Money\n5.Exit");
			int choice = read.nextInt();
			switch (choice) {
			case 1:
				Account account = readAccountDetails(read);
				dbTask.saveAccount(account);
				break;
			case 2:
				valid = validateUser(read, dbTask);
				System.out.println();
				if (valid) {
					System.out.println("Enter Account Number to get the Ballance:");
					int accountNumber = read.nextInt();
					ballance = dbTask.getAccountBallance(accountNumber);
					System.out.println("Your Account Ballance = " + ballance);
				} else {
					System.out.println("Enter the proper Details!!!");
				}
				break;
			case 3:
				valid = validateUser(read, dbTask);
				System.out.println();
				if (valid) {
					System.out.println("Enter the Account Number to Add Money");
					int accountNumber = read.nextInt();
					System.out.println("Enter the Amount to be Added into Your Account : ");
					double amount = read.nextDouble();
					valid = dbTask.addMoneyToAccount(accountNumber, amount);
					if (valid) {
						System.out.println("After Adding Money Your Account Ballance = "
								+ dbTask.getAccountBallance(accountNumber));
					}
				} else {
					System.out.println("Enter the proper Details!!!");
				}
				break;
			case 4:
				valid = validateUser(read, dbTask);
				System.out.println();
				if (valid) {
					System.out.println("Enter the Account Number to WithDraw Money");
					int accountNumber = read.nextInt();
					System.out.println("Enter the Amount to be WithDraw From Your Account : ");
					double amount = read.nextDouble();
					valid = dbTask.withDrawMoney(accountNumber, amount);
					if (valid) {
						System.out.println("After WithDrawing Money Your Account Ballance = "
								+ dbTask.getAccountBallance(accountNumber));
					}
				} else {
					System.out.println("Enter the proper Details!!!");
				}
				break;
			case 5:
				System.exit(0);
				break;

			default:
				System.out.println("Select the Proper Choice");
				break;
			}

		}
	}

	private static Account readAccountDetails(Scanner read) {
		System.out.println("Enter the Account Number:");
		int accountNumber = read.nextInt();
		System.out.println("Enter the User Name :");
		read.nextLine();
		String userName = read.nextLine();
		System.out.println("Enter the Atm Card Number :");
		String atmCardNumber = read.nextLine();
		System.out.println("Enter the 4 Digit Pin For Atm Card :");
		int pin = read.nextInt();
		System.out.println("Enter the Intial Amount To Be Add to Account :");
		double amount = read.nextDouble();

		return new Account(accountNumber, userName, atmCardNumber, pin, amount);
	}

	private static boolean validateUser(Scanner read, AtmDao dbTask) {
		System.out.println("Enter the Atm Card Number : ");
		read.nextLine();
		String atmCardNumber = read.nextLine();
		System.out.println("Enter the Atm Pin :");
		int pin = read.nextInt();
		return dbTask.validateUser(atmCardNumber, pin);
	}

}
