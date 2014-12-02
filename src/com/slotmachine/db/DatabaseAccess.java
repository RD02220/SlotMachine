package com.slotmachine.db;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * Provides access to the 'player' database.
 * 
 * @author Chad Golden
 * @see <a href="http://www.chadgolden.com/?page_id=12">Merlin's Ghost</a>
 */
public class DatabaseAccess {

	private Connection dbConnection;
	private Statement statement;
	private ResultSet resultSet;

	public DatabaseAccess() {
	}

	private void initialize() {
		// Load MySQL Driver.
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver works");

			// Server connection login information.
			String connectionString = "jdbc:mysql://184.154.247.70:3306/chadgold_slotmachine";

			// Initialize the connection.
			dbConnection = DriverManager.getConnection(connectionString,
					"chadgold_team", "csci5335");

			 System.out.println("Successfully connected to database.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Failed to load MySQL driver.");
		}
	}

	/**
	 * Retrieves currency value from the database when passed a player name.
	 * 
	 * @param playerName
	 *            The player whose currency to retrieve.
	 * @return
	 */
	public int getCurrencyForPlayer(String playerName) {
		int currencyForPlayer = -1;
		try {
			// Connect to database.
			initialize();

			// Create statement and execute query.
			statement = dbConnection.createStatement();
			resultSet = statement
					.executeQuery("SELECT currency FROM player WHERE username = '"
							+ playerName + "';");

			// Get the value in the currency column for the player
			while (resultSet.next()) {
				currencyForPlayer = resultSet.getInt(1);
				break;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			// Display message if player not found.
			if (currencyForPlayer == -1) {
				JOptionPane.showMessageDialog(null, playerName
						+ " was not found in the database.");
			}
			close(); // Close when completed.
		}
		return currencyForPlayer;
	}

	/**
	 * Sets a specified currency value in the database for the passed player
	 * name.
	 * 
	 * @param playerName
	 *            The player whose currency to update.
	 * @param currencyForPlayer
	 *            The value to update to.
	 */
	public void setCurrencyForPlayer(String playerName, int currencyForPlayer) {
		try {
			initialize();
			System.out.println("Got through initializing");
			statement = dbConnection.createStatement();
			statement.executeUpdate("UPDATE player SET currency = "
					+ currencyForPlayer + " WHERE username = '" + playerName
					+ "';");
			System.out.println("Currency value for '" + playerName + "' "
					+ "successfully changed to " + currencyForPlayer + ".");

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			close();
		}
	}

	/**
	 * Inserts new player into the database with given username and password.
	 * 
	 * @param playerName
	 *            The name of the player to create.
	 * @param password
	 *            The password for the player.
	 */
	public void registerPlayer(String playerName, String password) {
		try {
			initialize();
			statement = dbConnection.createStatement();
			statement
					.executeUpdate("INSERT INTO player (`username`, `password`) "
							+ "VALUES('"
							+ playerName
							+ "','"
							+ getMd5Hash(password) + "');");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			close();
		}
	}

	/**
	 * Returns true if login credentials passed match those found in the
	 * database otherwise false is returned.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean verifyLogin(String username, String password) {
		boolean isValid = false;
		try {
			initialize();

			// Get the username and password from the database.
			String sqlStatement = "SELECT username, password " + "FROM player "
					+ "WHERE username = '" + username + "';";

			statement = dbConnection.createStatement();
			resultSet = statement.executeQuery(sqlStatement);

			while (resultSet.next()) {
				// Match the md5 hash of the password to stored db hash.
				if (username.equals(resultSet.getString(1))
						&& getMd5Hash(password).equals(resultSet.getString(2))) {
					isValid = true;
					System.out.println("Login for " + username
							+ " was successful.");
				} else {
					System.out.println("Login for " + username + " failed.");
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			close();
		}
		return isValid;
	}

	/**
	 * Returns 16 character md5 hash of the passed string.
	 * 
	 * @param password
	 *            Non-hashed password string.
	 * @return
	 */
	private String getMd5Hash(String password) {
		String hashText = "               ";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes(), 0, password.length());
			byte[] digest = md.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			hashText = bigInt.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hashText.substring(0, 16);
	}

	private void close() {
		close(resultSet);
		close(statement);
		close(dbConnection);
		// System.out.println("The session has closed successfully.");
	}

	private void close(AutoCloseable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		DatabaseAccess dbAccess = new DatabaseAccess();
		 //System.out.println(dbAccess.getCurrencyForPlayer("cha"));
		System.out.println("Database login test:\n---------------------");
		while (true) {
			java.util.Scanner scanner = new java.util.Scanner(System.in);
			System.out.print("Username: ");
			String username = scanner.nextLine();
			System.out.print("Password: ");
			String password = scanner.nextLine();

			dbAccess.verifyLogin(username, password);
			System.out.println();
			System.out.print("Try again? (Yes/No): ");
			if (!scanner.nextLine().equals("Yes")) {
				break;
			}
			System.out.println();
		}
		System.out.println("Test completed.");
	}
}
