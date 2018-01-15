
//DB CONFIG

// based on the https://github.com/HackeaMesta/Game-Store
/**
 * Another area where the DB config is stored (also kept in MainFXApp and Model.DBConnection)
 */
package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import controller.mainViewController;

public class DBConfig {
	static //

	Connection connection = null;
	private static final String USERNAME = "MC";
	private static final String PASSWORD = "Hmmark18";
	private static final String CONN = ("jdbc:mysql://50.62.209.159/"); // 50.62.209.159
																		// is
																		// the
																		// correct
																		// ip
	private static final String DB = "company?autoReconnect=true&useSSL=false";

	/**
	 * Method that connects us to the SQL database
	 * 
	 * @return the database connection if connection is successful
	 * @throws SQLException
	 *             SQL error
	 */
	// connection method that connects us to the MySQL database
	public static Connection getConnection() throws SQLException {
		// This is the syntax for connecting to the DB
		// identify DB 'iVoterDB' then use a username 'root' and for this user
		// it
		// has no password
		System.out.println("Establishing connection to database");
		try {
			Connection conn = DriverManager.getConnection((CONN + DB), USERNAME, PASSWORD);

			System.out.println("Connection success");
			return conn;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());

			if (e.getMessage().equals(
					"Could not create connection to database server. Attempted reconnect 3 times. Giving up.")) {

				createCompanyDatabase();
				System.out.println(connection.toString());
				return connection;
			}

		}

		return connection;
	}
	/*
	 * =========================================================================
	 * ===================================
	 * 
	 * @author Maurice Siegle Description: Generates database if it does not
	 * already exist:
	 *
	 * =========================================================================
	 * ===================================
	 */

	public static void createCompanyDatabase() {
		System.out.println("Attempting to construct Company Database");
		
		String url = "jdbc:mysql://localhost";

		// Defines username and password to connect to database server.
		String username = "root";
		String password = "";

		// SQL command to create a database in MySQL.

		String sql = "CREATE DATABASE COMPANY";
		
		try  {
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.execute();
		

		} catch (SQLException sqlException) {
			if (sqlException.getErrorCode() == 1007) {
				// Database already exists error
				System.out.println(sqlException.getMessage());
			} else {
				// Some other problems, e.g. Server down, no permission, etc
				sqlException.printStackTrace();
			}
		}
		
		
		String url1 = "jdbc:mysql://localhost/company";

		try  {
			Connection conn = DriverManager.getConnection(url1, username, password);
			sql = "CREATE TABLE IF NOT EXISTS company.department (`Dname` varchar(15) DEFAULT NULL,`Dnumber` varchar(15) NOT NULL, `Mgr_ssn` varchar(15) DEFAULT NULL,`Dlocation` varchar(15) DEFAULT NULL, primary key(Dnumber)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.execute();
			sql = "INSERT Ignore INTO company.department (`Dname`, `Dnumber`, `Mgr_ssn`, `Dlocation`) VALUES('Headquarts', '1', '333445555', 'Houston'),('Administration', '4', '987654321', 'Stafford'),('Research', '5', '888665555', 'Bellaire');";
			stmt = conn.prepareStatement(sql);
			stmt.execute();
			sql = "CREATE TABLE IF NOT EXISTS company.employee (`Fname` varchar(15) NOT NULL,`Lname` varchar(15) DEFAULT NULL,`Ssn` varchar(15) DEFAULT NULL,`Super_ssn` varchar(15) DEFAULT NULL,`Dno` varchar(15) DEFAULT NULL, primary key(Fname), foreign key(Dno) references department(Dnumber) on update cascade on delete cascade) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
			stmt = conn.prepareStatement(sql);
			stmt.execute();
			sql = "INSERT Ignore INTO company.employee (`Fname`, `Lname`, `Ssn`, `Super_ssn`, `Dno`) VALUES('Ahmad', 'Jabbar', '987987987', '333445555', '4'),('Alicia', 'Zelaya', '999887777', NULL, '4'),('Franklin', 'Wong', '333445555', '888665555', '5'),('James', 'Borg', '888665555', '333445555', '1'),('Jennifer', 'Wallace', '987654321', '333445555', '4'),('John', 'Smith', '123456789', '333445555', '5'),('Joyce', 'English', '453453453', '9876554321', '5'),('Ramesh', 'Narayan', '666884444', '888665555', '5'),('Richard', 'Marini', '325467891', '888665555', '4');";
			stmt = conn.prepareStatement(sql);
			stmt.execute();
			sql = "CREATE TABLE IF NOT EXISTS company.project (`Pname` varchar(15) DEFAULT NULL,`Pnumber` varchar(15) NOT NULL,`Plocation` varchar(15) DEFAULT NULL,`Dnum` varchar(15) DEFAULT NULL, primary key(Pnumber), foreign key(Dnum) references department(Dnumber) on update cascade on delete cascade) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
			stmt = conn.prepareStatement(sql);
			stmt.execute();
			sql = "INSERT Ignore INTO company.project (`Pname`, `Pnumber`, `Plocation`, `Dnum`) VALUES('ProductX', '1', 'Bellaire', '5'),('Computerization', '10', 'Stafford', '4'),('ProductY', '2', 'Bellaire', '5'),('Reorganization', '20', 'Houston', '1'),('ProductZ', '3', 'Bellaire', '5'),('Newbenefits', '30', 'Stafford', '4'),('testCase', '5', 'Bellaire', '5');";
			stmt = conn.prepareStatement(sql);
			stmt.execute();
			sql = "CREATE TABLE IF NOT EXISTS company.works_on (`Essn` varchar(15) NOT NULL,`Pno` varchar(15) NOT NULL,`Hours` varchar(15) DEFAULT NULL, primary key(Essn, Pno), foreign key(Pno) references project(Pnumber) on update cascade on delete cascade) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
			stmt = conn.prepareStatement(sql);
			stmt.execute();
			sql = "INSERT Ignore INTO company.works_on (`Essn`, `Pno`, `Hours`) VALUES('123456789', '1', '33'),('123456789', '2', '8'),('333445555', '10', '10'),('333445555', '2', '10'),('333445555', '20', '10'),('333445555', '3', '10'),('453453453', '1', '20'),('453453453', '2', '20'),('666884444', '3', '40'),('888665555', '20', NULL),('987654321', '20', '15'),('987654321', '30', '20'),('987987987', '10', '35'),('987987987', '30', '5'),('999887777', '10', '10'),('999887777', '30', '30');";
			stmt = conn.prepareStatement(sql);
			stmt.execute();
			connection = conn;
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Method that displays errors if the connection fails, like if you use the
	 * wrong username/password
	 * 
	 * @param ex
	 *            The exception that was thrown by SQL
	 */
	public static void displayException(SQLException ex) {
		System.out.println("Error Message: " + ex.getMessage());
		System.out.println("Error Code: " + ex.getErrorCode());
		System.out.println("SQL Status: " + ex.getSQLState());

	}
}