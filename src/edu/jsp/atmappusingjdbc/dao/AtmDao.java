package edu.jsp.atmappusingjdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.jsp.atmappusingjdbc.dto.Account;

public class AtmDao {

	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	private static Connection getConnection() throws SQLException, ClassNotFoundException {
		String dbUrl = "jdbc:h2:~/test";
		String username = "sa";
		String password = "1234";

		Class.forName("org.h2.Driver");
		return DriverManager.getConnection(dbUrl, username, password);
	}

	public void saveAccount(Account account) {
		try {
			// Getting the Connection With the DataBase
			connection = getConnection();

			// Creating Statement and Writing the Query to Save the Account Details
			String query = "INSERT INTO `account` (`accountId`, `userName`, `AtmCardNumber`, `pin`, `amount`) VALUES (?, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(query);

			// Setting the Values to Query
			preparedStatement.setInt(1, account.getId());
			preparedStatement.setString(2, account.getUserName());
			preparedStatement.setString(3, account.getAtmCardNumber());
			preparedStatement.setInt(4, account.getPin());
			preparedStatement.setDouble(5, account.getAmount());

			// Executing the Query and Verifying the result
			if (preparedStatement.executeUpdate() == 1) {
				System.out.println("User Account Created!!");
			} else {
				System.out.println("User Account Not Created!!");
			}

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Enter the Proper Detalies");
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean validateUser(String atmCardNumber, int pin) {
		boolean result = false;
		try {
			connection = getConnection();
			String query = "SELECT * FROM account WHERE atmCardNumber = ? AND Pin =?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, atmCardNumber);
			preparedStatement.setInt(2, pin);
			resultSet = preparedStatement.executeQuery();
			result = resultSet.next();

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Provide Proper Account Detalies");
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return result;

	}

	public boolean withDrawMoney(int accountId, double amount) {
		boolean result = false;
		double ballance = getAccountBallance(accountId);
		if (ballance > 0) {
			if (ballance > amount) {
				ballance = ballance - amount;
				// Calling the Method to Update the Account Balance
				result = updateAccountBalance(accountId, ballance);
				System.out.println(amount + " WithDraw From the Account = " + accountId);
			} else {
				System.out.println("Account Ballance is InSufficient!!");
			}
		} else {
			System.out.println("Account Ballance is InSufficient!!");
		}
		return result;

	}

	private boolean updateAccountBalance(int accountId, double ballance) {
		boolean result = false;
		try {
			connection = getConnection();

			String query = "UPDATE ACCOUNT SET AMOUNT = ? WHERE ACCOUNTID = ?";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setDouble(1, ballance);
			preparedStatement.setInt(2, accountId);

			if (preparedStatement.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;

	}

	public double getAccountBallance(int accountId) {
		try {
			connection = getConnection();
			String query = "SELECT * FROM ACCOUNT WHERE ACCOUNTID = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, accountId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getDouble(5);
			} else {
				System.out.println("Account With the Given AccountNumber = " + accountId + " DoesNot Exist");
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return 0.1;
	}

	public boolean addMoneyToAccount(int accountId, double amount) {
		double ballance = getAccountBallance(accountId);
		ballance = ballance + amount;
		System.out.println(amount + " Added To the Account = " + accountId);
		return updateAccountBalance(accountId, ballance);

	}
}
